package com.qafox.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.qafox.pages.HomePage;
import com.qafox.pages.ShoppingCartPage;
import com.qafox.utils.TestBase;

public class ShoppingCartTest extends TestBase {
    
    @Test
    public void testShoppingCartFunctionality() {
        HomePage homePage = new HomePage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        
        // 1. Open URL
        homePage.openUrl();
        
        // 2. Add 2 MacBooks to shopping cart
        homePage.addMacBooksToCart(2);
        
        // 3. Validate success message appears
        Assert.assertTrue(homePage.isSuccessMessageVisible(), 
            "Success message should be visible after adding product to cart");
        
        // 4. Open shopping cart
        homePage.openShoppingCart();
        
        // 5. Validate the navigation by comparing URL
        Assert.assertTrue(cartPage.validateURL(), 
            "Should be on shopping cart page");
        
        // 6 & 7. Validate quantity, unit price and total price
        Assert.assertTrue(cartPage.validateCartItem("MacBook", 2), 
            "Cart should contain 2 MacBooks");
            
        Assert.assertTrue(cartPage.validatePricing("MacBook"), 
            "Total price should be correctly calculated based on quantity and unit price");
    }
} 