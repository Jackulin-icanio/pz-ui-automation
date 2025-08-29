package com.propertyzar.ui.base;

import com.aventstack.extentreports.ExtentTest;
import lombok.Getter;

public abstract class Element<D, E> {

    protected final WebDriver<D> webDriver;

    protected final ExtentTest etTest;

    @Getter
    protected final E element;

    protected Element(WebDriver<D> webDriver, ExtentTest etTest, E element) {
        this.webDriver = webDriver;
        this.etTest = etTest;
        this.element = element;
    }

    public Element(WebDriver<D> webDriver, ExtentTest etTest, String xpath, ExpectedConditions expConditions, int timeOut) {
        this.webDriver = webDriver;
        this.etTest = etTest;
        this.element = detectElement(xpath, expConditions, timeOut);
    }

    public abstract E detectElement(String xpath, ExpectedConditions expConditions, int timeOut);

    public abstract String getText();

    public abstract void setText(String text);

    public abstract void clickElement(String logMessage);

    public abstract boolean isDisplayed();

    public abstract boolean isEnabled();

    public abstract String getAttribute(String name);

    public abstract void scrollFromLeftToRight();

    public enum ExpectedConditions {
        ELEMENT_TO_BE_CLICKABLE, PRESENCE_OF_ELEMENT_LOCATED, VISIBILITY_OF_ALL_ELEMENTS_LOCATED, INVISIBILITY_OF_ELEMENT_LOCATED;
    }
}