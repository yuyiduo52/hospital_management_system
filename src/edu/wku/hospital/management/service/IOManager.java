package edu.wku.hospital.management.service;
import javax.swing.*;
import java.io.File;


/**
 * This class is used to manage the input and output of the system
 *@author Yiduo Yu
 *@version 1.0
 *@since 5-17-2024
 *
 */
public class IOManager {
    //ALERT: IN THIS PROGRAM, HOME_DIRECTORY IS THE DESKTOP
    private final static File HOME_DIRECTORY = new File(System.getProperty("user.home"), "Desktop");
    private final static File WORKING_DIRECTORY = new File(System.getProperty("user.dir"));
    private File currentDirectory = WORKING_DIRECTORY;


    /**
     * This method is used to choose a file from the file system
     * @return the file that the user selected
     */
    public static File chooseFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String folderPath = fileChooser.getSelectedFile().getPath();
            return new File(folderPath);
        } else {
            // User cancelled the file chooser or an error occurred
            return null;
        }
    }

    /**
     * This method is used to choose a directory from the file system
     * @return the directory that the user selected
     */
    public void changeDirectory(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String folderPath = fileChooser.getSelectedFile().getPath();
            currentDirectory = new File(folderPath);
        } else {
           return;
        }
       
    }

    /**
     * this method is a helper method to searchGivenInfo(String message)
     * @param message is the designated object we want to search (directory or file name)
     * @param mode mode 1: directory, mode 2: file name
     * @return the searchedFile
     */
    public File searchGivenInfo(String message, int mode){
        switch (mode){
        case 1: break;
        case 2: break;
        }

        return new File("");
    }

    /**
     * This method is used to search the designated object in the current directory
     * @param message is the designated object we want to search (directory or file name)
     * @return the searchedFile
     */
    public File searchGivenInfo(String message){
        if (message.contains("/"))
           return searchGivenInfo(message,1);
        else
          return searchGivenInfo(message,2);

    }
}