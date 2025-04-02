package com.qafox.tests;

import com.qafox.base.BaseTest;
import com.qafox.pages.HomePage;
import com.qafox.pages.ShoppingCartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {

    @Test
    public void testShoppingCartFunctionality() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("MacBook");
        homePage.addToCart("MacBook", 2);
        Assert.assertTrue(homePage.isSuccessMessageVisible(), "Success message not visible");
        homePage.openShoppingCart();
        
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout/cart"), "Not on cart page");
        cartPage.validateCartItems();
        cartPage.validatePricing();
    }
} 