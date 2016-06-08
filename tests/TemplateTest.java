import org.junit.*;

public class TemplateTest {

    // The function name is the test name

    @BeforeClass
    public static void initClass() throws Exception {
        // Executed before the first tests of the class
    }

    @AfterClass
    public static void finishClass() throws Exception {
        // Executed after all the tests of the class
    }

    @Before
    public void initTest() throws Exception {
        // Executed before each test
    }

    @After
    public void finishTest() throws Exception {
        // Executed after each test
    }

    @Test
    public void functionOne() {
        Assert.assertTrue(true);
        Assert.assertEquals(2, 2);
        // All the tests about the function `functionOne`.
    }
}
