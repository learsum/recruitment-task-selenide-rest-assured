package com.example.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CartPage extends BasePage {
    private static final String CART_HEADER = ".basketHeaderText";
    private static final String PRICE_DETAILS = "[data-qa='BKT_PriceAccordian'] .accordionTitle";
    private static final String UPFRONT_PRICE = "[data-qa='BKT_ItemlupFront']";
    private static final String MONTHLY_PRICE = "[data-qa='BKT_ItemMonthly']";

    public boolean isCartPageVisible() {
        logger.info("Checking if cart page is visible");
        return $(CART_HEADER).shouldBe(visible).isDisplayed() &&
               $(PRICE_DETAILS).shouldBe(visible).isDisplayed();
    }

    public void expandPriceDetails() {
        logger.info("Expanding price details section");
        $(PRICE_DETAILS).shouldBe(visible).click();
    }

    public String getUpfrontPrice() {
        return $(UPFRONT_PRICE).shouldBe(visible).getText();
    }

    public String getMonthlyPrice() {
        return $(MONTHLY_PRICE).shouldBe(visible).getText();
    }
} 