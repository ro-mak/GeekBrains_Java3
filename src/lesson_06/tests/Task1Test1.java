import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Task1Test1 {
    private HomeworkJava3Junit homeworkJava3Junit;

    @Before
    public void init(){
        homeworkJava3Junit = new HomeworkJava3Junit();
    }

    private int[] array;
    private int[] arrayResult;

    public Task1Test1(int[]array, int[]arrayResult){
        this.array = array;
        this.arrayResult = arrayResult;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {new int[]{4,1,2,3,1,5},new int[]{1,2,3,1,5}},
                {new int[]{1,4,3,6,4},new int[]{}},
                {new int[]{2,2,7,4,4,765,5,4,1},new int[]{1}},
                {new int[]{4,2,3,2,4,2,1,6,4,9},new int[]{9}},
        });
    }
    @Test
    public void testTask1(){
        Assert.assertArrayEquals(arrayResult,homeworkJava3Junit.task1(array));
    }

}
