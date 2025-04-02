package com.qafox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @FindBy(css = "div#content form")
    private WebElement cartForm;

    @FindBy(css = "input[name^='quantity']")
    private List<WebElement> quantityInputs;

    @FindBy(css = "table.table-bordered tbody tr")
    private List<WebElement> cartRows;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    private void waitForPageLoad() {
        wait.until((ExpectedCondition<Boolean>) wd ->
            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void validateCartItems() {
        // Wait for the page to load completely
        waitForPageLoad();
        
        // Wait for the cart form to be visible
        wait.until(ExpectedConditions.visibilityOf(cartForm));
        
        // Check quantity inputs
        wait.until(ExpectedConditions.visibilityOfAllElements(quantityInputs));
        WebElement quantityInput = quantityInputs.get(0);
        String quantity = quantityInput.getAttribute("value");
        Assert.assertEquals(quantity, "2", "Expected quantity to be 2, but found " + quantity);
    }

    public void validatePricing() {
        // Wait for the page to load completely
        waitForPageLoad();
        
        // Wait for the cart form to be visible
        wait.until(ExpectedConditions.visibilityOf(cartForm));
        
        // Find all table rows
        List<WebElement> rows = driver.findElements(By.cssSelector("table.table-bordered tbody tr"));
        Assert.assertFalse(rows.isEmpty(), "No rows found in the cart table");
        
        // Find the MacBook row
        WebElement macBookRow = null;
        for (WebElement row : rows) {
            if (row.getText().contains("MacBook")) {
                macBookRow = row;
                break;
            }
        }
        
        Assert.assertNotNull(macBookRow, "MacBook row not found in cart");
        
        // Get unit price and total price from the row
        List<WebElement> cells = macBookRow.findElements(By.tagName("td"));
        String unitPriceText = cells.get(4).getText(); // 5th column
        String totalPriceText = cells.get(5).getText(); // 6th column
        
        double unitPrice = Double.parseDouble(unitPriceText.replace("$", "").replace(",", ""));
        double totalPrice = Double.parseDouble(totalPriceText.replace("$", "").replace(",", ""));
        
        // Calculate expected total (unit price * 2)
        double expectedTotal = unitPrice * 2;
        
        // Allow for small rounding differences
        Assert.assertTrue(Math.abs(totalPrice - expectedTotal) < 0.01, 
            String.format("Total price is not correct. Expected: %.2f, Actual: %.2f", expectedTotal, totalPrice));
    }
} 