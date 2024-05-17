package edu.wku.hospital.management.exception;

public class NotFoundException extends Exception{
    private final String resource;
    private final String filePath;

    public NotFoundException(String resource,String filePath ){
        super("Resource: " + resource + " not found at " +filePath);
        this.resource = resource;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
    public String getResource(){
        return resource;
    }
}
