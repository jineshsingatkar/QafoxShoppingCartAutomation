package com.qafox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ShoppingCartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By cartTable = By.cssSelector("div.table-responsive");
    private final By quantityInput = By.cssSelector("input[type='text']");
    private final By updateButton = By.cssSelector("button[data-original-title='Update']");
    private final By unitPrice = By.xpath(".//td[contains(@class, 'text-right')][1]");
    private final By totalPrice = By.xpath(".//td[contains(@class, 'text-right')][2]");

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean validateCartItem(String productName, int expectedQuantity) {
        try {
            // Wait for cart table to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));

            // Find the product row
            String xpathExpression = String.format("//div[@class='table-responsive']//tr[.//a[contains(text(), '%s')]]", productName);
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
            System.out.println("Found product row: " + row.getText());

            // Get quantity input
            WebElement quantityField = row.findElement(quantityInput);
            String currentQuantity = quantityField.getAttribute("value");
            System.out.println("Current quantity: " + currentQuantity);

            // Update quantity if needed
            if (!currentQuantity.equals(String.valueOf(expectedQuantity))) {
                quantityField.clear();
                quantityField.sendKeys(String.valueOf(expectedQuantity));
                row.findElement(updateButton).click();
                
                // Wait for page to refresh
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Verify quantity after update
            quantityField = row.findElement(quantityInput);
            String updatedQuantity = quantityField.getAttribute("value");
            System.out.println("Updated quantity: " + updatedQuantity);

            return updatedQuantity.equals(String.valueOf(expectedQuantity));
        } catch (Exception e) {
            System.out.println("Error validating cart item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean validatePricing(String productName) {
        try {
            // Wait for cart table to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));

            // Find the product row
            String xpathExpression = String.format("//div[@class='table-responsive']//tr[.//a[contains(text(), '%s')]]", productName);
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));

            // Get unit price and total price
            String unitPriceStr = row.findElement(unitPrice).getText().replace("$", "").replace(",", "").trim();
            String totalPriceStr = row.findElement(totalPrice).getText().replace("$", "").replace(",", "").trim();

            // Parse prices
            double unitPrice = Double.parseDouble(unitPriceStr);
            double totalPrice = Double.parseDouble(totalPriceStr);

            // Get quantity
            WebElement quantityField = row.findElement(quantityInput);
            int quantity = Integer.parseInt(quantityField.getAttribute("value"));

            // Calculate expected total
            double expectedTotal = unitPrice * quantity;

            System.out.println("Unit Price: $" + unitPrice);
            System.out.println("Quantity: " + quantity);
            System.out.println("Expected Total: $" + expectedTotal);
            System.out.println("Actual Total: $" + totalPrice);

            // Compare with small delta for floating-point comparison
            return Math.abs(expectedTotal - totalPrice) < 0.01;
        } catch (Exception e) {
            System.out.println("Error validating pricing: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateURL() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("checkout/cart") || currentUrl.contains("route=checkout/cart");
    }
} 