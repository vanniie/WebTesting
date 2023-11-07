package com.MLShopPageObjects;

import org.openqa.selenium.By;
public class MLShopPaymongoPage {
    //Check Out Button
    public static By objCheckout = By.xpath("//*[contains(text(),'Checkout')]");

    //Select Pick Up   Branch
    public static By objProvinceStateSelection = By.xpath("//select[@name='Branch Name']"); //also fetch the disable selection
    public static By objCityTownSelection = By.xpath("//select[@name='Branch Name']"); //also fetch the disable selection
    public static By objBranchNameSelection = By.xpath("//select[@name='Branch Name']");

    //Pay Method selection
    public static By objMLWallet = By.xpath("//input[@value='ML WALLET']");
    public static By objEWallet = By.xpath("//input[@value='E-WALLET']");

    //E-Wallet G-cash
    public static By objGcash = By.xpath("//input[@value='GCASH']");

    //E-Wallet GRAB PAY
    public static By objGrabPay = By.xpath("//input[@value='GRAB PAY']");

    //E-Wallet Pay Maya
    public static By objPayMaya = By.xpath("//input[@value='PAYMAYA']");

    public static By objVisaMasterCard = By.xpath("//input[@value='CARD']");

    public static By objDirectOnlineBanking = By.xpath("//input[@value='DIRECT ONLINE BANKING']");

    //Continue Shopping button
    public static By objContinueShoppingbtn = By.xpath("//*[contains(text(),'Continue Shopping')]");

    //Place Order button
    public static By objPlaceOrderbtn = By.xpath("//*[contains(text(),'Place Order')]");
    public  static By objShoppingCartPage = By.xpath("//*[contains(text(),'Shopping Cart')]");

    //Complete your purchase modal
    public  static By objProceedbtn = By.xpath("//*[contains(text(),'Proceed')]");
    public  static By objClosebtn = By.xpath("//*[contains(text(),'Close')]");


    //Web elements in the Pay Monggo Page

    public  static By objPayMongoFooterImg = By.xpath("//img[@alt='PayMongo Logo']");
    public  static By objGrabPaymethod = By.xpath("//*[contains(text(),'GrabPay')]");
    public  static By objBackbtn  = By.xpath("//*[contains(text(),'Back')]");
    public  static By objContinuebtn  = By.xpath("//*[contains(text(),'Back')]");


}
