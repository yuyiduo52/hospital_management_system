package edu.wku.hospital.management.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import edu.wku.hospital.management.exception.NotFoundException;
import edu.wku.hospital.management.exception.TransactionFailedException;
import edu.wku.hospital.management.model.Doctor;
import edu.wku.hospital.management.model.Hospital;
import edu.wku.hospital.management.model.Patient;

/**
 * This class contains the commands that can be executed in the system.
 * Each command is a class that implements the Command interface.
 *       INITIAL, 
 *       PATIENT_REGISTRATION, 
 *       DOCTOR_CHECK, 
 *       BILLING, 
 *       CASE_RECORDING,
 *       QUIT, 
 * @author Yiduo Yu
 * @version 1.0
 * @since 5-17-2024
 */

//实现所有的命令
public class Commands {
    IOManager ioManager = new IOManager();

    
    public static class Initial implements Command, Serializable{
        HashMap<String,Doctor> doctors;
        public Initial() {
            try {
                doctors = (HashMap<String,Doctor>)IOManager.loadSerializable("doctors", "./tmp/doctors");
            } catch (NotFoundException e) {
                System.out.println("Please register as a doctor first");
            }
        }

        //退出后载入历史数据
        @Override
        public Serializable execute(List<Serializable> data) {
            if (data==null) {
                System.out.println("hi, doctor. Welcome");
                return null;
            }
            ArrayList datas = (ArrayList) data;
            System.out.println("Hi, " + ((Doctor)datas.get(0)).getName());
            return null;
        }

        //检查密码
        @Override
        public Serializable execute() {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter your userID");
            String userID = input.nextLine();
            try {
                doctors = (HashMap<String,Doctor>)IOManager.loadSerializable("doctors", "./tmp/doctors");
            } catch (NotFoundException e) {
                System.out.println(e.toString());
            }

            Doctor doctor = doctors.get(userID);
            if (doctor==null){
                System.out.println("Please enter a valid id");
                execute();
                return doctor;
            }
            System.out.println("Please enter your password");
            String password = input.nextLine();

                while(!doctor.checkPassword(password)){
                    System.out.println("Your password is wrong, please enter again");
                    password = input.nextLine();
                }
                System.out.println("Welcome to HMS !!!");

            return doctor;
        }
    }

    //医生名下的患者
    public static class Register implements Command, Serializable{
        @Override
        public Serializable execute(List<Serializable> data) {
            Scanner scanner = new Scanner(System.in);
            List li = data;
            Patient patient = (Patient) data.get(0);
            System.out.println("You have already register a patient " + patient.getName());
            System.out.println("Please enter any key to get to the main menu");
            String input = scanner.nextLine();

            return (Serializable) patient;
        }

        @Override
        public Serializable execute() {
            // TODO Auto-generated method stub
            Scanner scanner = new Scanner(System.in);
            Patient patient = new Patient("null", "null", "null", 0, Patient.Gender.FE);

            System.out.println("Please enter the patient's name.");
            String name = scanner.nextLine();
            HashMap doctors = new HashMap();
            try {
                doctors = (HashMap) IOManager.loadSerializable("doctors", "./tmp/doctors");
            } catch (NotFoundException e) {
                System.out.println(e.toString());
            }
            System.out.println("enter the doctor's id");
            String id = scanner.nextLine();
            if(doctors.get(id)!=null){
            for (int i = 0; i < ((Doctor) doctors.get(id)).getPatients().size(); i++) {
                if (((Doctor) doctors.get(id)).getPatients().get(i).getName().equals(name)) {
                    System.out.println("The patient is already registered");
                    return (Serializable) ((Doctor) doctors.get(id)).getPatients().get(i);
                }
            }
            }else {
                System.out.println("you input a wrong id");
                return null;
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
            ((Doctor)doctors.get(id)).getPatients().add(patient);
                IOManager.saveSerializable( patient, "./tmp/patient");

            return   patient;
        }

    }


    public static class Check implements Command, Serializable{
        @Override
        public Serializable execute(List<Serializable> data) {
            Scanner scanner = new Scanner(System.in);
            List li = data;
            String str = (String) li.get(0);
            System.out.println("You have record the patient's information, the file is located at ./tmp/records.txt " );
            System.out.println("enter any key to resume to main menu, and you are supposed to charge the patient");
            scanner.nextLine();
            return null;
        }

        @Override
        public Serializable execute() {
            JFrame frame = new JFrame("Record patient information");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 150);

            // 创建文本字段用于输入
            JTextField textField = new JTextField(20);

            // 创建按钮
            JButton submitButton = new JButton("Submit");

            // 创建标签用于显示输入结果
            JLabel resultLabel = new JLabel("You entered: ");

            // 添加按钮点击事件监听器
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String desc = textField.getText();
                    resultLabel.setText("You entered: " + desc);

                    File file = new File("./tmp/records.txt");
                    if (!file.exists()) {
                        try {
                            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
                            file.createNewFile();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                        writer.write(desc);
                        writer.newLine(); // Add a new line after writing the description
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // 创建面板并添加组件
            JPanel panel = new JPanel();
            panel.add(textField);
            panel.add(submitButton);
            panel.add(resultLabel);

            // 将面板添加到框架并设置框架可见
            frame.getContentPane().add(panel);
            frame.setVisible(true);

            return null;
        }
    }


    //患者记账
    public static class Bill implements Command, Serializable{
        @Override
        public Serializable execute(List<Serializable> data) {
                return null;

        }

        @Override
        public Serializable execute() {
            Hospital hospital = new Hospital();
            Patient patient = new Patient("null","null","null",0, Patient.Gender.FE);
            try {
                patient = (Patient) IOManager.loadSerializable("patient","./tmp/patient");
            } catch (NotFoundException e) {
                System.out.println(e.toString());
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the bill.");
            double amount = scanner.nextDouble();
            try {
                hospital.charge(patient,amount);
                System.out.println(patient.getBalance());
            } catch (TransactionFailedException e) {
                System.out.println("Patient's balance is not enough");
            }
            return null;
        }
    }

    //记录患者病例
    public static class Record implements Command, Serializable {
        @Override
        public Serializable execute(List<Serializable> data) {
            return null;
        }

        @Override
        public Serializable execute() {
            File aFile = IOManager.getHomeDirectory();
            File recordsFolder = new File(aFile, "records");

            // Check if the 'records' subfolder exists and is a directory
            if (!recordsFolder.exists() || !recordsFolder.isDirectory()) {
                recordsFolder.mkdir();
                System.out.println("The 'records' subfolder is located at." + recordsFolder.getAbsolutePath());
            }

            // Specify the source file path
            Path sourcePath = Paths.get("./tmp/records.txt");

            // Specify the target file path
            Path targetPath = Paths.get(recordsFolder.getAbsolutePath(), "records.txt");

            try {
                // Move the file
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File moved successfully.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return null;
        }
    }

        //退出系统
        public static class Quit implements Command, Serializable {
            @Override
            public Serializable execute(List<Serializable> data) {
                System.out.println("You have quit the system");
                return null;
            }

            @Override
            public Serializable execute() {
                System.out.println("You have quit the system");
                return null;
            }
        }

        //account log out
        public static class Expire implements Command, Serializable {
            @Override
            public Serializable execute(List<Serializable> data) {
                System.out.println("You have logged out");
                return null;
            }

            @Override
            public Serializable execute() {
                System.out.println("You have logged out");
               return null;
            }
        }

}
