package com.example.web.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.web.session.SessionContext;
import io.cucumber.java.*;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

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
        
        boolean isJenkinsBuild = Boolean.parseBoolean(System.getProperty("jenkins.build", "false"));
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("browser.headless", "true"));
        
        Configuration.browser = "chrome";
        Configuration.browserSize = isJenkinsBuild ? "1920x1080" : "2560x1440";
        Configuration.timeout = isJenkinsBuild ? 20000 : 30000;
        Configuration.headless = isHeadless;
        
        Configuration.reportsFolder = "target/selenide-reports";
        Configuration.savePageSource = true;
        Configuration.screenshots = true;
        Configuration.reopenBrowserOnFail = true;
        
        logger.info("Browser configuration: headless={}, size={}, reports={}", 
            Configuration.headless, Configuration.browserSize, Configuration.reportsFolder);
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
                String screenshotName = String.format("%s_%d", 
                    scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"),
                    System.currentTimeMillis());
                
                String screenshotPath = Configuration.reportsFolder + "/" + screenshotName + ".png";
                Selenide.screenshot(screenshotPath);
                
                byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", screenshotName + ".png");
                
                String pageSource = Selenide.webdriver().driver().source();
                String pagePath = Configuration.reportsFolder + "/" + screenshotName + ".html";
                FileUtils.writeStringToFile(
                    new java.io.File(pagePath), 
                    pageSource, 
                    "UTF-8"
                );
                
                logger.info("Screenshot saved to: {}", screenshotPath);
                logger.info("Page source saved to: {}", pagePath);
            } catch (Exception e) {
                logger.error("Failed to save screenshot or page source: {}", e.getMessage(), e);
            }
        } else {
            logger.info("Scenario completed successfully: {}", scenario.getName());
        }
        
        SessionContext.getInstance().clearAll();
        
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