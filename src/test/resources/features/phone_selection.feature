Feature: Phone selection from T-Mobile website

  Scenario: Select phone from offers list
    When I open the browser
    Then Browser is opened
    When I navigate to "https://www.t-mobile.pl/"
    Then Main page is visible
    When I click on "Urządzenia" in the top menu
    Then Dropdown menu is visible 