package com.example.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPage extends BasePage {
    private static final String SLICK_TRACK = ".slick-track";
    private static final String CART_ICON = "a[title='Koszyk']";
    private static final String CART_COUNTER = "a[title='Koszyk'] div[class*='rounded-full']";
    private static final String HOME_LOGO = "a.logoWrap";
    
    public MainPage open(String url) {
        logger.info("Opening T-Mobile main page");
        Selenide.open(url);
        acceptCookies();
        return this;
    }

    public boolean isMainPageVisible() {
        logger.info("Checking if main page is visible");
        return $(SLICK_TRACK).shouldBe(visible).isDisplayed();
    }

    public void goToHomePage() {
        logger.info("Going to T-Mobile home page");
        $(HOME_LOGO).shouldBe(visible).click();
    }

    public boolean isMainPageVisibleWithCart() {
        logger.info("Checking if main page is visible with cart icon");
        return isMainPageVisible() &&
               $(CART_ICON).shouldBe(visible).isDisplayed() &&
               $(CART_COUNTER).shouldBe(visible).getText().equals("1");
    }
} 