import java.io.*;
import java.util.Scanner;

public class FileManager {
    //author: Callan Murphy in group 0110 for CSC207H1 summer 2020 project
    //used provided code in file StudentManager.java as a reference
    //used https://www.tutorialspoint.com/java/java_serialization.htm as a reference

    public static void saveUserToFile(User usr) {
        try {
            FileOutputStream file = new FileOutputStream("/files/" + usr.username + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(usr);
            out.close();
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}