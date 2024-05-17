package edu.wku.hospital.management.frame;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public interface Command  {
    void execute(List<Serializable> data);
    void execute();
}