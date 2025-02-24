package com.example.api;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/api",
    glue = "com.example.api.steps",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/api/cucumber.html",
        "json:target/cucumber-reports/api/cucumber.json"
    }
)
public class RunApiTests {
} 