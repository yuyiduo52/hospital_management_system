package edu.wku.hospital.management.service;

import java.io.Serializable;
import java.util.List;


//接口，定义执行命令
public interface Command  {
    Serializable execute(List<Serializable> data);
    Serializable execute();
    
}