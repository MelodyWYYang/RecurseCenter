
import java.io.*;
import java.util.*;
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


//store ea line in an arraylist for the hashmap
public class MenuPresenter {
    private final LinkedHashMap<Integer, ArrayList<String>> menusMap = new LinkedHashMap<Integer, ArrayList<String>>();
    File menu = new File("Menu.txt");

    private LinkedHashMap readMenus() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(menu));

            try {
                int menuNum = 1;
                String readBuff = br.readLine();
                // add newline char back into sb
                StringBuilder section = new StringBuilder();
                while (readBuff != null) {
                    if (readBuff.equals("MENU{")) {
                        section.append(readBuff);
                        menusMap.get(menuNum).add(section);
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

    public void getMenu(int num) {
        System.out.println(menusMap.get(num));
    }
}
