package testng;

import org.testng.TestNG;
import org.testng.collections.Lists;

import java.io.*;
import java.util.List;

public class TestNGRunner {

    public static void main(String[] args) throws IOException {

        String xmlFile = "testng.xml";

        // Get the content of testng.xml
        ClassLoader classLoader = TestNGRunner.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(xmlFile);

        assert inputStream != null;
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);

        File targetFile = new File(xmlFile);
        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);

            // Run all the tests in testng.xml
            TestNG testng = new TestNG();
            List<String> suites = Lists.newArrayList();
            suites.add(xmlFile);
            testng.setTestSuites(suites);
            testng.run();
        }
        targetFile.deleteOnExit();
    }
}
