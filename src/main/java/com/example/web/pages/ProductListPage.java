package com.example.web.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductListPage extends BasePage {
    private static final String PRODUCTS_LIST = ".products-list";
    private static final String PRODUCT_CARDS = ".dt_productCards";

    public boolean isProductListVisible() {
        logger.info("Checking if products list is visible");
        return $(PRODUCT_CARDS).shouldBe(visible).isDisplayed();
    }

    public void clickFirstProduct() {
        logger.info("Clicking first product from the list");
        $$(PRODUCT_CARDS).first().shouldBe(visible).click();
    }
} 