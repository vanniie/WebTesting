package com.parameters;

//import static com.jayway.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.xml.XmlSuite;
import org.testng.IAlterSuiteListener;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ParameterInjector implements IAlterSuiteListener {

	@Override
	public void alter(List<XmlSuite> suites) {
		XmlSuite suite = suites.get(0);
		Map<String, String> params = new HashMap<>();

		// Pass environment data
		params.put("url", suite.getParameter("url"));
		params.put("browserType", suite.getParameter("browserType"));
		params.put("UserName", suite.getParameter("UserName"));
		params.put("Password", suite.getParameter("Password"));

		params.put("userType", suite.getParameter("userType"));
		params.put("NonsubscribedUserName", suite.getParameter("NonsubscribedUserName"));

		params.put("runModule", suite.getParameter("runModule"));
		params.put("runMode", suite.getParameter("runMode"));
		params.put("pTabName", suite.getParameter("pTabName"));

		params.put("SugarBoxPhoneNum", suite.getParameter("SugarBoxPhoneNum"));
		params.put("SubscribedUserName799", suite.getParameter("SubscribedUserName799"));
		System.out.println("Browser type : " + suite.getParameter("browserType"));

		if (suite.getParameter("url").equals("Url_Name")) {
			params.put("url", "pass Url");
		}
		params.put("18+Content", suite.getParameter("18+Content"));

		// Pass region specific data
		Response regionResponse = RestAssured.given().urlEncodingEnabled(false).when()
				.get("https://xtra.zee5.com/country");
		String region = regionResponse.getBody().jsonPath().getString("state_code");
		System.out.println("Region : " + region);
		if (region.equals("KA")) {
			params.put("searchModuleSearchKey", "dummy");
			if (suite.getFileName().contains("WEB_Mixpanel")) {
				params.put("ClubUserName", suite.getParameter("ClubUserName"));
				params.put("ClubPassword", suite.getParameter("ClubPassword"));
			}
			if (suite.getParameter("url").equals("newpwa")) {
				params.put("dummy", "dummy");
			} else {
				params.put("dummy", "dummy");
			}
		}
		if (region.equals("MH")) {
			params.put("dummy", "dummy");
			if (suite.getFileName().contains("WEB_Mixpanel")) {
				params.put("ClubUserName", suite.getParameter("ClubUserName"));
				params.put("ClubPassword", suite.getParameter("ClubPassword"));
				params.put("SettingsNonSubscribedUserName", suite.getParameter("SettingsNonSubscribedUserName"));
				params.put("SettingsNonSubscribedPassword", suite.getParameter("SettingsNonSubscribedPassword"));
				params.put("SettingsSubscribedUserName", suite.getParameter("SettingsSubscribedUserName"));
				params.put("SettingsSubscribedPassword", suite.getParameter("SettingsSubscribedPassword"));
			}
		}
		suite.setParameters(params);
	}
}
