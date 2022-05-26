Feature: Validate user

  @userPost
  Scenario Outline: Validate post user
    Given the following post request that add user
    And the response is 200 for the user post
    Then the body response contains the "<id>" of the user created

    Examples:
      |   id   |
      |  7654  |

  @userGet
  Scenario Outline: Validate that the response od the user created request is 200
    Given the following get request that brings us the "<username>" created
    Then the response is 200 for user created

    Examples:
      |   username   |
      |    FlorCh    |

  @userLogin
  Scenario Outline: Validate that the response od the user login request is 200
    Given the following get request that brings us the "<username>" created
    And the user login with username and password
    Then the response is 200 for user login

    Examples:
      | username |
      | FlorCh   |

  @logout
  Scenario: Validate that the response od the logout request is 200
    Given the user logout
    Then the response is 200 for logout

  @userPut
  Scenario Outline: Validate update create user
    Given the following get request that brings us the "<username>" created
    And the following put request that update user password
    And the response is 200 for the user put
    Then the user login with username and password

    Examples:
      | username |
      | FlorCh   |

  @userDel
  Scenario Outline: Validate delete user
    Given the following post request that add user
    And the following get request that brings us the "<username>" created
    And the following delete request that delete user
    Then the response is 200 for the delete user

    Examples:
      | username |
      | FlorCh   |