package main.t01.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * Created by Ekaterina Semenova on 17.03.2018.
 */
public class DirectoryChooser {
    private File directory;
    private File[] filesArray;

    public DirectoryChooser(String path)  {
        if (new File(path).isDirectory()) {
            directory = new File(path);
            filesArray = directory.listFiles();
        }
    }

    public File[] getFilesArray() {
        return filesArray;
    }

    public File[] getFilesArray(String format)  {
        return directory.listFiles(file -> file.getPath().endsWith(format));
    }

    public boolean createNewFile(String filename) {
        File file = new File(directory.getPath() + "\\" + filename +  ".txt");
        try {
            if (file.createNewFile()) {
                System.out.println(String.format("Файл %s создан", filename + ".txt"));
                return true;
            }
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            filesArray = directory.listFiles();
        }
    }

    public boolean deleteFile(File file) {
        boolean result = file.delete();
        if (result) {
            filesArray = directory.listFiles();
            System.out.println(String.format("Файл %s удален", file.getName()));
        }
        return result;
    }

    public void changeDirectory(String path) throws NoSuchFileException {
        File file = new File(path);
        if (file.exists()) {
            directory = file;
            filesArray = directory.listFiles();
        } else {
            throw new NoSuchFileException(file.getAbsolutePath());
        }
    }


    public String getDirectoryPath() {
        return directory.getAbsolutePath();
    }
}
