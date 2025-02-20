package com.example.steps;

import com.example.pages.MainPage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class PhoneSelectionSteps {
    private static final Logger logger = LoggerFactory.getLogger(PhoneSelectionSteps.class);
    private final MainPage mainPage = new MainPage();
    
    @When("I open the browser")
    public void openBrowser() {
        logger.info("Browser configuration is handled by TestHooks");
    }
    
    @Then("Browser is opened")
    public void browserIsOpen() {
        logger.info("Browser is ready for testing");
    }
    
    @When("I navigate to {string}")
    public void openWebsite(String url) {
        mainPage.open();
    }
    
    @Then("Main page is visible")
    public void mainPageIsVisible() {
        assertTrue("Main page should be visible", mainPage.isMainPageVisible());
    }
    
    @When("I click on {string} in the top menu")
    public void clickTopMenuItem(String menuItem) {
        if (menuItem.equals("Urządzenia")) {
            mainPage.clickDevicesMenu();
        }
    }
    
    @Then("Dropdown menu is visible")
    public void dropdownMenuIsVisible() {
        assertTrue("Dropdown menu should be visible", mainPage.isDropdownVisible());
    }
} 