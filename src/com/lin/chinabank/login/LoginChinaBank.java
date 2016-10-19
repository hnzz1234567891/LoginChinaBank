package com.lin.chinabank.login;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginChinaBank {
	
	public static final String WEBDRIVER_PATH = "D:/lin/IEDriverServer/chromedriver.exe";
	
	public static final String LOGIN_URL = "https://ebsnew.boc.cn/boc15/login.html";
	
	public static final String LOGIN_ACCOUNT = "6217852000013970218";
	
	public static final String LOGIN_PASSWORD = "662663";
	
	public static void main(String[] args) {
		String pageSource = new LoginChinaBank().login(LOGIN_ACCOUNT, LOGIN_PASSWORD);
		ChinaBankFetcher fetcher = new ChinaBankFetcher();
		AccountInfo accountInfo = fetcher.displayUserInfo(pageSource);
		System.out.println(accountInfo);
	}
	
	private static void sleep(long sec){
			
			try{
				Thread.sleep(sec);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
	/**
	 * 登录中国银行.
	 * @param loginAccount 登录账户
	 * @param loginPassword 登录密码
	 * @return 登录成功页面源码
	 */
	public String login(String loginAccount,String loginPassword){	
		/**
		 * 这里打开的是Chrome浏览器
		 */
		System.setProperty("webdriver.chrome.driver",WEBDRIVER_PATH);
		
		WebDriver webDriver = new ChromeDriver();
		//WebDriver webDriver = new InternetExplorerDriver();
		
		webDriver.get(LOGIN_URL);
		
		/**
		 * 解决 org.openqa.selenium.ElementNotVisibleException: element not visible 的问题
		 */
//		WebDriverWait wait1 = new WebDriverWait(webDriver,300);
//		wait1.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#container > div > div.login-article > div > ul:nth-child(1) > li:nth-child(1) > div > input")));  
		
		//((JavascriptExecutor)webDriver).executeScript("arguments[0].checked = true;", account_input);
		/**
		 * 输入账号 <input type="text" maxlength="20" id="txt_username_79443" v="用户名" tips="tipsrequired tipsmax tipsmin tips0601" validategroup="{required:true,maxLength:20,minLength:6,reg170:true}" trim="true" class="input warning" pass="false">
		 */
		webDriver.findElement(By.cssSelector("#container > div > div.login-article > div > ul:nth-child(1) > li:nth-child(1) > div > input")).clear();
		webDriver.findElement(By.cssSelector("#container > div > div.login-article > div > ul:nth-child(1) > li:nth-child(1) > div > input")).sendKeys(LOGIN_ACCOUNT);
		
		WebDriverWait wait2 = new WebDriverWait(webDriver,500);
		wait2.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#container > div > div.login-article > div > ul.layout-lr.hide > li:nth-child(2) > div > div > input")));
		
		try{
			/**
			 * 输入密码
			 */
			//<input id="input_div_password_79445_1" type="text" oncopy="return false;" onpaste="return false;" oncut="return false;" style="border:none;width:192px;height:16px;padding:2px 3px;background:#fff;color:#666;">
			webDriver.findElement(By.cssSelector("#container > div > div.login-article > div > ul.layout-lr.hide > li:nth-child(2) > div > div > input")).clear();
			webDriver.findElement(By.cssSelector("#container > div > div.login-article > div > ul.layout-lr.hide > li:nth-child(2) > div > div > input")).sendKeys(LOGIN_PASSWORD);
			
			webDriver.findElement(By.cssSelector("#container > div > div.login-article > div > ul.layout-lr.hide > li:nth-child(4) > div > a")).click();//点击“登录”按钮
		}catch(Exception e){
			System.out.println("出现异常，重新输入.等待用户输入验证码.");
			
			sleep(10000);//等待用户输入验证码
			
			/**
			 * 输入密码
			 */
			webDriver.findElement(By.cssSelector("#cardLogin_ul > li:nth-child(2) > div > div > input")).clear();
			webDriver.findElement(By.cssSelector("#cardLogin_ul > li:nth-child(2) > div > div > input")).sendKeys(LOGIN_PASSWORD);
			
			webDriver.findElement(By.cssSelector("#cardLogin_ul > li:nth-child(4) > div > a")).click();//点击“查询”按钮
			
		}
		
		while(!webDriver.getCurrentUrl().equals("https://ebsnew.boc.cn/boc15/welcome_ele.html?v=20160930022741473&locale=zh&login=card&segment=1")){
		}//等待登录成功
		
		System.out.println("Login China Bank Success!!!");
		String currentUrl = webDriver.getCurrentUrl();
		System.out.println("Current URL"+currentUrl);
		
		/**
		 * 登录成功后，页面需要一定时间加载出来，否则，无法获得登录成功页面的源码
		 */
		WebDriverWait wait3 = new WebDriverWait(webDriver,10);
		wait3.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#div_accountnumber_740994 > span:nth-child(2)")));
		
		webDriver.manage().window().maximize();
		
		return webDriver.getPageSource();
	}
	
	/**
	 * 得到Cookies.
	 * @param webDriver
	 * @return
	 */
	private static Set<Cookie> getCookies(WebDriver webDriver){
		Set<Cookie> cookies = webDriver.manage().getCookies();
		return cookies;
	}

}
