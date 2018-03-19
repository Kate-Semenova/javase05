package main.t01;


import main.t01.model.DirectoryChooser;
import main.t01.model.TxtChanger;

import java.io.File;

import java.util.Scanner;

/**
 * Created by Ekaterina Semenova on 17.03.2018.
 * Разработать приложение, позволяющее просматривать файлы и каталоги файловой системы,
 * а также создавать и удалять текстовые файлы.
 * Для работы с текстовыми файлами необходимо реализовать функциональность записи (дозаписи) в файл.
 * Требуется определить исключения для каждого слоя приложения и корректно их обработать.
 */
public class App {
    public static void main(String[] args) {
        DirectoryChooser directoryChooser = null;
        String path = "src\\file";
        directoryChooser = new DirectoryChooser(path);

        System.out.println(String.format("============List of *.txt files from %s==============", path));

        File[] filesTXT = directoryChooser.getFilesArray("txt");
        for (int i = 0; i < filesTXT.length; i++) {
            System.out.println(i + 1 + " - " + filesTXT[i].getName());
        }


        System.out.println("=========Choose what file you wanna read=========");
        Scanner in = new Scanner(System.in);
        int fileNumber;
        for (fileNumber = in.nextInt(); filesTXT.length < fileNumber; fileNumber = in.nextInt()) {
            System.out.println("Print the number correctly");
        }
        System.out.printf("=========text from the file \"%s\"=========\n", filesTXT[fileNumber - 1].getName());
        TxtChanger txtChanger = new TxtChanger(filesTXT[fileNumber - 1]);
        System.out.println(txtChanger.getText());
        System.out.println("==========Add some text=============");
        Scanner in2 = new Scanner(System.in);
        String string = in2.nextLine();
        txtChanger.addLine(string);
        System.out.println("============Let`s see what is in the file===============");
        System.out.println(txtChanger.getText());

        System.out.println("============Let`s create new file. Enter the name==============");
        in = new Scanner(System.in);
        String result = "unsuccessful\nEnter another name";
        do {
            String str = in.nextLine();
            System.out.println("Trying to create file " + str);
            if (directoryChooser.createNewFile(str)) {
                result = "successful";
            }
            System.out.println(result);
        }
        while (result.equals("unsuccessful\nEnter another name"));

        System.out.println("========let`s delete file=======");
        System.out.println("Choose file number:");

        filesTXT = directoryChooser.getFilesArray("txt");
        for (int i = 0; i < filesTXT.length; i++) {
            System.out.println(i + 1 + " - " + filesTXT[i].getName());
        }
        in = new Scanner(System.in);
        for (fileNumber = in.nextInt(); filesTXT.length < fileNumber; fileNumber = in.nextInt()) {
            System.out.println("Print the number correctly");
        }
        if(directoryChooser.deleteFile(filesTXT[fileNumber - 1])){
            System.out.println("File is deleted");
        }else {
            System.out.println("File is not deleted");
        }

    }


}
