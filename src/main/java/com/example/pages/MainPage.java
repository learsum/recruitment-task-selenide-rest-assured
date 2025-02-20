package com.example.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPage extends BasePage {
    private static final String MAIN_LOGO = "#logo-svg";
    private static final String ACCEPT_COOKIES_BUTTON = "#didomi-notice-agree-button";
    private static final String DEVICES_MENU = "//span[text()='Urządzenia']";
    private static final String DEVICES_DROPDOWN = ".dropdown-menu";
    
    public MainPage open() {
        logger.info("Opening T-Mobile main page");
        Selenide.open("https://www.t-mobile.pl/");
        acceptCookies();
        return this;
    }
    
    private void acceptCookies() {
        logger.info("Accepting cookies");
        $(ACCEPT_COOKIES_BUTTON).shouldBe(visible).click();
    }
    
    public void clickDevicesMenu() {
        logger.info("Clicking on Devices menu");
        $x(DEVICES_MENU).shouldBe(visible).click();
    }
    
    public boolean isDropdownVisible() {
        logger.info("Checking if dropdown menu is visible");
        return $(DEVICES_DROPDOWN).shouldBe(visible).isDisplayed();
    }
    
    public boolean isMainPageVisible() {
        logger.info("Checking if main page is visible");
        return $(MAIN_LOGO).shouldBe(visible).isDisplayed();
    }
} 