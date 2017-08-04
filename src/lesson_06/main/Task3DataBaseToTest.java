import java.sql.*;
import java.util.Arrays;

public class Task3DataBaseToTest {

    private static PreparedStatement preparedStatement;
    public static Connection connection;
    private final static String tableName = "Students";

    public static void main(String[] args) {
        connect();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(select("Roma")));
        insert("Donald,2");
        update("name,Don,ID,1");
        delete("name,Donald");
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public static String[] select(String name) {
        setPreparedStatement("SELECT * FROM " + tableName + " WHERE NAME = ?", name);
        String[] result = null;
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int size = 1;
        if (resultSet != null) {
            try {
                result = new String[size];
                int count = 0;
                while (resultSet.next()) {
                    if (result.length <= count) {
                        String[] temp = new String[result.length + 1];
                        System.arraycopy(result, 0, temp, 0, result.length);
                        result = temp;
                    }
                    result[count++] = resultSet.getString("name") + " " + resultSet.getInt("result");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void insert(String query) {
        String name = query.split(",")[0];
        int result = Integer.parseInt(query.split(",")[1]);
        setPreparedStatement("INSERT INTO " + tableName + " (NAME,RESULT) VALUES(?,?)"
                , name, result);
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(String query) {
        String[] queryFields = query.split(",");
        int queryLength = queryFields.length;
        String columnName = queryFields[0];
        String value = queryFields[1];
        String conditionColumn;
        String conditionValue;
        if (queryLength > 2) {
            conditionColumn = queryFields[2];
            conditionValue = queryFields[3];
            setPreparedStatement("UPDATE " + tableName
                    + " SET " + columnName
                    + " = ? WHERE " + conditionColumn + " = ?;", value, conditionValue);
        } else if (queryLength == 2) {
            setPreparedStatement("UPDATE " + tableName
                    + " SET " + columnName + " = " + value);
        }
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String query) {
        String[] queryFields = query.split(",");
        String columnName = queryFields[0];
        String columnValue = queryFields[1];
        setPreparedStatement("DELETE FROM " + tableName
                + " WHERE " + columnName + " = ?", columnValue);
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static <T> void setPreparedStatement(String statement, T... argument) {
        try {
            preparedStatement = connection.prepareStatement(statement);
            if (argument.length > 0) {
                for (int i = 0; i < argument.length; i++) {
                    if (argument[i] instanceof String) {
                        preparedStatement.setString(i + 1, (String) argument[i]);
                    } else if (argument[i] instanceof Integer) {
                        preparedStatement.setInt(i + 1, (Integer) argument[i]);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/lesson_06/TestsDB.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
