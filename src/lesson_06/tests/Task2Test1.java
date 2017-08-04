import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task2Test1 {
    private HomeworkJava3Junit homeworkJava3Junit;

    @Before
    public void init() {
        homeworkJava3Junit = new HomeworkJava3Junit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {new int[]{4,4,1,1}},
                {new int[]{4,1,1,1}},
                {new int[]{4,4,4,1}}
        });
    }

    public Task2Test1(int[]array){
        this.array = array;
    }

    private int[] array;

    @Test
    public void testTask2(){
        Assert.assertTrue(homeworkJava3Junit.task2(array));
    }
}
