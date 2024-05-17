package edu.wku.hospital.management.model;

import edu.wku.hospital.management.exception.TransactionFailedException;
/**
 * Tradable
 * @version 1.0
 * @date May 17, 2024
 */
public interface Tradable {
    public void pay(double amount) throws TransactionFailedException;
    public void charge(double amount);    
}