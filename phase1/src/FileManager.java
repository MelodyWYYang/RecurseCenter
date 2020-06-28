import java.io.*;
import java.util.Scanner;

public class FileManager {
    //author: Callan Murphy in group 0110 for CSC207H1 summer 2020 project
    //used provided code in file StudentManager.java as a reference
    //used https://www.tutorialspoint.com/java/java_serialization.htm as a reference

    public static void saveUserToFile(User usr) {
        try {
            FileOutputStream file = new FileOutputStream("/serFiles/" + usr.username + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(usr);
            out.close();
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User loadUserFromFile(String fileName) {
        //need to decide if fileName should be whole path, just the file, or just the name without .ser;
        //is currently the whole path
        User usr;
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            usr = (User) in.readObject();
            in.close();
            file.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return usr;
    }
}