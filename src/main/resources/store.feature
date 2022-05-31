Feature: Validate store

  @store
  Scenario: Validate that the response od the store request is 200
    Given the following get request that brings us the all inventory
    Then the response is 200 store

  @storePost
  Scenario Outline: Validate post store
    Given the following post request that add order
    And the response is 200 for the post store
    Then the body response contains the "<complete>" of the order created

    Examples:
      |   complete   |
      |     true     |

  @orderGet
  Scenario: Validate that the response od the order created request is 200
    Given the following get request that brings us the order created
    Then the response is 200 order

  @orderDel
  Scenario: Validate delete order
    Given the following post request that add order
    And the following delete request that delete order
    Then the response is 200 for the delete order
