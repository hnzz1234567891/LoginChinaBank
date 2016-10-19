package com.lin.chinabank.login;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 中国银行账户信息抓取类.
 * @author lin
 *
 */
public class ChinaBankFetcher {
	
	/**
	 * 显示用户信息.
	 * @param pageSource
	 */
	public AccountInfo displayUserInfo(String pageSource){
		
		System.out.println(pageSource);
		
		if(pageSource != null && pageSource.length() != 0){
			
			Document document = Jsoup.parse(pageSource);
			String accountName = document.select("#cardMain > div > div:nth-child(2) > p > label").get(0).text() + " : " + 
					document.select("#div_accountnumber_740994 > span:nth-child(1)").get(0).text() + " " + 
					document.select("#div_accountnumber_740994 > span:nth-child(2)").get(0).text() + " " +
					document.select("#div_accountnumber_740994 > span:nth-child(3)").get(0).text();
			String status = document.select("#div_zhzt_740995").get(0).text();
			String setUpLocation = document.select("#div_establishpoint_740997").get(0).text();
			String setUpTime = document.select("#div_khrq_740999").get(0).text();
			String city = document.select("#cardMain > div > div:nth-child(2) > ul > li:nth-child(4) > div").get(0).text();
			String moneyType = document.select("#cardMain > div > div:nth-child(3) > table > tbody > tr > td.fi").get(0).text();
			String accountBalance = document.select("#cardMain > div > div:nth-child(3) > table > tbody > tr > td:nth-child(3)").get(0).text();
			String activeBalance = document.select("#cardMain > div > div:nth-child(3) > table > tbody > tr > td:nth-child(4)").get(0).text();
			
			return new AccountInfo(accountName, status, setUpLocation, setUpTime, city, moneyType, accountBalance, activeBalance);
		}
		return null;
	}

}
