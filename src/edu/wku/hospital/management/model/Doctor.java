package edu.wku.hospital.management.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;



/**
 * Doctor 
 * @author Yiduo Yu
 * @version 1.0
 * @date May 17, 2024
 */
public class Doctor extends Person implements Serializable {
    private UUID id;
    private ArrayList<Appointment> patients = new ArrayList<>();
    private String name;
    private String passwordHash;

    public Doctor(String name, String address, String phone, String password) throws NoSuchAlgorithmException {
        super(name, address, phone);
        this.id = UUID.randomUUID();
        this.passwordHash = hashPassword(password);

    }

    public String getDoctorId() {
        return id.toString();
    }

    public void setDoctorId(String doctorId) {
        this.id = UUID.fromString(doctorId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
