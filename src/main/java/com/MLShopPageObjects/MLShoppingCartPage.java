package com.MLShopPageObjects;

import org.openqa.selenium.By;

public class MLShoppingCartPage {

    //Shopping Cart page
    public static  By objShoppingCartPage = By.xpath("//*[contains(text(),'Shopping Cart')]");

    //Item is already in the cart
    public  static By objItemIsAlreadyInTheCart = By.xpath("//*[@class='swal2-icon swal2-warning swal2-icon-show']");

    //Select first item listed in the cart checkbox
    public static  By objCheckBox = By.xpath("//*[@class= ' w-full']/div[3]//input");

    // Select all items listed in the cart checkbox
    public static  By objAllCheckBox = By.xpath("//*[@class= ' w-full']//input");

    //Delete button
    public static By objDeletebtn = By.xpath("//*[@class= ' w-full']/div[3]//button");

    //Are you sure to remove this item?
    public static By objConfirmationQuestion = By.xpath("//*[contains(text(),'Are you sure to remove this item?')]");

    //Confirmation modal Yes button
    public static By objYesbtn = By.xpath("//*[contains(text(),'Yes')]");

    //Confirmation modal Cancel button
    public static By objCancelbtn = By.xpath("//*[contains(text(),'Cancel')]");

    //Confirmation modal X icon
    public static By objExitIcon = By.xpath("");
}
