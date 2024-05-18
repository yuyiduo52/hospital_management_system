package edu.wku.hospital.management.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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

public class Commands {
    IOManager ioManager = new IOManager();

    
    public static class Initial implements Command, Serializable{
        private Properties config;

        public Initial() {
            config = new Properties();
            try {
                FileInputStream in = new FileInputStream("  ./temp/config.properties");
                config.load(in);
                in.close();
                System.out.println(config.getProperty("password"));
            } catch (IOException e) {
                System.out.println("Error reading config file: " + e.getMessage());
            }
    }


        @Override
        public void execute(List<Serializable> data) {
            
        }

        @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your password:");
        String inputPassword = scanner.nextLine();

        String correctPassword = config.getProperty("password");

        if (inputPassword.equals(correctPassword)) {
            System.out.println("Access granted.");
        } else {
            System.out.println(config.getProperty("password"));
            System.out.println("Access denied.");
        }
    }
    }

    public static class Register implements Command, Serializable{
        @Override
        public void execute(List<Serializable> data) {
            // Define what should be done in the PATIENT_REGISTRATION state
        }

        @Override
        public void execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    }

    public static class Check implements Command, Serializable{
        @Override
        public void execute(List<Serializable> data) {
            // Define what should be done in the DOCTOR_CHECK state
        }

        @Override
        public void execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    }

    public static class Bill implements Command, Serializable{
        @Override
        public void execute(List<Serializable> data) {
            // Define what should be done in the BILLING state
        }

        @Override
        public void execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    }

    public static class Record implements Command, Serializable {
        @Override
        public void execute(List<Serializable> data) {
            // Define what should be done in the CASE_RECORDING state
        }

        @Override
        public void execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    }

    public static class Quit implements Command, Serializable{
        @Override
        public void execute(List<Serializable> data) {
            // Define what should be done in the QUIT state
        }

        @Override
        public void execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    }

    public static class Expire implements Command, Serializable {

        @Override
        public void execute(List<Serializable> data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }

        @Override
        public void execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
        }
    
        
    }
}