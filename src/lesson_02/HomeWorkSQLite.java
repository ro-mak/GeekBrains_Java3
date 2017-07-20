package lesson_02;

import java.sql.*;

public class HomeWorkSQLite {

    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        //region First two tasks
//        connect();
//        createStatement();
//          dropTheTable("LESSON2");
//          task1();
//        System.out.println("Table created");
//          task2();
//        System.out.println("Done");
//        disconnect();
        //endregion
        //region SQLConsoleApp
        SQLConsoleApplication sqlConsoleApplication = new SQLConsoleApplication();
        sqlConsoleApplication.startApp();
        sqlConsoleApplication.closeApp();
        //endregion
    }

    public static void task1(){
        executeUpdate("CREATE TABLE LESSON2" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "PRODID TEXT UNIQUE," +
                "TITLE TEXT," +
                "COST INTEGER)");
    }

    public static void task2(){
        for (int i = 0,j = 10; i < 10000; i++,j+=10) {
            executeUpdate("INSERT INTO LESSON2 (ID,PRODID,TITLE,COST) VALUES (" +
                     i +",'id_product"+i+"', 'product" + i + "', '"+ j+"')");
        }
    }

    public static void dropTheTable(String tableName){
        executeUpdate("DROP TABLE " + tableName);
    }



    public static void connect(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:educational.sqlite");
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void createStatement(){
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeUpdate(String query){
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(){
        try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
