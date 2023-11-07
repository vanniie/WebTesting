package com.mlshop.test;

import org.testng.annotations.Test;

public class MLWalletWebScriptsSuite1 extends BaseTest{

    @Test(priority = 1)
    public void shopItemsShippingDetailsPageUIValidation() throws Exception {
        mlWalletWeb.shopItemsShippingDetailsPageUIValidation();
    }

    @Test(priority = 2)
    public void shopItemsAddedItemInCartDeletedPopupValidationYesButton() throws Exception
    {
        mlWalletWeb.shopItemsAddedItemInCartDeletedPopupValidationYesButton();
    }

    @Test(priority = 3)
    public void shopItemsAddedItemInCartDeletedPopupValidationCancelButton() throws Exception
    {
        mlWalletWeb.shopItemsAddedItemInCartDeletedPopupValidationCancelButton();
    }

}
