import java.io.*;
import java.util.HashMap;

public class DataBase {
    private final HashMap<String, Controller> dataBase = new HashMap<>();
    static private DataBase instance;

    static public DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    void addDataBase(String str, Controller controller) {
        dataBase.put(str, controller);
    }

    Controller getController(String str) {
        return dataBase.get(str);
    }
}

