package ua.drozda;

/**
 * Created by GFH on 15.05.2015.
 */
public class TestConstants {
    public static final String TEST_DELIMETER = "--------------------------------------------------------------";
    public static final String TEST_METHOD_STARTER = ">>";

    public static void testComplete() {
        System.out.println(TEST_DELIMETER);
    }


    public static void testMethodStarted(String testName) {
        System.out.print(TEST_METHOD_STARTER);
        System.out.println(testName + " Started");
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }
}
