Feature: Validate pet

  @pets
  Scenario: Validate that the response od the pet request is 200
    Given the following get request that brings us the all pets per status
    Then the response is 200 for pet

  @petPost
  Scenario Outline: Validate post pet
    Given the following post request that add pet
    And the response is 200 for the pet post
    Then the body response contains the "<name>" of the pet created

    Examples:
      |   name   |
      | Charly   |

  @petGet
  Scenario: Validate that the response od the pet created request is 200
    Given the following get request that brings us the pet created
    Then the response is 200 for pet created

  @petPut
  Scenario Outline: Validate update create pet
    Given  the following put request that update pet status
    And the response is 200 for the pet put
    Then the body response contains update "<status>" pet

    Examples:
      |status  |
      | sold   |

  @petDel
  Scenario: Validate delete pet
    Given the following post request that add pet
    And the following delete request that delete pet
    Then the response is 200 for the delete pet
