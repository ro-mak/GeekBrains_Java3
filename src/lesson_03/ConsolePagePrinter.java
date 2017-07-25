package lesson_03;

import java.io.*;

public class ConsolePagePrinter {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final int PAGE_SIZE = 1800;
    private String fileName = "warpeace.txt";
    private int page;

    private void intro(){
        System.out.println("Добро пожаловать в консольную читалку!");
        System.out.println("Основные команды:");
        System.out.println("выбрать книгу - /выбрать");
        System.out.println("перелистнуть страницу - /след");
        System.out.println("выход из программы - /выход");
        System.out.println("выбрать страницу - номер_страницы");
        System.out.println("вспомнить команды - /помощь");
        System.out.println("Введите название файла книги и номер страницы" +
                " или нажмите enter для чтения текущей страницы выбранной книги(по умолчанию - Война и Мир):");
    }

    public void start(){
        intro();
        String line;
        while (true){
            line = readingConsoleInput();
            if(line.equals("/выход"))break;

            if((line.isEmpty())) {
                printPage(fileName,page);
            }else if(line.equals("/след")) {
                next();
            }else if(line.equals("/выбрать")){
                    fileName = line.split(",")[0];
                    page = Integer.parseInt(line.split(",")[1]);
                    printPage(fileName,page);

            }else if(line.equals("/помощь")){
                intro();
            }else{
                try{
                    page = Integer.parseInt(line) - 1;
                    printPage(fileName,page>=0? page:0);
                }catch (NumberFormatException e){
                    System.out.println("Неверный ввод - /помощь");
                }
            }
        }
    }

    private void printPage(String fileName, int page){
        System.out.println("Страница № " + (page!=0?page+1:1));
        StringBuilder text = new StringBuilder();
        this.fileName = fileName;
        this.page = page;
        File file  = new File(fileName);
        int position = PAGE_SIZE > (int)file.length() ? 0 : PAGE_SIZE*page;
        int pageLength = PAGE_SIZE > (int)file.length() ? (int)file.length() : PAGE_SIZE;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName,"r")) {
            randomAccessFile.seek(position);
            for (int i = 0; i < pageLength; i++) {
                text.append((char)randomAccessFile.read());
            }
            System.out.println(text.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void next(){
        printPage(fileName,page + 1);
    }

    private static String readingConsoleInput() {
        String result = "";
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void stop() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
