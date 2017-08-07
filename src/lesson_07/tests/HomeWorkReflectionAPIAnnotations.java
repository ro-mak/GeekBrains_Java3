
public class HomeWorkReflectionAPIAnnotations {

    @BeforeSuite
    public void before(){
        System.out.println("Before");
    }

//    @BeforeSuite
//    public void before1(){
//        System.out.println("Before");
//    }

    @Test(priority = 10)
    public void test1(){
        System.out.println("Test1");
    }

    @Test(priority = 2)
    public void test2(){
        System.out.println("Test2");
    }
    @Test(priority = 10)
    public void test3(){
        System.out.println("Test3");
    }
    @Test()
    public void test4(){
        System.out.println("Test4");
    }

    @Test()
    public void test5(){
        System.out.println("Test5");
    }

    @AfterSuite
    public void after(){
        System.out.println("After");
    }

//    @AfterSuite
//    public void after1(){
//        System.out.println("After");
//    }

    public static void main(String[] args) {
        TestExecutor.start(HomeWorkReflectionAPIAnnotations.class);
    }
}
