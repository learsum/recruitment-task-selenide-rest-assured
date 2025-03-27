package com.example.web.steps;

import com.example.web.pages.CartPage;
import com.example.web.pages.MainPage;
import com.example.web.pages.ProductListPage;
import com.example.web.pages.ProductPage;
import com.example.web.session.SessionContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhoneSelectionSteps {
    private static final Logger logger = LoggerFactory.getLogger(PhoneSelectionSteps.class);
    private final MainPage mainPage = new MainPage();
    private final ProductListPage productListPage = new ProductListPage();
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();
    private final SessionContext sessionContext = SessionContext.getInstance();

    @When("I navigate to {string}")
    public void openWebsite(String url) {
        mainPage.open(url);
    }

    @Then("Main page is visible")
    public void mainPageIsVisible() {
        assertTrue("Main page should be visible", mainPage.isMainPageVisible());
    }

    @When("I click on {string} in the top menu")
    public void clickTopMenuItem(String menuItem) {
        mainPage.clickMenuItem(menuItem);
    }

    @Then("Dropdown menu is visible")
    public void dropdownMenuIsVisible() {
        assertTrue("Dropdown menu should be visible", mainPage.isDropdownVisible());
    }

    @When("I click {string} from {string} column")
    public void clickSubmenuItem(String itemName, String columnName) {
        mainPage.clickSubmenuItem(itemName, columnName);
    }

    @Then("Products list is visible")
    public void productsListIsVisible() {
        productListPage.isProductListVisible();
    }

    @When("I click first product from the list")
    public void clickFirstProduct() {
        productListPage.clickFirstProduct();
    }

    @Then("Product page is visible")
    public void productPageIsVisible() {
        assertTrue("Product page should be visible", productPage.isProductPageVisible());
    }

    @When("I add product to cart")
    public void addProductToCart() {
        String productUpfrontPrice = productPage.getUpfrontPrice();
        String productMonthlyPrice = productPage.getMonthlyPrice();
        
        // Store prices in session context
        sessionContext.setValue("productUpfrontPrice", productUpfrontPrice);
        sessionContext.setValue("productMonthlyPrice", productMonthlyPrice);
        
        productPage.addToCart();
    }

    @Then("Cart page is visible")
    public void cartPageIsVisible() {
        assertTrue("Cart page should be visible", cartPage.isCartPageVisible());
    }

    @Then("Cart prices match product prices")
    public void cartPricesMatchProductPrices() {
        cartPage.expandPriceDetails();
        String cartUpfrontPrice = cartPage.getUpfrontPrice();
        String cartMonthlyPrice = cartPage.getMonthlyPrice();

        // Retrieve prices from session context
        String productUpfrontPrice = sessionContext.getValue("productUpfrontPrice");
        String productMonthlyPrice = sessionContext.getValue("productMonthlyPrice");

        assertEquals("Upfront price should match", productUpfrontPrice, cartUpfrontPrice);
        assertEquals("Monthly price should match", productMonthlyPrice, cartMonthlyPrice);
    }

    @When("I go to T-Mobile home page")
    public void goToHomePage() {
        mainPage.goToHomePage();
    }

    @Then("Main page is visible with cart icon showing products")
    public void mainPageVisibleWithCart() {
        assertTrue("Main page should be visible with cart icon showing products",
        mainPage.isMainPageVisibleWithCart());
    }
} 