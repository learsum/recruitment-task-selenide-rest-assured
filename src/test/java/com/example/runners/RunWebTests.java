package com.example.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/web",
    glue = {"com.example.web.steps", "com.example.web.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/web/cucumber.html",
        "json:target/cucumber-reports/web/cucumber.json",
        "junit:target/cucumber-reports/web/cucumber.xml",
        "com.example.web.hooks.TestHooks"
    },
    tags = "not @api"
)
public class RunWebTests {
} 