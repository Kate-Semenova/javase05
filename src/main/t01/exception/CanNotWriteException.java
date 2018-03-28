package main.t01.exception;

/**
 * Created by Ekaterina Semenova on 28.03.2018.
 */
public class CanNotWriteException extends Throwable {
    public CanNotWriteException(String name) {
        System.out.printf("%s can`t be edited", name);
    }
}
