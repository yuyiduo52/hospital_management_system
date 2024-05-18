package edu.wku.hospital.management.frame;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import edu.wku.hospital.management.model.Doctor;
import edu.wku.hospital.management.service.Command;
import edu.wku.hospital.management.service.IOManager;

public class HospitalManagementSystem {
    private StateMachine stateMachine;
    private Scanner scanner;
    private Set<Doctor> doctors;


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
        System.out.println("Welcome to the system, if you want to register your admin info,"+ 
        "input register\n otherwise input any key to resume ");
        
        if (scanner.nextLine().equals("register")) {
            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            System.out.println("Enter your address:");
            String address = scanner.nextLine();
            System.out.println("Enter your phone number:");
            String phone = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();
            try{
            Doctor doctor = new Doctor(name, address, phone, password);
            doctors.add(doctor);
            IOManager.saveSerializable((Serializable) doctors, "./tmp/doctors");
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
            // Now you can use the 'doctor' object for further operations
        }

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
        "6. quit (q) to quit the system  \n"+
        "7. expire (logout) to log out the system, need you to enter the passwords"
        );

        command = stateMachine.getCommand(scanner.nextLine());
        command.execute();
       
        stateMachine.manageState(command, null);
        } while (!command.equals(stateMachine.getCommand("quit")));
    }

    public static void main(String[] args) {
        new HospitalManagementSystem().start();
    }
}