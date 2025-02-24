package com.example.api.service;

import com.example.api.model.ExchangeTable;
import com.example.api.model.Rate;
import io.restassured.response.Response;
import java.util.List;

import static io.restassured.RestAssured.given;

public class NBPService {
    private static final String NBP_API_URL = "http://api.nbp.pl/api";
    private Response response;
    private ExchangeTable[] exchangeTables;

    public Response getExchangeRates() {
        if (response == null) {
            response = given()
                .baseUri(NBP_API_URL)
                .header("Accept", "application/json")
                .when()
                .get("/exchangerates/tables/A")
                .then()
                .extract()
                .response();
            
            exchangeTables = response.as(ExchangeTable[].class);
        }
        return response;
    }

    public double getRateByCode(String code) {
        return exchangeTables[0].getRates().stream()
            .filter(rate -> rate.getCode().equals(code))
            .findFirst()
            .map(Rate::getMid)
            .orElseThrow(() -> new RuntimeException("Currency code not found: " + code));
    }

    public double getRateByCurrency(String currencyName) {
        return exchangeTables[0].getRates().stream()
            .filter(rate -> rate.getCurrency().equalsIgnoreCase(currencyName))
            .findFirst()
            .map(Rate::getMid)
            .orElseThrow(() -> new RuntimeException("Currency name not found: " + currencyName));
    }

    public List<Rate> getRatesAbove(double threshold) {
        return exchangeTables[0].getRates().stream()
            .filter(rate -> rate.getMid() > threshold)
            .toList();
    }

    public List<Rate> getRatesBelow(double threshold) {
        return exchangeTables[0].getRates().stream()
            .filter(rate -> rate.getMid() < threshold)
            .toList();
    }
} 