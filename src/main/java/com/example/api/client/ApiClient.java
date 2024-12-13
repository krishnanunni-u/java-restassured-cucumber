package com.example.api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ApiClient {

    private String baseUri;

    public ApiClient(String baseUri) {
        this.baseUri = baseUri;
        RestAssured.baseURI = baseUri;
    }

    public Response get(String endpoint, Map<String, String> headers) {
        return RestAssured
                .given()
                .headers(headers)       // Add headers if provided
                .log().all()            // Log all request details
                .get(endpoint)          // Perform the GET request
                .then()
                .log().all()            // Log all response details
                .extract().response();  // Extract the response
    }


    public Response post(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured
                .given()
                .headers(headers)
                .body(body)
                .log().all()          // Log all details of the request
                .post(endpoint)
                .then()
                .log().all()          // Log all details of the response
                .extract().response();
    }

    public Response put(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured
                .given()
                .headers(headers)
                .body(body)
                .log().all()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response delete(String endpoint, Map<String, String> headers) {
        return RestAssured
                .given()
                .headers(headers)
                .log().all()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response patch(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured
                .given()
                .headers(headers)
                .body(body)
                .log().all()
                .patch(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public String getBaseUri() {
        return baseUri;
    }

}
