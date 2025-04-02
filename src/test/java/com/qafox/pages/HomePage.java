package com.qafox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(css = "button.btn-default")
    private WebElement searchButton;

    @FindBy(css = "div.alert-success")
    private WebElement successMessage;

    @FindBy(css = "a[title='Shopping Cart']")
    private WebElement shoppingCartLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void searchProduct(String productName) {
        searchInput.clear();
        searchInput.sendKeys(productName);
        searchButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.product-thumb")));
    }

    public void addToCart(String productName, int quantity) {
        // First, find all product thumbnails
        List<WebElement> productThumbnails = driver.findElements(By.cssSelector("div.product-thumb"));
        
        // Find the specific product thumbnail
        WebElement targetThumbnail = null;
        for (WebElement thumbnail : productThumbnails) {
            if (thumbnail.getText().contains(productName)) {
                targetThumbnail = thumbnail;
                break;
            }
        }
        
        if (targetThumbnail == null) {
            throw new RuntimeException("Product thumbnail not found: " + productName);
        }
        
        // Find and click the product link within the thumbnail
        WebElement productLink = targetThumbnail.findElement(By.cssSelector("h4 a"));
        String productUrl = productLink.getAttribute("href");
        driver.get(productUrl);
        
        // Wait for the page to load and verify we're on the product page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.product-details")));
        
        // Set quantity
        WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='quantity']")));
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
        
        // Click add to cart button
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#button-cart")));
        addToCartButton.click();
        
        // Wait for success message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert-success")));
    }

    public boolean isSuccessMessageVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openShoppingCart() {
        shoppingCartLink.click();
        wait.until(ExpectedConditions.urlContains("checkout/cart"));
    }
} 