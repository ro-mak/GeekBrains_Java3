import org.junit.Before;
import org.junit.Test;

public class Task1Test2 {
    private HomeworkJava3Junit homeworkJava3Junit;

    @Before
    public void init(){
        homeworkJava3Junit = new HomeworkJava3Junit();
    }

    @Test(expected = NullPointerException.class)
    public void test2Task1(){
        homeworkJava3Junit.task1(null);
    }

    @Test(expected = NullPointerException.class)
    public void test3Task1(){
        homeworkJava3Junit.task1(new int[]{});
    }

    @Test(expected = RuntimeException.class)
    public void test4Task1(){
        homeworkJava3Junit.task1(new int[]{1,2,3});
    }
}
