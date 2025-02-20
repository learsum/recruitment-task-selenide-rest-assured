package com.example.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHooks {
    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);

    @Before
    public void setUp() {
        logger.info("Initializing test configuration");
        Configuration.browser = "chrome";
        // Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.browserVersion = "latest";
        Configuration.headless = false;
        Configuration.browserSize = "maximized";
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Test failed: {}", scenario.getName());
            try {
                byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            } catch (Exception e) {
                logger.error("Failed to take screenshot: {}", e.getMessage());
            }
        }
        
        try {
            Selenide.closeWebDriver();
            logger.info("Browser closed successfully");
        } catch (Exception e) {
            logger.error("Error while closing browser: {}", e.getMessage());
        }
    }
} 