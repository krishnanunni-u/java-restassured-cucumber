Feature: PetStore API Tests
  This feature validates the functionality of the PetStore API.
  It ensures that new pets can be added and responses are correct, with validations for both positive and negative scenarios.

  Background:
    Given I set the base URI for PetStore API

  @Positive @AddPet
  Scenario: Successfully add a new pet to the store
    Given I prepare a new pet with name "doggie" and status "available"
    When I send a POST request to add the pet
    Then the API should return status code 200
    And the response should contain the name "doggie"
    And the response should contain the status "available"

  @Negative @InvalidPayload
  Scenario: Fail to add a pet with an invalid payload
    Given I prepare an invalid pet payload
    When I send a POST request to add the pet
    Then the API should return status code 400
    And the response should contain an error message

  @Negative @NotFound
  Scenario: Retrieve a non-existent pet
    When I send a GET request for a pet with ID 99999
    Then the API should return status code 404
    And the response should contain "Pet not found"

  @Regression @AddPet
  Scenario Outline: Add pets with different names and statuses
    Given I prepare a new pet with name "<name>" and status "<status>"
    When I send a POST request to add the pet
    Then the API should return status code 200
    And the response should contain the name "<name>"
    And the response should contain the status "<status>"

    Examples:
      | name     | status     |
      | doggie   | available  |
      | kitty    | pending    |
      | fishy    | sold       |
