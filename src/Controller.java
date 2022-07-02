import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

class Controller {
    private final File file;
    private RandomAccessFile raf;
    private FileWriter fw;

    public Controller(String str) {
        file = new File(str);
        try {
            raf = new RandomAccessFile(file, "rw");
            String last = readFile();
            fw = new FileWriter(file);
            writeFile(last);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void writeFile(String str, boolean... reset) {
        try {
            if (reset.length != 0) {
                fw = new FileWriter(file);
            }
            fw.write(str);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFile() {
        StringBuilder recovery = new StringBuilder();
        String str;
        try {
            while ((str = raf.readLine()) != null) {
                recovery.append(str).append("\n");
            }
            raf.seek(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recovery.toString();
    }


}

