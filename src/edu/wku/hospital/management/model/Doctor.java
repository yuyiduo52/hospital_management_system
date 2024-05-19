package edu.wku.hospital.management.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * Doctor 
 * @author Yiduo Yu
 * @version 1.0
 * @date May 17, 2024
 */
//医生信息

public class Doctor extends Person implements Serializable {
    private UUID id;
    //private String name;//为什么 super.name() 得到的是null
    private String userID;
    private ArrayList<Patient> patients = new ArrayList<>();
    private String passwordHash;

    public Doctor(String name, String department, String phone, String password,String userID) throws NoSuchAlgorithmException {
        super(name, department, phone);
        this.id = UUID.randomUUID();
        this.passwordHash = hashPassword(password);
        this.userID = userID;
        //this.name = name;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your userID");
        String userID = scanner.nextLine();
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your department:");
        String department = scanner.nextLine();
        System.out.println("Enter your phone number:");
        String phone = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        Doctor doctor = new Doctor(name, department, phone, password, userID);
        System.out.println(doctor.getName());
    }



    public Doctor(){
        super("dr. x","null","null");
    }
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public String getDoctorId() {
        return id.toString();
    }

    public void setDoctorId(String doctorId) {
        this.id = UUID.fromString(doctorId);
    }

    public String getName() {
        return super.getName();//super.getName()
    }

    public void setName(String name) {
        super.setName(name);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        return this.passwordHash.equals(hashPassword(password));
    }
}
    
