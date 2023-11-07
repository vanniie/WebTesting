package com.MLShopPageObjects;

import org.openqa.selenium.By;
public class MLShoopShippingDetails {

    public static By objMlShoppingDetailsPage= By.xpath("//*[@class=' max-w-[1200px] w-full h-[80px] flex justify-between px-[20px] m-auto z-999']/following-sibling::div[2]");

    public static By objMlShopProductHeader= By.xpath("(//div/p[2])[1]");
    public static By objQuantity = By.xpath("//*[contains(text(),'Quantity')]");
    public static By objItemSubtotal = By.xpath("//*[contains(text(),'Item Subtotal')]");
    public static By objTotalOrder = By.xpath("(//*[contains(text(),'Products Ordered')])[2]");

    public static By objServiceFee = By.xpath("//*[contains(text(),'Select Pick Up Branch')]");
    public static By objServiceFeeDetails = By.xpath("//*[@class='flex flex-wrap']/child::div[1]");

    public static By objPaymentMethodMlWallet = By.xpath("//*[contains(text(),'ML WALLET')]");
    public static By objPaymentMethodOnlineBanking = By.xpath("//*[contains(text(),'DIRECT ONLINE BANKING')]");

    public static By objMerchandiseTotal = By.xpath("//*[contains(text(),'Merchandise Total')]");

    public static By objServiceTotal = By.xpath("//*[contains(text(),'Service Total')]");
    public static By objShippingTotal = By.xpath("//*[contains(text(),'Shipping Total')]");
    public static By objTotalPayment = By.xpath("//*[contains(text(),'Total Payment')]");
    public static By objPlaceOrder = By.xpath("//*[contains(text(),'Place Order')]");



}
