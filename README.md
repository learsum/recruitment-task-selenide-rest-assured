# Test Automation Framework

Framework do automatyzacji testów API i Web, wykorzystujący:
- Cucumber do BDD
- RestAssured do testów API
- Selenide do testów Web
- Logback do logowania
- Maven do zarządzania projektem

## Uruchamianie testów

mvn clean test

### Testy API

mvn test -Dtest=RunApiTests

### Testy Web

mvn test -Dtest=RunWebTests

