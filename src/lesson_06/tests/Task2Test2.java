import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task2Test2 {

    private HomeworkJava3Junit homeworkJava3Junit;

    @Before
    public void init() {
        homeworkJava3Junit = new HomeworkJava3Junit();
    }

    private int[] array;

    public Task2Test2(int[] array) {
        this.array = array;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{0, 2, 3, 4, 1}},
                {new int[]{0, 5, 3, 5, 1}},
                {new int[]{0, 2, 3, 4, 23}},
                {new int[]{0, 2, 3, 5, 6}},
                {new int[]{44, 11}},
                {new int[]{4, 4}},
                {new int[]{1, 1}},
        });
    }

    @Test
    public void test2Task2() {
        Assert.assertFalse(homeworkJava3Junit.task2(array));
    }
}
