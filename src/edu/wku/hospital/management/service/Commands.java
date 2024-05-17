package edu.wku.hospital.management.service;

import java.io.Serializable;
import java.util.List;

import edu.wku.hospital.management.frame.Command;

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
        @Override
        public void execute(List<Serializable> data) {
            // Define what should be done in the INITIAL state
        }

        @Override
        public void execute() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'execute'");
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
}