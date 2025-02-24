package com.example.api.steps;

import com.example.api.model.Rate;
import com.example.api.service.NBPService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExchangeRatesSteps {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesSteps.class);
    private final NBPService nbpService = new NBPService();
    private Response response;

    @When("Pobierz kursy walut")
    public void getExchangeRates() {
        response = nbpService.getExchangeRates();
        logger.info("Response status code: {}", response.getStatusCode());
        logger.info("Response headers: {}", response.getHeaders());
    }

    @Then("Wyświetl kurs dla waluty o kodzie: {string}")
    public void displayRateByCode(String code) {
        double rate = nbpService.getRateByCode(code);
        logger.info("Kurs dla waluty {}: {}", code, rate);
    }

    @Then("Wyświetl kurs dla waluty o nazwie: {string}")
    public void displayRateByCurrency(String currency) {
        double rate = nbpService.getRateByCurrency(currency);
        logger.info("Kurs dla waluty {}: {}", currency, rate);
    }

    @Then("Wyświetl waluty o kursie powyżej: {double}")
    public void displayRatesAbove(double threshold) {
        List<Rate> rates = nbpService.getRatesAbove(threshold);
        logger.info("Waluty o kursie powyżej {}: ", threshold);
        rates.forEach(rate -> logger.info("{} ({}): {}", rate.getCurrency(), rate.getCode(), rate.getMid()));
    }

    @Then("Wyświetl waluty o kursie poniżej: {double}")
    public void displayRatesBelow(double threshold) {
        List<Rate> rates = nbpService.getRatesBelow(threshold);
        logger.info("Waluty o kursie poniżej {}: ", threshold);
        rates.forEach(rate -> logger.info("{} ({}): {}", rate.getCurrency(), rate.getCode(), rate.getMid()));
    }
} 