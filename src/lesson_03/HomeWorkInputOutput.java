package lesson_03;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HomeWorkInputOutput {

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
//        //region Task1
//        task1ReadArrayOfBytesFromFile();
//        //endregion
//
//        //region Task2
//        task2WriteFiveFilesInOne();
//        //endregion

//        //region Task3
//        ConsolePagePrinter consolePagePrinter = new ConsolePagePrinter();
//        consolePagePrinter.start();
//        consolePagePrinter.stop();
        //endregion

        closeConsoleBufferedReader();
    }

    //region Task1
    public static void task1ReadArrayOfBytesFromFile() {
        System.out.println("Задание 1.");
        System.out.println("Введите название файла или пустую строку, чтобы прочитать файл по умолчанию");
        String fileName = readingConsoleInput();
        FileInputStream fileInputStream = null;
        byte[] bytes;
        try {
            fileInputStream = new FileInputStream(!fileName.isEmpty() ? fileName : "50bytes.txt");
            bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            System.out.println(Arrays.toString(bytes));
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Task2
    private static void task2WriteFiveFilesInOne(){
        System.out.println("Задание 2.");
        System.out.println("Введите последовательно 5 названий файлов" +
                " или пустую строку, чтобы прочитать файлы по умолчанию");
        ArrayList<InputStream> inputStreams = new ArrayList<>();
        String[] fileNames = {"first.txt","second.txt","third.txt","fourth.txt","fifth.txt"};
        String line;
        try {
            line = readingConsoleInput();
            for (int i = 0; i < 5; i++) {
                if(line.isEmpty()) {
                    inputStreams.add(new FileInputStream(fileNames[i]));
                }else{
                    line = readingConsoleInput();
                    inputStreams.add(new FileInputStream(line));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(inputStreams));
        System.out.println("Введите имя файла для записи содержимого пяти файлов" +
                "или пустую строку, чтобы записать в файл по умолчанию");
        line = readingConsoleInput();
        byte[] buffer = new byte[1024];
        try (FileOutputStream fileOutputStream = new FileOutputStream(!line.isEmpty() ? line : "unitedFile.txt")) {
            int data;
            while ((data = sequenceInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer,0,data);
            }
            sequenceInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Объединенный файл создан");
    }
    //endregion

    private static String readingConsoleInput() {
        String result = "";
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void closeConsoleBufferedReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
