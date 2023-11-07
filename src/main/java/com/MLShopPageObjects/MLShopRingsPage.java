package com.MLShopPageObjects;

import org.openqa.selenium.By;

public class MLShopRingsPage {

//  Rings Names displayed
    public static By  objProductsDisplayed = By.xpath("//*[@class=' text-md text-[#444444] text-right truncate overflow-hidden ...']");
//  Ring item button
    public static By objRingsItemBtn = By.xpath("//*[contains(text(),'Rings')]");
//  Rings first product
    public static By objRingsProduct = By.xpath("(//*[@class=' text-md text-[#444444] text-right truncate overflow-hidden ...'])[1]");
//  Add to cart
    public static By objAddToCartBtn = By.xpath("//*[@class=' bg-[#303030] px-5 py-1  flex mt-2 rounded-lg hover:bg-gray-300 text-[#FFFFFF] place-items-center justify-center font-medium lg:w-[130px] 2sm:w-full 3sm:w-full']");
//  Successfully added to cart popup
    public static By objPopup = By.xpath("//*[@class='translate duration-300 h-full w-full translate-y-0 opacity-100']");
//  Confirm btn
    public static By objConfirmBtn = By.xpath("//*[contains(text(),'Confirm')]");
//  Cart icon
    public static By objCartIcon = By.xpath("//*[@class='px-[10px] cursor-pointer relative mr-2']/child::a");
//  Checkout Btn
    public static By objCheckout = By.xpath("//*[contains(text(),'Checkout')]");
//  BranchName dropdown
    public static By objBranchNameDropdown = By.xpath("//*[@name='Branch Name']");
//  ML Wallet Option
    public static By objMLWalletOption = By.xpath("//*[contains(text(),'ML WALLET')]");
//  Place Order
    public static By objPlaceOrder = By.xpath("//*[contains(text(),'Place Order')]");
//  Proceed Btn
    public static By objProceedBtn = By.xpath("//*[contains(text(),'Proceed')]");

}
