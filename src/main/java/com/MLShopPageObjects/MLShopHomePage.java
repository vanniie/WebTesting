package com.MLShopPageObjects;

import org.openqa.selenium.By;

public class MLShopHomePage {

//  Available Jewelry item
    public static By objAvailableJewelryItem = By.xpath("//*[@class='mt-[20px] text-[#333333] font-bold bg-white border-[2px] border-solid border-[#cfa529] rounded-[50px] py-[10px] px-[5px] min-w-[65%] text-center mb-[10px] w-auto mx-auto 2sm:text-xs']");
//  Watches
    public static By objWatchesItems = By.xpath("//*[@class=' flex flex-wrap justify-center gap-5 mt-5 pb-[50px]']/child::a/child::div");
//  Necklaces & Pendants
    public static By objNecklacesAndPendants = By.xpath("//*[contains(text(),'Necklaces & Pendants')]");
//  Bracelets & Bangles
    public static By objBraceletsAndBangles = By.xpath("//*[contains(text(),'Bracelets & Bangles')]");
//  GREAT SUMMER SALE
    public static By objGreatSummerSale = By.xpath("//*[contains(text(),'GREAT SUMMER SALE')]");
//  Earrings
    public static By objEarrings = By.xpath("//*[contains(text(),'Earrings')]");
}
