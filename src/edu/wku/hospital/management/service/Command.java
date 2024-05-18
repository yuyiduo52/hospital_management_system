package edu.wku.hospital.management.service;

import java.io.Serializable;
import java.util.List;

public interface Command  {
    void execute(List<Serializable> data);
    void execute();
    
}