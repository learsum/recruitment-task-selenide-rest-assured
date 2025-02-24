package com.example.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ACCEPT_COOKIES_BUTTON = "#didomi-notice-agree-button";
    private static final String DROPDOWN_MENU = ".menu-dropdown";
    private static final String MENU_ITEM_PATTERN = "//button[contains(@class, 'menu-dropdown-item') and text()='%s']";
    private static final String COLUMN_HEADER_PATTERN = "//p[contains(@class, 'font-extrabold') and text()='%s']";
    private static final String SUBMENU_ITEM_PATTERN =
            "//p[contains(@class, 'font-extrabold') and text()='%s']" +
                    "/parent::span/following-sibling::ul//span[contains(text(), '%s')]";

    protected void acceptCookies() {
        logger.info("Accepting cookies");
        $(ACCEPT_COOKIES_BUTTON).shouldBe(visible).click();
    }

    public void clickMenuItem(String menuName) {
        logger.info("Clicking on menu item: {}", menuName);
        $x(String.format(MENU_ITEM_PATTERN, menuName)).shouldBe(visible).click();
    }

    public boolean isDropdownVisible() {
        logger.info("Checking if dropdown menu is visible");
        return $(DROPDOWN_MENU).shouldBe(visible).isDisplayed();
    }

    public void clickSubmenuItem(String itemName, String columnName) {
        logger.info("Clicking '{}' in '{}' column", itemName, columnName);
        $x(String.format(COLUMN_HEADER_PATTERN, columnName)).shouldBe(visible);
        $x(String.format(SUBMENU_ITEM_PATTERN, columnName, itemName)).shouldBe(visible).click();
    }
}