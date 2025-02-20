package com.example.pages;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void clickElement(SelenideElement element, String elementName) {
        logger.info("Clicking on element: {}", elementName);
        element.click();
    }

    protected void enterText(SelenideElement element, String text, String elementName) {
        logger.info("Entering text: '{}' into element: {}", text, elementName);
        element.setValue(text);
    }
} 