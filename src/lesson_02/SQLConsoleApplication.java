package lesson_02;

import java.io.*;
import java.sql.*;

public class SQLConsoleApplication {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static Connection connection;
    private static Statement statement;
    private String table;
    private boolean firstLaunch = true;

    public SQLConsoleApplication() {
        loadSettings();
    }

    public void startApp() {
        connect();
        createStatement();
        String command = "";
        if (firstLaunch) {
            System.out.println("Здравствуйте, спасибо за использование нашей программы.");
            executeUpdate("/помощь");
            firstLaunch = false;
            saveSettings();
        }
        int exit = 1;
        while (true) {
            try {
                command = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (command.isEmpty()) {
                if (exit == 0) System.exit(0);
                System.out.println("Нажмите enter, если действительно хотите выйти из программы.");
                exit--;
            } else if (command.startsWith("/помощь")) {
                executeUpdate(command);
            } else if (command.startsWith("/цена") || command.startsWith("/товарыпоцене")) {
                executeQuery(command);
                if (exit == 0) {
                    exit++;
                }
            } else if (command.startsWith("/сменитьцену") || command.startsWith("/создатьтаблицу")
                    || command.startsWith("/выбратьтаблицу")) {
                executeUpdate(command);
                if (exit == 0) {
                    exit++;
                }
            }else{
                System.out.println("Такой команды нет, введите /помощь");
            }
        }
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:educational.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void createStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeQuery(String query) {
        String command = "";
        StringBuilder result = new StringBuilder("Такого товара нет");
        ResultSet resultSet;
        if (query.startsWith("/цена")) {
            command = "SELECT COST FROM " + table
                    + " WHERE TITLE = '" + query.split(" ")[1] + "'";
        } else if (query.startsWith("/товарыпоцене")) {
            command = "SELECT * FROM " + table
                    + " WHERE COST >= " + query.split(" ")[1]
                    + " AND COST <= " + query.split(" ")[2];
        }
        try {
            resultSet = statement.executeQuery(command);
            boolean hasNext = false;
            while (resultSet.next()) {
                if (!hasNext) {
                    result = new StringBuilder();
                    hasNext = true;
                }
                if (query.startsWith("/цена")) {
                    result.append("Цена выбранного товара: " + resultSet.getInt("COST"));
                } else if (query.startsWith("/товарыпоцене")) {
                    result.append(String.format("%d UNIQUE_NUMBER: %s TITLE: %s COST: %d\n",
                            resultSet.getInt("ID"), resultSet.getString("PRODID"),
                            resultSet.getString("TITLE"), resultSet.getInt("COST")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result.toString());
    }

    private void executeUpdate(String update) {
        String command = "";
        if (update.startsWith("/создатьтаблицу")) {
            command = "CREATE TABLE" + update.split(" ")[1];
            table = update.split(" ")[1];
            saveSettings();
        } else if (update.startsWith("/помощь")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Основные команды:\n");
            stringBuilder.append("/выбратьтаблицу имя_таблицы\n");
            stringBuilder.append("/создатьтаблицу имя_таблицы\n");
            stringBuilder.append("/сменитьцену наименование(title) цена(cost)\n");
            stringBuilder.append("/цена наименование(title)\n");
            stringBuilder.append("/товарыпоцене цена(минимум) цена(максимум)\n");
            stringBuilder.append("Для выхода из программы дважды нажмите ENTER");

            System.out.println(stringBuilder.toString());
        } else if (update.startsWith("/выбратьтаблицу")) {
            table = update.split(" ")[1];
            System.out.println("Выбрана таблица " + table);
            saveSettings();
        } else if (update.startsWith("/сменитьцену")) {
            command = "UPDATE " + table
                    + " SET COST = " + update.split(" ")[2]
                    + " WHERE TITLE = '" + update.split(" ")[1] + "'";
        }
        if (command.isEmpty()) return;
        try {
            statement.executeUpdate(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveSettings() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("settings.txt")) {
            String stringBuilder = String.valueOf(firstLaunch) +
                    "\r\n" +
                    table;
            fileOutputStream.write(stringBuilder.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        String[] settings = new String[2];
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("settings.txt")))) {
            int count = 0;
            while (bufferedReader.ready()) {
                settings[count] = bufferedReader.readLine();
                count++;
            }
        } catch (FileNotFoundException f) {
            System.out.println("Таблица не выбрана");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (settings[0] != null) {
            firstLaunch = Boolean.valueOf(settings[0]);
        }
        if (settings[1] != null) {
            table = settings[1];
            System.out.println("Выбрана таблица " + table);
        }
    }

    public void closeApp() {
        disconnect();
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
