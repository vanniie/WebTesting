package com.MLShopPageObjects;

import org.openqa.selenium.By;

public class MLShopProfileLoginPage {

//  Profile Icon
    public static By objProfileIcon = By.xpath("//*[@class='px-[10px] cursor-pointer 2sm:hidden 3sm:hidden relative']/child::div");
//  Contact Header
    public static By objContact = By.xpath("//*[contains(text(),'CONTACT')]");
//  Mobile NumberInput Field
    public static By objMobileNumberInputField = By.xpath("//*[@id='OTP']");
//  Get OTP Btn
    public static By objLoginBtn = By.xpath("(//*[contains(text(),'Login')])[2]");
//  OTP Input field
    public static By objOTpInputField = By.xpath("//*[@class=' grid grid-cols-6 w-3/4 m-auto my-10 gap-3']");
//  One time pin popup
    public static By objOTPPopup = By.xpath("//*[@class=' font-semibold text-lg']");
//  Successfully login popup
    public static By objSuccessfullyLoginPopup = By.xpath("//*[@id='swal2-html-container']");
//  Okay Button
    public static By objOkayBtn = By.xpath("//*[contains(text(),'Okay')]");
//  Hi Button
    public static By objProfileName = By.xpath("//*[@class='text-[#333333] text-[14px] pl-[5px]']");
//  ProfileDropDown
    public static By objProfileDropDownElement = By.xpath("(//*[contains(text(),'Profile')])[1]");
//  Purchase History
    public static By objPurchaseHistoryDropDownElement = By.xpath("(//*[contains(text(),'Purchase History')])[1]");
//  LogOut
    public static By objLogOutDropDownElement = By.xpath("(//*[contains(text(),'Logout')])[1]");
//  Profile Name
    public static By objFullNameInProfilePage = By.xpath("(//*[@class='bg-gray-200 px-[10px] py-[5px] rounded-[3px] min-w-[50%] max-w-[75%] 2sm:text-[13px] 3sm:text-[13px] 3sm:max-w-[100%] 3sm:w-[200px] sm:w-[300px] 2sm:w-[200px]'])[1]");
//  Email Address
    public static By objEmailAddressInProfilePage = By.xpath("(//*[@class='bg-gray-200 px-[10px] py-[5px] rounded-[3px] min-w-[50%] max-w-[75%] 2sm:text-[13px] 3sm:text-[13px] 3sm:max-w-[100%] 3sm:w-[200px] sm:w-[300px] 2sm:w-[200px]'])[2]");
//  Mobile Number
    public static By objMobileNumberInProfilePage = By.xpath("//*[@class='bg-gray-200 px-[10px] py-[5px] rounded-[3px] min-w-[50%] max-w-[75%] 2sm:text-[13px] 3sm:text-[13px] 3sm:max-w-[100%] sm:w-[300px] 2sm:w-[200px] 3sm:w-[200px]']");
//   Jewelry items
    public static By objMLShopItemsInPurchaseHistory = By.xpath("//*[@class='ml-2 text-base font-semibold']");

}
