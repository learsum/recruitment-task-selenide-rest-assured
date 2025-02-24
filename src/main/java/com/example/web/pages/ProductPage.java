package com.example.web.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.cssClass;

public class ProductPage extends BasePage {
    private static final String PRODUCT_TITLE = ".productTitle";
    private static final String PRODUCT_PRICE = ".actualText";
    private static final String COLOR_SECTION = ".color-title";
    private static final String STORAGE_SECTION = "[data-qa='PRD_StorageDropDownLabel']";
    private static final String ADD_TO_CART_BUTTON = "[data-qa='PRD_AddToBasket']";
    private static final String UPFRONT_PRICE = "[data-qa='PRD_TotalUpfront'] .dt_price_change";
    private static final String MONTHLY_PRICE = ".style_priceInfoSection .hetZET .dt_price_change";

    public boolean isProductPageVisible() {
        logger.info("Checking if product page is visible");
        return $(PRODUCT_TITLE).shouldBe(visible).isDisplayed() &&
               $(PRODUCT_PRICE).shouldBe(visible).isDisplayed() &&
               $(COLOR_SECTION).shouldBe(visible).isDisplayed() &&
               $(STORAGE_SECTION).shouldBe(visible).isDisplayed() &&
               $$(ADD_TO_CART_BUTTON).filter(visible).filter(not(cssClass("hideDefault"))).first().isDisplayed();
    }

    public void addToCart() {
        logger.info("Adding product to cart");
        $$(ADD_TO_CART_BUTTON).filter(visible).filter(not(cssClass("hideDefault"))).first().click();
    }

    public String getUpfrontPrice() {
        return $$(UPFRONT_PRICE).filter(visible).filter(not(cssClass("hideDefault"))).first().shouldBe(visible).getText().replaceAll("[^0-9]", "");
    }

    public String getMonthlyPrice() {
        return $$(MONTHLY_PRICE).filter(visible).filter(not(cssClass("hideDefault"))).first().shouldBe(visible).getText().replaceAll("[^0-9]", "");
    }
} 