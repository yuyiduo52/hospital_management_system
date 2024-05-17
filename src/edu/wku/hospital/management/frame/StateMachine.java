package edu.wku.hospital.management.frame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * This class is used to manage the state of the system
 * @author Yiduo Yu
 * @version 1.0
 * @since 2021-5-17
 *
 */
public class StateMachine implements Serializable {
    private String state;
    private Map<String, Serializable> data;
    private Map<State, Command> commands;


    public enum State {
        INITIAL, 
        PATIENT_REGISTRATION, 
        AD, 
        DOCTOR_CHECK, 
        PHARMACY_DISPENSE,
        BILLING, 
        CASE_RECORDING,
        QUIT, 
    }

    public StateMachine(String state) {
        this.state = state;
        this.data = new HashMap<>();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Command getCommand(String state){
        switch (state) {
            case "register":
            case "pr":
                state = State.PATIENT_REGISTRATION.name();
                break;
            case "check":
            case "dc":
                state = State.DOCTOR_CHECK.name();
                break;
            case "record":
            case "cr":
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
            default:
                state = State.INITIAL.name();
                break;
        }
        return commands.get(State.valueOf(state));
    }

    /**
     * This method is used to get the state of the system
     * @return the state of the system
     */
    public void save() {
        try (FileOutputStream fileOut = new FileOutputStream("./tmp/state.ser");
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
    public static StateMachine load() {
        StateMachine stateMachine = null;
        try (FileInputStream fileIn = new FileInputStream("/tmp/state.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            stateMachine = (StateMachine) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("StateMachine class not found");
            c.printStackTrace();
        }
        return stateMachine;
    }
    
}