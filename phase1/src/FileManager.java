import java.io.*;
import java.util.ArrayList;

public class FileManager {
    //author: Callan Murphy in group 0110 for CSC207H1 summer 2020 project
    //used provided code in file StudentManager.java as a reference
    //used https://www.tutorialspoint.com/java/java_serialization.htm
    // as a reference for saveUserToFile and loadUserFromFile
    //used https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
    //as a reference for iterating over files in loadAllUsers

    public static void saveUserToFile(User usr) {
        //serializes the given user's information in a .ser file with the title of their username
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
        //loads the given .ser file and returns a deserialized user object
        //need to decide if fileName should be whole path, just the file, or just the name without .ser;
        //is currently just the file
        User usr;
        try {
            FileInputStream file = new FileInputStream("/serFiles/" + fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            usr = (User) in.readObject();
            in.close();
            file.close();
        }
        catch (IOException | ClassNotFoundException e) {
            // ClassNotFoundException if User class is not recognized
            e.printStackTrace();
            return null;
        }
        return usr;
    }

    public static ArrayList<User> loadAllUsers(String directory) throws Exception {
        //allows all .ser user files in directory to be deserialized into the program and returned as an ArrayList
        //depends on loadUserFromFile in order to deserialize each user
        ArrayList<User> userList = new ArrayList<>();
        File dir = new File(directory);
        File[] directoryFiles = dir.listFiles();
        if (directoryFiles != null) {
            for (File child : directoryFiles) {
                userList.add(loadUserFromFile(child.getName()));
            }
        }
        else {
            throw new Exception("Directory does not exist"); //unsure of error handling: maybe use try/catch?
        }
        return userList;
    }
}