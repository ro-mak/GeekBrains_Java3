package lesson_02;

import java.sql.*;

public class HomeWorkSQLite {

    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        connect();
        createStatement();
        dropTheTable("LESSON2");
        task1();
        task2();

        disconnect();
    }

    public static void task1(){
        executeUpdate("CREATE TABLE LESSON2" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "PRODID INTEGER UNIQUE," +
                "TITLE TEXT," +
                "COST INTEGER)");
    }

    public static void task2(){
        for (int i = 0,j = 10; i < 10; i++,j+=10) {
            executeUpdate("INSERT INTO LESSON2 (PRODID,TITLE,COST) VALUES ('" +
                     i +"', 'product" + i + "', '"+ j+"')");
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
