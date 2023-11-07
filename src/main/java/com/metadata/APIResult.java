package com.metadata;

import com.utility.Utilities;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class APIResult{

  public static Response generateQRCode(){
     String requestBody = "{\n" +
             "  \"request\": {\n" +
             "    \"head\": {\n" +
             "      \"version\": \"2.0\",\n" +
             "      \"function\": \"gcash.acquiring.ordercode.create\",\n" +
             "      \"clientSecret\": \"2018101611204819271136Oaea\",\n" +
             "      \"clientId\": \"2018103116394800000000\",\n" +
             "      \"reqTime\": \"2021-01-31T15:53:00+08:00\",\n" +
             "      \"reqMsgId\": \"94a1760d-c83f-4c67-a1b9-56572469c522\",\n" +
             "      \"reserve\": \"\"\n" +
             "      },\n" +
             "      \"body\": {\n" +
             "      \"envInfo\": {\n" +
             "        \"terminalType\": \"APP\",\n" +
             "        \"osType\": \"Windows.PC\",\n" +
             "        \"extendInfo\": \"\",\n" +
             "        \"orderOsType\": \"IOS\",\n" +
             "        \"sdkVersion\": \"1.0\",\n" +
             "        \"websiteLanguage\": \"en_US\",\n" +
             "        \"tokenId\": \"a8d359d6-ca3d-4048-9295-bbea5f6715a6\",\n" +
             "        \"sessionId\": \"8EU6mLl5mUpUBgyRFT4v7DjfQ3fcauthcenter\",\n" +
             "        \"appVersion\": \"1.0\",\n" +
             "        \"merchantAppVersion\": \"1.0\",\n" +
             "        \"clientKey\": \"e5806b64-598d-414f-b7f7-83f9576eb6fb\",\n" +
             "        \"orderTerminalType\": \"APP\",\n" +
             "        \"clientIp\": \"10.15.8.189\",\n" +
             "        \"merchantTerminalId\": \"fsdynamc\",\n" +
             "        \"merchantIP\": \"10.12.125.1131\",\n" +
             "        \"channel\": \"GCASH_APP\",\n" +
             "        \"rdsData\": \"201482043\"\n" +
             "      },\n" +
             "      \"orderTitle\": \"Product Name\",\n" +
             "      \"merchantTransId\": \"94a1760d-c83f-4c67-a1b9-56572469c522\",\n" +
             "      \"orderMemo\": \"Memo\",\n" +
             "      \"orderAmount\": {\n" +
             "        \"currency\": \"PHP\",\n" +
             "        \"value\": \"400000\"\n" +
             "      },\n" +
             "      \"productCode\": \"51051000101000100046\",\n" +
             "      \"merchantId\": \"217020000647830058151\",\n" +
             "      \"subMerchantId\": \"00001103\",\n" +
             "      \"subMerchantName\": \"Test Merchant 001\",\n" +
             "      \"mcc\": \"1111\",\n" +
             "      \"extendInfo\": \"{}\"\n" +
             "    }\n" +
             "  },\n" +
             "  \"signature\": \"QWFskZPw++6bzC/SeWk/nifr6ZnyLJsGeGNBURnOtsiPDr8oZe6OFY5kjeK6UMjAlN5AKcwd9DH8D+crNz4LSUxJWefttZuUpAapu3kyStQWGTZF+XOd9dpoTafGowU9MYsiWOiJrGXqla8SW1y7ZyOL5ir6pOJW+nDKk6QsPEDdMuWqDpkQydc4yW7DFxC+nPm4rgesS1MsTAg7aipdSVLzK7koLQ9XxZ3AHMEXCKDhz1PoqREABqzisd/4tuBJtWO5UFSH9cUPSBWg+euhagjdbzeuwN8qgBge4e252jd1mpLCzNuEKNe3z68rOB/1nNdgpYUdQGsqNv9rpL9UDw==\"\n" +
             "}";

     RequestSpecification res = RestAssured.given();
     res.headers("Content-type", "application/json");

     Response response = res.body(requestBody).log().all().when().post("https://api-sit.saas.mynt.xyz/gcash/acquiring/ordercode/create.htm").then().log()
                           .all().extract().response();

     System.out.println(response.getStatusCode());

      String resultCode = response.getBody().jsonPath().get("response.body.orderQrCode");

     return response;
  }
}
