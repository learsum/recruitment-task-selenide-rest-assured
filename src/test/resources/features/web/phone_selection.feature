Feature: Phone selection from T-Mobile website

  Scenario: Select phone from offers list
    When I navigate to "https://www.t-mobile.pl/"
    Then Main page is visible
    When I click on "Urządzenia" in the top menu
    Then Dropdown menu is visible
    When I click "Smartfony" from "Bez abonamentu" column
    Then Products list is visible
    When I click first product from the list
    Then Product page is visible
    When I add product to cart
    Then Cart page is visible
    And Cart prices match product prices
    When I go to T-Mobile home page
    Then Main page is visible with cart icon showing products