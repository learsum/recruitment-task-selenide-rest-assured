Feature: NBP Exchange Rates

  Scenario: Kursy walut
    When Pobierz kursy walut
    Then Wyświetl kurs dla waluty o kodzie: "USD"
    And Wyświetl kurs dla waluty o nazwie: "dolar amerykański"
    And Wyświetl waluty o kursie powyżej: 5
    And Wyświetl waluty o kursie poniżej: 3 