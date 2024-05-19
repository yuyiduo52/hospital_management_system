package edu.wku.hospital.management.exception;

//搜索时重复（文件重复）
public class DuplicatedException extends Exception{
    public DuplicatedException(String message){
        super(message);
    }
    
}
