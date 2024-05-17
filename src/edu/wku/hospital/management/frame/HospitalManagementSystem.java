package edu.wku.hospital.management.frame;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class HospitalManagementSystem {
    private StateMachine stateMachine;
    private Scanner scanner;

    public HospitalManagementSystem() {
        stateMachine   = new StateMachine(StateMachine.State.INITIAL);
        //load state machine from temp file
        if (stateMachine.load() != null) {
            stateMachine = stateMachine.load();
        } else {
            stateMachine = new StateMachine(StateMachine.State.INITIAL);
        }
        scanner = new Scanner(System.in);
    }

    public void start() {
        Command command= stateMachine.getCommand(stateMachine.getCurrentState().name());
        System.out.println(stateMachine.getCurrentState().name());
        List<Serializable> data = stateMachine.getData(stateMachine.getCurrentState());
        command.execute(data);
        do { 
        System.out.println(stateMachine.getCurrentState().name());
        System.out.println("Please enter a command: \n" +
        "1. initial (i) go to the initial page \n" +
        "2. register (rg) register a patient  \n" +
        "3. check (ck) to check the patient \n" +
        "4. bill (bl) to charge the patient\n" +
        "5. record (rc) for case report   \n" +
        "6. quit (q) to quit the system  \n");
        command = stateMachine.getCommand(scanner.nextLine());
        command.execute();
       
        stateMachine.manageState(command, null);
        } while (!command.equals(stateMachine.getCommand("quit")));
    }

    public static void main(String[] args) {
        new HospitalManagementSystem().start();
    }
}