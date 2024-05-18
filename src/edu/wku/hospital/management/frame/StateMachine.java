package edu.wku.hospital.management.frame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wku.hospital.management.service.Command;
import edu.wku.hospital.management.service.Commands;

/**
 * 
 * This class is used to manage the state of the system
 * @author Yiduo Yu
 * @version 1.0
 * @since 2024-5-17
 *
 */
public class StateMachine implements Serializable {
    private List<State> stateHistory;
    private Map<State, List<Serializable>> data;
    private Map<State, Command> commands;

    public enum State {
        INITIAL, 
        PATIENT_REGISTRATION, 
        DOCTOR_CHECK, 
        BILLING, 
        CASE_RECORDING,
        QUIT, 
        EXPIRED
    }   

    public StateMachine(State initialState) {
        this.stateHistory = new ArrayList<>();
        this.stateHistory.add(initialState);
        this.data = new HashMap<>();
        commands = new HashMap<>();
        commands.put(State.INITIAL, new Commands.Initial());
        commands.put(State.PATIENT_REGISTRATION, new Commands.Register());
        commands.put(State.DOCTOR_CHECK, new Commands.Check());
        commands.put(State.BILLING, new Commands.Bill());
        commands.put(State.CASE_RECORDING, new Commands.Record());
        commands.put(State.QUIT, new Commands.Quit());
        commands.put(State.EXPIRED, new Commands.Expire());
    }

    public void manageState(Command command, Serializable data){
        //manage state for some special cases
        if ( command == null) {
            return;
        }
        else if (command instanceof Commands.Bill && stateHistory.contains(State.DOCTOR_CHECK) ) {
            stateHistory.add(State.BILLING);
            addData(State.BILLING,data);
        }
        else if (command instanceof Commands.Check && stateHistory.contains(State.PATIENT_REGISTRATION) ) {
            stateHistory.add(State.DOCTOR_CHECK);
            addData(State.DOCTOR_CHECK,data);

        }
        else if (command instanceof Commands.Record && stateHistory.contains(State.DOCTOR_CHECK) ) {
            stateHistory.add(State.CASE_RECORDING);
            addData(State.CASE_RECORDING,data);

        }
        else if (command instanceof Commands.Quit) {
            stateHistory.add(State.QUIT);
            addData(State.QUIT,data);
        }
        else if (command instanceof Commands.Initial) {
            stateHistory.add(State.INITIAL);
            addData(State.INITIAL,data);

        }
        else if (command instanceof Commands.Register) {
            stateHistory.add(State.PATIENT_REGISTRATION);
            addData(State.PATIENT_REGISTRATION,data);
        }
        else if (command instanceof Commands.Expire){
            stateHistory.clear();
            stateHistory.add(State.INITIAL);
        }
        save();       
    }


    public State getCurrentState() {
        return stateHistory.get(stateHistory.size() - 1);
    }


    public Command getCommand(String state){
        switch (state) {
            case "initial":
            case "i":
                state = State.INITIAL.name();
                break;
            case "register":
            case "rg":
                state = State.PATIENT_REGISTRATION.name();
                break;
            case "check":
            case "ck":
                state = State.DOCTOR_CHECK.name();
                break;
            case "record":
            case "rc":
                state = State.CASE_RECORDING.name();
                break;
            case "bill":
            case "bl":
                state = State.BILLING.name();
                break;
            case "quit":
            case "q":
                state = State.QUIT.name();
                break;
            case "expire":
            case "logout":
                state = State.EXPIRED.name();
            default:
                state = State.INITIAL.name();
                break;
        }
        return commands.get(State.valueOf(state));
    }

    public void addData(State state, Serializable value) {
        if (!data.containsKey(state)) {
            data.put(state, new ArrayList<>());
        }
        data.get(state).add(value);
    }

    public List getData(State state) {
        if (!data.containsKey(state)) {
            return null;
        }
        return data.get(state);
    }

    /**
     * 
     * This method is used to save the state of the system
     * @return the state of the system
     */
 public void save() {
        File file = new File("./tmp/state");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * This method is used to load the state of the system
     * @return the state of the system
     */
    public StateMachine load() {
        File file = new File("./tmp/state");
        if (!file.exists() || file.length() == 0) {
            return new StateMachine(State.INITIAL);
        }

        StateMachine stateMachine = null;
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            stateMachine = (StateMachine) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
            return new StateMachine(State.INITIAL);
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return new StateMachine(State.INITIAL);
        }
        return stateMachine;
    }
    
}