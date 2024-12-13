package com.example.api.tests.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Initialize the ExtentReports instance
    public static synchronized ExtentReports createInstance(String filePath) {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
            sparkReporter.config().setDocumentTitle("API Test Automation Report");
            sparkReporter.config().setReportName("PetStore API Tests");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Your Name");
        }
        return extent;
    }

    // Fetch the ExtentTest for the current thread
    public static synchronized ExtentTest getTest() {
        return test.get();
    }

    // Set ExtentTest for the current thread
    public static synchronized void setTest(String testName) {
        test.set(extent.createTest(testName));
    }

    // Flush the report to disk
    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
