package com.example.api.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.example.api.tests.utils.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks {

    private static ExtentReports extent;
    private static String reportPath;

    // Initialize Extent Report before all tests
    @Before
    public void beforeScenario(Scenario scenario) {
        // Set the test case in ExtentReport for the current scenario
        ExtentReportManager.setTest(scenario.getName());
        ExtentTest test = ExtentReportManager.getTest();
        test.info("Starting scenario: " + scenario.getName());
    }

    // Capture test results after each scenario
    @After
    public void afterScenario(Scenario scenario) {
        ExtentTest test = ExtentReportManager.getTest();

        if (scenario.isFailed()) {
            test.fail("Scenario failed: " + scenario.getName());
        } else {
            test.pass("Scenario passed: " + scenario.getName());
        }
        test.info("Scenario execution completed.");
    }

    // Flush the report after all tests
    @AfterAll
    public static void afterAll() {
        ExtentReportManager.flushReport();
        System.out.println("Extent Report generated at: " + reportPath);
    }

    // Ensure the report is initialized before all hooks
    static {
        reportPath = System.getProperty("user.dir") + "/target/extent-reports/ExtentReport.html";
        extent = ExtentReportManager.createInstance(reportPath);
    }
}
