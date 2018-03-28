package main.t01.exception;

/**
 * Created by Ekaterina Semenova on 28.03.2018.
 */
public class FileIsNotCreatedException extends Throwable {
    public FileIsNotCreatedException(String filename) {
        System.out.printf("%s can`t be created", filename);
    }
}
