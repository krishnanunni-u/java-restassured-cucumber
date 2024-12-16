package com.example.api.tests;

import com.aventstack.extentreports.ExtentTest;
import com.example.api.client.ApiClient;
import com.example.api.model.PetRequest;
import com.example.api.tests.utils.ConfigReader;
import com.example.api.tests.utils.ExtentReportManager;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class PetStoreSteps {

    private final ApiClient apiClient; // Reusable ApiClient instance
    private Response response; // Stores the API response
    private ExtentTest test; // ExtentReport Test instance
    private String requestBody; // Stores the request payload

    public PetStoreSteps() {
        // Initialize ApiClient with the base URI from configuration
        this.apiClient = new ApiClient(ConfigReader.getProperty("base.uri"));

        // Fetch the current ExtentTest instance for the running scenario
        this.test = ExtentReportManager.getTest();

        if (test != null) {
            test.info("PetStoreSteps initialized successfully.");
        } else {
            System.err.println("ExtentTest instance is null. Ensure CucumberHooks is setting it correctly.");
        }
    }

    @Given("I set the base URI for PetStore API")
    public void setBaseURI() {
        test.info("Base URI is already set during ApiClient initialization: " + apiClient.getBaseUri());
    }

    @Given("I prepare a new pet with name {string} and status {string}")
    public void prepareNewPet(String name, String status) {
        // Use the PetRequest class to generate the request body
        requestBody = PetRequest.createRequestBody(
                0L,                              // Default ID
                "string",                        // Default category name
                name,                            // Pet name from the method parameter
                List.of("string"),               // Default photo URLs
                List.of(PetRequest.Tag.builder() // Default tags
                        .id(0L)
                        .name("string")
                        .build()),
                status                           // Pet status from the method parameter
        );

        test.info("Prepared pet payload with name: " + name + " and status: " + status);
    }


    @Given("I prepare an invalid pet payload")
    public void prepareInvalidPetPayload() {
        requestBody = """
        {
            "invalidField": "invalidValue"
        }
        """;
        test.info("Prepared invalid pet payload: " + requestBody);
    }

    @When("I send a POST request to add the pet")
    public void sendPostRequest() {
        String endpoint = ConfigReader.getProperty("pet.endpoint");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        test.info("Sending POST request to: " + apiClient.getBaseUri() + endpoint);
        response = apiClient.post(endpoint, headers, requestBody);

        test.info("Response received: " + response.prettyPrint());
    }

    @When("I send a GET request for a pet with ID {int}")
    public void sendGetRequestForPet(int petId) {
        String endpoint = ConfigReader.getProperty("pet.endpoint") + "/" + petId;

        Map<String, String> headers = new HashMap<>(); // Initialize headers if needed
        headers.put("Content-Type", "application/json"); // Example header

        test.info("Sending GET request to: " + apiClient.getBaseUri() + endpoint);
        response = apiClient.get(endpoint, headers); // Pass headers into the GET method

        test.info("Response received: " + response.prettyPrint());
    }


    @Then("the API should return status code {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        try {
            response.then().statusCode(expectedStatusCode);
            test.pass("Verified that the status code is: " + expectedStatusCode);
        } catch (AssertionError e) {
            test.fail("Expected status code: " + expectedStatusCode + ", but got: " + response.statusCode());
            throw e;
        }
    }

    @Then("the response should contain the name {string}")
    public void verifyResponseName(String name) {
        try {
            response.then().body("name", equalTo(name));
            test.pass("Verified that the response contains name: " + name);
        } catch (AssertionError e) {
            test.fail("Name validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Then("the response should contain the status {string}")
    public void verifyResponseStatus(String status) {
        try {
            response.then().body("status", equalTo(status));
            test.pass("Verified that the response contains status: " + status);
        } catch (AssertionError e) {
            test.fail("Status validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Then("the response should contain an error message")
    public void verifyErrorMessage() {
        try {
            String errorMessage = response.jsonPath().getString("message");
            Assert.assertNotNull("Error message is missing in the response.", errorMessage);
            test.pass("Verified that the response contains an error message: " + errorMessage);
        } catch (Exception e) {
            test.fail("Error message validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Then("the response should contain {string}")
    public void verifyGenericErrorMessage(String expectedMessage) {
        try {
            String actualMessage = response.jsonPath().getString("message");
            Assert.assertEquals("Unexpected error message.", expectedMessage, actualMessage);
            test.pass("Verified the error message matches: " + expectedMessage);
        } catch (Exception e) {
            test.fail("Generic error message validation failed: " + e.getMessage());
            throw e;
        }
    }
}
