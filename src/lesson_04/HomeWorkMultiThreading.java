package lesson_04;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomeWorkMultiThreading {
    public static void main(String[] args) {
//        task1Start();
//        task2Start();
//        task3Start();
    }

    //region Task1
    private static volatile Character currentChar = 'A';
    private static final Object monitor = new Object();
    private static int numberOfIterations = 5;

    private static Thread thread1 = new Thread(() -> {
        for (int i = 0; i < numberOfIterations; i++) {
            synchronized (monitor) {
                while (currentChar != 'A') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(currentChar);
                currentChar = 'B';
                monitor.notifyAll();
            }
        }
    });
    private static Thread thread2 = new Thread(() -> {
        for (int i = 0; i < numberOfIterations; i++) {
            synchronized (monitor) {
                while (currentChar != 'B') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(currentChar);
                currentChar = 'C';
                monitor.notifyAll();
            }
        }
    });
    private static Thread thread3 = new Thread(() -> {
        for (int i = 0; i < numberOfIterations; i++) {
            synchronized (monitor) {
                while (currentChar != 'C') {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(currentChar);
                if(i+1 < numberOfIterations)System.out.print("_");
                currentChar = 'A';
                monitor.notifyAll();
            }
        }
    });
    private static synchronized void task1Start(){
        thread1.start();
        thread2.start();
        thread3.start();
    }

    //endregion

    //region Task2
    private static FileOutputStream fileOutputStream = null;
    private static int numberOfIterationsTask2 = 10;

    private static void task2Start(){
        initFileOutputStream();
        new Thread(() -> writeToFile("Hello, I'm thread 1\r\n".getBytes())).start();
        new Thread(() -> writeToFile(("Hello, I'm thread 2\r\n").getBytes())).start();
        new Thread(() -> writeToFile("Hello, I'm thread 3\r\n".getBytes())).start();
    }

    private static void initFileOutputStream(){
        File fileToWrite = new File("MultithreadedWriting.txt");
            try {
                if(!fileToWrite.exists()) fileToWrite.createNewFile();
                fileOutputStream = new FileOutputStream(fileToWrite);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //Не стал синхронизировать раз поток записи синхронизирован
    private static void writeToFile(byte[] bytes){
        for (int i = 0; i < numberOfIterationsTask2; i++) {
            try {
                if (fileOutputStream != null) fileOutputStream.write(bytes);
                Thread.sleep(20);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region Task3
    private static void task3Start(){
        MultiFunctionDevice multiFunctionDevice = new MultiFunctionDevice();
        int numberOfDocuments = 30 + (int)(Math.random()*50);
        for (int i = 0; i < numberOfDocuments; i++) {
            multiFunctionDevice.addToQueue(new Document());
        }
        multiFunctionDevice.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        multiFunctionDevice.stop();
    }
    //endregion
}
