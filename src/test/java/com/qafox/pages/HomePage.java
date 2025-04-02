package com.qafox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By searchBox = By.name("search");
    private final By searchButton = By.cssSelector("button.btn-default");
    private final By macBookLink = By.xpath("//a[contains(text(), 'MacBook')]");
    private final By quantityInput = By.name("quantity");
    private final By addToCartButton = By.id("button-cart");
    private final By cartButton = By.cssSelector("a[title='Shopping Cart']");
    private final By viewCartLink = By.cssSelector("a[href*='checkout/cart']");
    private final By successMessage = By.cssSelector("div.alert-success");
    private final By stockStatus = By.xpath("//div[contains(@class, 'stock')]//span");
    private final By productPrice = By.xpath("//div[contains(@class, 'price')]//span[contains(@class, 'price-normal')]");
    private final By productTitle = By.xpath("//h1[contains(text(), 'MacBook')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openUrl() {
        driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));
    }

    public void addMacBooksToCart(int quantity) {
        try {
            // Search for MacBook
            WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(searchBox));
            searchInput.clear();
            searchInput.sendKeys("MacBook");
            driver.findElement(searchButton).click();

            // Wait for search results and click on MacBook
            WebElement macBookElement = wait.until(ExpectedConditions.elementToBeClickable(macBookLink));
            macBookElement.click();

            // Wait for product page to load and verify we're on the correct page
            wait.until(ExpectedConditions.presenceOfElementLocated(productTitle));
            System.out.println("Product page loaded successfully");

            // Check stock status
            try {
                WebElement stockElement = wait.until(ExpectedConditions.presenceOfElementLocated(stockStatus));
                String stockText = stockElement.getText().trim();
                System.out.println("Stock status: " + stockText);

                if (stockText.contains("Out Of Stock")) {
                    throw new RuntimeException("Product is out of stock");
                }
            } catch (Exception e) {
                System.out.println("Warning: Could not find stock status element. Continuing with test...");
            }

            // Get product price
            try {
                WebElement priceElement = driver.findElement(productPrice);
                String price = priceElement.getText().trim();
                System.out.println("Product price: " + price);
            } catch (Exception e) {
                System.out.println("Warning: Could not find price element. Continuing with test...");
            }

            // Set quantity and add to cart
            WebElement quantityField = wait.until(ExpectedConditions.presenceOfElementLocated(quantityInput));
            quantityField.clear();
            quantityField.sendKeys(String.valueOf(quantity));
            
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            addToCartBtn.click();

            // Wait for success message
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

        } catch (Exception e) {
            System.out.println("Error adding MacBooks to cart: " + e.getMessage());
            throw e;
        }
    }

    public boolean isSuccessMessageVisible() {
        try {
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            String messageText = message.getText();
            System.out.println("Success message: " + messageText);
            return message.isDisplayed() && messageText.contains("Success");
        } catch (Exception e) {
            System.out.println("Error checking success message: " + e.getMessage());
            return false;
        }
    }

    public void openShoppingCart() {
        try {
            // Click on cart button
            WebElement cartElement = wait.until(ExpectedConditions.elementToBeClickable(cartButton));
            cartElement.click();

            // Wait for the view cart link to be present and click it
            WebElement viewCartElement = wait.until(ExpectedConditions.elementToBeClickable(viewCartLink));
            viewCartElement.click();

            // Wait for cart page to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.table-responsive")));
            
            // Print current URL for validation
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
        } catch (Exception e) {
            System.out.println("Error opening shopping cart: " + e.getMessage());
            throw e;
        }
    }
} 