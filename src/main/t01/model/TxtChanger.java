package main.t01.model;

import main.t01.exception.CantWriteException;

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
                this.file = null;
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

    public boolean addLine(String line) throws CantWriteException {
        if (file != null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                stringBuilder.append(line).append("\n");
                bufferedWriter.write(stringBuilder.toString());
                return true;
            } catch (IOException e) {
                throw new CantWriteException(file.getName());
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
