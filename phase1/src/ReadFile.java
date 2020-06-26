import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    //author: Callan Murphy in group 0110 for CSC207H1 summer 2020 project
    //used provided code in file StudentManager.java as a reference
    //also used https://www.w3schools.com/java/java_files_read.asp as a reference

    public static void main(String fileName) {
        try {
            File file = new File(fileName);
            Scanner newScanner = new Scanner(file);
            while (newScanner.hasNextLine()) {
                String data = newScanner.nextLine();
                System.out.println(data);
            }
            newScanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot read file.");
        }
    }
}
