

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
//basically making a state machine, code pulled from:
//https://stackoverflow.com/questions/12218959/how-to-read-certain-portion-of-the-text-file-in-java



public class MenuPresenter {
    private LinkedHashMap readMenus() {
        LinkedHashMap menusMap = new LinkedHashMap();
        File menu = new File("Menu.text");

        try {
            BufferedReader br = new BufferedReader(new FileReader(menu));

            try {
                int menuNum = 1;
                String readBuff = br.readLine();
                String section = "";
                while (readBuff != null) {
                    if (section.equals("MENU{") && !readBuff.equals("}")) {
                        menusMap.put(menuNum, readBuff);
                        menuNum += 1;
                    } else if (readBuff.equals("}")) {
                        section = "";
                    }
                    readBuff = br.readLine();
                }

            } finally {
                br.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error occured, please try again later.");
            e.printStackTrace();
        }
        return menusMap;
    }
}
