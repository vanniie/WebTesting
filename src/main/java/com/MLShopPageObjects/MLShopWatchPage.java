package com.MLShopPageObjects;

import org.openqa.selenium.By;

public class MLShopWatchPage {

//  Displayed watch products amount
    public static By objWatchAmounts = By.xpath("//*[@class=' text-red-500 font-bold  border-b-gray-400']");
//  Watch Image
    public static By objWatchItem = By.xpath("(//*[@class=' flex flex-wrap justify-center gap-5 mt-5 pb-[50px]']/child::a/child::div)[1]");
//  Discount banners
    public static By objDiscountBanners = By.xpath("//*[@class=' rounded-lg text-xs px-2 py-1 text-white  bg-red-500']");
//
    public static By objWatchProductDisplayed = By.xpath("//*[@class=' text-[#444444] text-xs text-right']");
}
