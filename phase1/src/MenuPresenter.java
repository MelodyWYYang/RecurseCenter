
import java.io.*;
import java.util.*;



// import university.Student;

//take file in and store each group of lines into a data structure
//afterwards, call presenter methods i.e. MenuPresenter.loginmenu1() which will be the loginmenu
// corresponding to the cell in the data structure
//basically making a state machine, code pulled from:
//https://stackoverflow.com/questions/12218959/how-to-read-certain-portion-of-the-text-file-in-java


//store ea line in an arraylist for the hashmap

// getMenu(key).get[0] .. get[1]... get[2]
// stringbuilder -- //n
public class MenuPresenter {
    private final LinkedHashMap<Integer, ArrayList<String>> menusMap = new LinkedHashMap<Integer, ArrayList<String>>();
    File menu;

    private LinkedHashMap readMenus() {
        try {
            menu = new File("Menu.txt");
            BufferedReader br = new BufferedReader(new FileReader(menu));

            try {
                int menuNum = 0;
                String readBuff = br.readLine();
                // add newline char back into sb
                ArrayList<String> section = new ArrayList<String>();
                while (readBuff != null) {
                    if (readBuff.equals("MENU{")) {
                        readBuff = br.readLine();
                        section.clear();
                        while (!readBuff.equals("}")){
                            section.add(readBuff);
                            readBuff = br.readLine();
                        }
                        menusMap.put(menuNum, section);
                        menuNum ++;
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

    public void printMenu(int menuIndex, int lineIndex) {
        System.out.println(menusMap.get(menuIndex).get(lineIndex));
    }
}
