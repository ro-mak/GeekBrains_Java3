
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

    private static void task1(){
        executeUpdate("CREATE TABLE LESSON2" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "PRODID TEXT UNIQUE," +
                "TITLE TEXT," +
                "COST INTEGER)");
        commit();
    }

    private static void task2(){
        for (int i = 0,j = 10; i < 10000; i++,j+=10) {
            executeUpdate("INSERT INTO LESSON2 (ID,PRODID,TITLE,COST) VALUES (" +
                     i +",'id_product"+i+"', 'product" + i + "', '"+ j+"')");
        }
        commit();
    }

    private static void dropTheTable(String tableName){
        executeUpdate("DROP TABLE " + tableName);
    }



    private static void connect(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:educational.sqlite");
            connection.setAutoCommit(false);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    private static void commit(){
        try {
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void createStatement(){
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void executeUpdate(String query){

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void disconnect(){
        try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
