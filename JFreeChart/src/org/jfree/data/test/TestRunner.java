package org.jfree.data.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * TestRunner is a utility class for running JUnit test suites and displaying the results.
 */
public class TestRunner {
    public static void main(String[] args) {
        // Run the specified JUnit test suite and store the result
        Result result = JUnitCore.runClasses(TestSuite.class);

        // Iterate over each failure in the test result and print it
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        // Print whether the test suite was successful or not
        System.out.println(result.wasSuccessful());
    }
}
