package edu.wku.hospital.management.frame;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import edu.wku.hospital.management.exception.DuplicatedException;
import edu.wku.hospital.management.exception.NotFoundException;
import edu.wku.hospital.management.model.Doctor;
import edu.wku.hospital.management.service.Command;
import edu.wku.hospital.management.service.Commands;
import edu.wku.hospital.management.service.IOManager;

//运行框架，界面
public class HospitalManagementSystem {
    private StateMachine stateMachine;
    private Scanner scanner;
    private Map<String,Doctor> doctors;

    //框架构造器
    public HospitalManagementSystem() {
        stateMachine = new StateMachine(StateMachine.State.INITIAL);
        try {
            doctors = (HashMap<String,Doctor>)IOManager.loadSerializable("doctors", "./tmp/doctors");
        } catch (NotFoundException e) {
            doctors = new HashMap<String ,Doctor>();
        }
        //load state machine from temp file
        if (stateMachine.load() != null) {
            stateMachine = stateMachine.load();
        } else {
            stateMachine = new StateMachine(StateMachine.State.INITIAL);
        }
        scanner = new Scanner(System.in);
    }

    public void start() {
        Command command = stateMachine.getCommands().get(stateMachine.getCurrentState());
        System.out.println("Welcome to HMS, if you want to register your admin info,"+
        "Please input \"register\" \n (otherwise input any key word to resume) ");
        
        if (scanner.nextLine().equals("register")) {

            System.out.println("Enter your userID");
            String userID = scanner.nextLine();

            //比对id
                try {
                    HashMap<String,Doctor> doctors = (HashMap<String, Doctor>) IOManager.loadSerializable("doctors", "./tmp/doctors");
                    doctors.forEach((key,value) -> {
                        if (userID.equals(value.getUserID()))
                            try {
                                throw new DuplicatedException(" your userID is duplicated, please choose another one");
                            } catch (DuplicatedException e) {
                                System.out.println(e.getMessage());
                                new Commands.Initial().execute();
                            }
                    });
                } catch (NotFoundException e) {
                    System.out.println("Please register as a doctor first");
            }

            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            System.out.println("Enter your department:");
            String department = scanner.nextLine();
            System.out.println("Enter your phone number:");
            String phone = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            try{
            Doctor doctor = new Doctor(name, department, phone, password, userID);
            doctors.put(doctor.getUserID(),doctor);
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
        " initial (i) go to the initial page \n" +
        " register (rg) register a patient  \n" +
        " check (ck) to check the patient \n" +
        " bill (bl) to charge the patient\n" +
        " record (rc) for case report   \n" +
        " quit (q) to quit the system  \n"+
        " expire (logout) to log out the system, need you to enter the passwords"
        );

        command = stateMachine.getCommand(scanner.nextLine());
        Serializable o = command.execute();
       
        stateMachine.manageState(command, o);
        } while (!command.equals(stateMachine.getCommand("quit")));
    }

    public static void main(String[] args) {
        new HospitalManagementSystem().start();
    }
}