import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;


public class Task3Tests {

    @BeforeClass
    public static void init() {
        Task3DataBaseToTest.connect();
        try {
            Task3DataBaseToTest.connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void stop() {
        Task3DataBaseToTest.disconnect();
    }

    @Test
    public void dataBaseTest1() {
        Task3DataBaseToTest.insert("Test1,2");//Insert
        String[] result = {"Test1 2"};
        Assert.assertArrayEquals(Task3DataBaseToTest.select("Test1"), result); //Select
    }

    @Test
    public void dataBaseTest2() {
        Task3DataBaseToTest.update("name,Test2,id,1");//Update
        Task3DataBaseToTest.update("result,5,name,Test2");
        String[] result2 = {"Test2 5"};
        Assert.assertArrayEquals(Task3DataBaseToTest.select("Test2"), result2);//Select
    }

    @Test
    public void dataBaseTest3() {
        Task3DataBaseToTest.insert("Test3,2");
        Task3DataBaseToTest.delete("name,Test3");//Delete
        String[] result3 = {null};
        Assert.assertArrayEquals(Task3DataBaseToTest.select("Test3"), result3);//Select
    }

}
