package edu.wku.hospital.management.model;

import java.util.UUID;

import edu.wku.hospital.management.exception.TransactionFailedException;

/**
 * Patient 
 * @author Yiduo Yu
 * @version 1.0
 * @date May 17, 2024
 */
public class Patient extends Person implements Tradable{
    private UUID id;
    private double balance;
    private int age;
    private Gender gender;
    public enum Gender{
        MA,FE
    }

    public Patient(String name, String address, String phone,int age,Gender gender) {
        super(name, address, phone);
        this.id = UUID.randomUUID();
        this.age = age;
        this.gender = gender;
        this.balance = 2000;//every patient should prepay 2000 to get service
    }

    @Override
    public void pay(double amount) throws TransactionFailedException {
        if (amount < balance ) {
            balance -= amount;
        } else {
            throw new TransactionFailedException(id.toString() + ". " +this.getName() + " has insufficient balance.");            
        }

    }

    @Override
    public void charge(double amount) {
        throw new UnsupportedOperationException("YOU CANNOT LET THE PATIENT CHARGE ANYONE AT NOW (not supported yet)"); 
    }
    
}
