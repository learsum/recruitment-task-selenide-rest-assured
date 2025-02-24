package com.example.web.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.*;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHooks implements ConcurrentEventListener {
    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);

    @BeforeAll
    public static void beforeAll() {
        logger.info("Starting test suite execution");
    }

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Initializing test configuration");
        Configuration.browser = "chrome";
        Configuration.browserSize = "2560x1440";
        Configuration.timeout = 10000;
        Configuration.browserVersion = "latest";
        Configuration.headless = false;
        Configuration.reportsFolder = "target/selenide-reports";
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::logTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::logTestStepFinished);
    }

    private void logTestStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            logger.info("Starting step: {}", step.getStep().getText());
        }
    }

    private void logTestStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.PASSED)) {
                logger.info("Step passed: {}", step.getStep().getText());
            } else {
                logger.error("Step failed: {}", step.getStep().getText());
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario failed: {}", scenario.getName());
            try {
                byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
                logger.info("Screenshot taken and attached to the scenario");
            } catch (Exception e) {
                logger.error("Failed to take screenshot: {}", e.getMessage(), e);
            }
        } else {
            logger.info("Scenario completed successfully: {}", scenario.getName());
        }
        
        try {
            Selenide.closeWebDriver();
            logger.info("Browser closed successfully");
        } catch (Exception e) {
            logger.error("Error while closing browser: {}", e.getMessage(), e);
        }
    }

    @AfterAll
    public static void afterAll() {
        logger.info("Test suite execution completed");
    }
} 