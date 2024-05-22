package edu.wku.hospital.management.frame;

import java.io.*;
import java.util.*;
import edu.wku.hospital.management.exception.NotFoundException;
import edu.wku.hospital.management.model.Doctor;
import edu.wku.hospital.management.model.Patient;
import edu.wku.hospital.management.service.IOManager;

public class HospitalManagementSystem {
    private Scanner scanner;
    private Map<String, Doctor> doctors;
    private IOManager ioManager = new IOManager();
    private int stage = 0;
    private Doctor admin = null;

    public HospitalManagementSystem() {
        try {
            doctors = (HashMap<String, Doctor>) IOManager.loadSerializable("doctors", "./tmp/doctors");
            File aFile = new File("./tmp/system.txt");
            Scanner fScanner = new Scanner(aFile); 
            int lastInt = 0;
            if (aFile.exists()){
                while (fScanner.hasNext()) {
                    if (fScanner.hasNextInt()) {
                        lastInt = fScanner.nextInt();
                    } else {
                        fScanner.next();
                    }
                }
                fScanner.close();
            }
                stage = lastInt;
        } catch (NotFoundException e) {
            doctors = new HashMap<String, Doctor>();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        scanner = new Scanner(System.in);
    }

    public void start() {
        if (stage==0){
            System.out.println("Welcome to HMS, if you want to register your admin info," +
                "Please input \"register\"  \n (otherwise input any key word to resume) ");
        if (scanner.nextLine().equals("register")) {

            System.out.println("Enter your userID");
            String userID = scanner.nextLine();

            //load doctors from file to check if the userID has been registered
            try {
                doctors = (HashMap<String, Doctor>) IOManager.loadSerializable("doctors", "./tmp/doctors");
            } catch (NotFoundException e) {
                //if the file is not found, create a new hashmap
                doctors = new HashMap<>();
                System.out.println(e.getMessage());
            }
            //check if the userID has been registered
            if (!(doctors==null)) {
                for(Doctor x: doctors.values()){
                    if (x.getUserID().equals(userID)){
                        //if the userID has been registered, print a message and restart the system
                        System.out.println("your userID has been registered, please register again");
                        start();
                        return;
                    }

                }
            }

            //if the userID has not been registered, continue to register
            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            System.out.println("Enter your department:");
            String department = scanner.nextLine();
            System.out.println("Enter your phone number:");
            String phone = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            Doctor doctor = new Doctor(name, department, phone, password, userID);
            doctors.put(doctor.getUserID(), doctor);

            //save the doctor to the file
            this.stage = 1;
            save((Serializable) doctors, "./tmp/doctors","1");
        }

        }
        //if the stage is 1, the system is in the login stage
        if(stage==1){
        System.out.println("Please enter your userID");
        String userID = scanner.nextLine();
        Doctor doctor = doctors.get(userID);

        while (doctor==null){
            System.out.println("Please enter a valid id or enter quit to quit");
            userID = scanner.nextLine();
            doctor = doctors.get(userID);
            if (userID.equals("quit"))
                return;
        }

        System.out.println("Hi, doctor " + doctors.get(userID).getName());
        save(doctor, "./tmp/admin",null);
        admin = doctor;

        System.out.println("Please enter your password");
        String password = scanner.nextLine();
            while(!doctor.checkPassword(password)){
                System.out.println("Your password is incorrect, please enter again, or enter quit to quit");
                password = scanner.nextLine();
                if (password.equals("quit"))
                    return;
            }

            System.out.println("Welcome to HMS !!!");
            //save the stage to the file
            this.stage = 2;
            save(null, "./tmp/doctors","2");
        }
        if(stage==2){
            if(!(resume("admin", "./tmp/admin")==null)){
                admin = (Doctor) resume("admin", "./tmp/admin");
            }
            System.out.println("Welcome back, doctor " + admin.getName());
            System.out.println("enter search (s) to search a patient\n,"+ 
            "enter rg to register new patient \n enter quit(q) to quit");
            String command = scanner.nextLine();
            if (command.equals("s")){
                ArrayList<Patient> patients = admin.getPatients();
                for (Patient x: patients){
                    System.out.print(x.getName()+"\t");
                    System.out.println(x.getId());
                }
                System.out.println("Please enter the patient's id by given name above ");
                String  id = scanner.nextLine();
                for (Patient x: patients){
                    if (x.getId()==id){
                        System.out.println("Searched");
                        System.out.println("Patient's name: " + x.getName());
                        System.out.println("Patient's age: " + x.getAge());
                        this.stage = 3;
                        save(admin, "./tmp/admin", "3");
                    }
                    else{
                        System.out.println("Patient not found");
                        this.stage = 3;
                        save(admin, "./tmp/admin", "3");
                        start();
                        return;
                    }
                }


            }

            else if (command.equals("rg")){
                Patient patient = new Patient("test", "null", "null", 0, Patient.Gender.FE);
                if(!(resume("admin", "./tmp/admin")==null)){
                    admin = (Doctor) resume("admin", "./tmp/admin");
                }
                System.out.println("Please enter the patient's name.");
                String name = scanner.nextLine();
                try {
                    doctors = (HashMap) IOManager.loadSerializable("doctors", "./tmp/doctors");
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                }
                ArrayList<Patient> patientList = admin.getPatients();
                for (int i = 0; i < patientList.size(); i++) {
                    if (((Patient) patientList.get(i)).getName().equals(name)) {
                        System.out.println("The patient is already registered with ID:");
                        System.out.println(((Patient) patientList.get(i)).getId());
                        System.out.println("Make sure that the patient is not registered again, enter back to go back, quit to quit，" +
                                "any input to continue");
                        String quit = scanner.nextLine();
                        if (quit.equals("back")) {
                            start();
                            return;
                        }
                        if (quit.equals("quit")) {
                            return;
                        }
                        System.out.println("OK, continue to register the patient.");
                    }
                }
                System.out.println("Please enter the patient's address.");
                String address = scanner.nextLine();
                System.out.println("Please enter the patient's phone.");
                String phone = scanner.nextLine();
                System.out.println("Please enter the patient's age.");
                int age = scanner.nextInt();
                System.out.println("Please enter the patient's gender f or m.");
                String gender = scanner.nextLine();
                if (gender.equals("f")) {
                     patient = new Patient(name, address, phone, age, Patient.Gender.FE);
                }else {
                     patient = new Patient(name, address, phone, age, Patient.Gender.MA);
                }
                save(patient, "./tmp/patient", "4");
            }
                
            
            
        }
    }


     /*   do {
            System.out.println(stateMachine.getCurrentState().name());
            System.out.println("Please enter a command: \n" +
                    " initial (i) go to the initial page \n" +
                    " register (rg) register a patient  \n" +
                    " check (ck) to check the patient \n" +
                    " bill (bl) to charge the patient\n" +
                    " record (rc) for case report   \n" +
                    " quit (q) to quit the system  \n" +
                    " expire (logout) to log out the system, need you to enter the passwords"
            );

            command = stateMachine.getCommand(scanner.nextLine());
            Serializable o = command.execute();

            stateMachine.manageState(command, o);
        } while (!command.equals(stateMachine.getCommand("quit")));
    }*/

    public Serializable resume(String resource, String file){
        Serializable o = null;
        try {
            o = IOManager.loadSerializable(resource, file);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
            return o;
    }

    public void save(Serializable o , String directory,String x){
        if (!(o==null)) {
            IOManager.saveSerializable(o,directory);
        }
        File aFile = new File("./tmp/system.txt");
        if (!aFile.exists()){
            try {
                aFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if (x!=null) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(aFile);
                fileWriter.write(x);
                fileWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new HospitalManagementSystem().start();
    }

}