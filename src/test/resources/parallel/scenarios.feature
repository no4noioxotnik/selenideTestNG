Feature: Scenarios feature file

  Scenario: Scenario Number One
    Given open url "https://www.google.com/" in "firefox" browser
    And set language to English
    And type text "beach" in search field
    And wait for "10000" seconds
    And type text "smallest" in search field


  Scenario: Scenario Number Two
    Given open url "https://www.google.com/" in "chrome" browser
    And set language to English
    And type text "beach" in search field
    And wait for "10000" seconds
    And type text "biggest" in search field
    And set language to English
