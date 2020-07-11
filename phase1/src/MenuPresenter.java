

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


// import university.Student;

//take file in and store each group of lines into a data structure
//afterwards, call presenter methods i.e. MenuPresenter.loginmenu1() which will be the loginmenu
// corresponding to the cell in the data structure


public class MenuPresenter {
    private LinkedHashMap separateMenus() {
        LinkedHashMap menusMap = new LinkedHashMap();
        File menu = new File("Menu.text");
        try (BufferedReader br = new BufferedReader(new FileReader(menu))) {
            FileReader fr = new FileReader(menu);
            Scanner scanner = new Scanner(fr);
            while (menu.exists() && scanner.hasNextLine()) {
                String str = scanner.nextLine();
                System.out.println(str);
                String next = scanner.nextLine();
                while (! next.contains("MENU")){
                    System.out.println(next);
                } scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        }
    }
    public static void readMenu(String prompt) {

    }
}

