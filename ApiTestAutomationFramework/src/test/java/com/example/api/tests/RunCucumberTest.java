package com.example.api.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.example.api.tests", "com.example.api.main.utils"},
        plugin = {"pretty", "json:target/cucumber.json"},
        monochrome = true
)
public class RunCucumberTest {

        @BeforeClass
        public static void setup() {
                System.out.println("Test Suite Execution Started.");
        }

        @AfterClass
        public static void tearDown() {
                // Ensure the Extent Report is flushed properly after the tests
                CucumberHooks.afterAll();
                System.out.println("Test Suite Execution Completed.");
        }
}
