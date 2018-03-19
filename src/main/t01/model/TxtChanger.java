package main.t01.model;

import java.io.*;

/**
 * Created by Ekaterina Semenova on 17.03.2018.
 */
public class TxtChanger {
    private File file;
    private StringBuilder stringBuilder = new StringBuilder();
    private final String TXT = "txt";

    public TxtChanger(File file) {

        if (file.getPath().endsWith(TXT)) {
            this.file = file;
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                bufferedReader.lines().forEach(line -> stringBuilder.append(line).append("\n"));

            } catch (FileNotFoundException e) {
                file = null;
                e.printStackTrace();
            }

        }
    }

    public TxtChanger(String filename) {
        this(new File(filename));
    }


    public String getText() {
        return stringBuilder.toString();
    }

    public boolean addLine(String line) {
        if (file != null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                stringBuilder.append(line).append("\n");
                bufferedWriter.write(stringBuilder.toString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public String getFileName() {
        return file.getName();
    }

    public File getFile() {
        return file;
    }
}
