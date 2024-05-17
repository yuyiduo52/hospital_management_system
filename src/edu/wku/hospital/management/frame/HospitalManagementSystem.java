package edu.wku.hospital.management.frame;

import java.util.Scanner;

public class HospitalManagementSystem {
    private StateMachine stateMachine;
    private Scanner scanner;

    public HospitalManagementSystem() {
        //load state machine from temp file
        if (StateMachine.load() != null) {
            stateMachine = StateMachine.load();
        } else {
            stateMachine = new StateMachine(StateMachine.State.INITIAL.name());
        }
        scanner = new Scanner(System.in);
    }

    public void start() {
        Command command = stateMachine.getCommand(stateMachine.getState());
        do {
            if (command != null ) {
                command.execute();
            } else {
                System.out.println("Command:");
            }
        } while (!command.equals(stateMachine.getCommand(StateMachine.State.QUIT.name())));
    }

    public static void main(String[] args) {
        new HospitalManagementSystem().start();
    }
}