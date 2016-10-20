package utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 验证码识别.
 * 
 * @author lin
 *
 */
public class VerificationCodeUtils {

	private static final String VERIFICATION_CODE_IMAGE_PATH = "C:/Users/admin/Desktop/test.png";// 验证码图片生成目录

	private static final String VERIFICATION_CODE_FILE_PATH = "C:/Users/admin/Desktop/test";// 生成的验证码本地文件目录

	private VerificationCodeUtils() {
	}

	/**
	 * 输入验证码.
	 * 
	 * @param webDriver
	 *            Selenium的WebDriver
	 * @param codeInput
	 *            Selenium获取的网页验证码控件
	 */
	public static void inputVerificationCode(WebDriver driver,WebElement codeInput) {

		try {

			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			createCodeImg(driver, codeInput, VERIFICATION_CODE_IMAGE_PATH);

			Runtime rt = Runtime.getRuntime();

			String cmdCommand = "cmd.exe /C  tesseract.exe " + VERIFICATION_CODE_IMAGE_PATH + "  "
					+ VERIFICATION_CODE_FILE_PATH + " -1 ";

			rt.exec(cmdCommand);

			Thread.sleep(1000);

			String verificationCode = readVerificationCodeFromFile(VERIFICATION_CODE_FILE_PATH + ".txt");
			System.out.println("----------------验证码:" + verificationCode + "----------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成验证码本地图片文件.
	 * 
	 * @param element
	 *            Selenium获取的网页验证码控件
	 * @param path
	 *            验证码图片生成目录
	 * @throws Exception 
	 */
	private static void createCodeImg(WebDriver driver,WebElement element,String path)
			throws Exception {
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			Point p = null;
			try{
				p = element.getLocation();
			}catch(StaleElementReferenceException e){
				/**
				 * 解决WebElement失效抛出异常的问题 
				 * stale element reference: element is not attached to the page document，
				 * 在失效时重新获取一次Webelement
				 */
				element = driver.findElement(By.xpath("//*[@id=\"captcha_debitCard\"]"));
				p = element.getLocation();
			}
			int width = element.getSize().getWidth();
			int height = element.getSize().getHeight();
			Rectangle rect = new Rectangle(width, height);
			BufferedImage img = ImageIO.read(scrFile);
			BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
			ImageIO.write(dest, "png", scrFile);
			Thread.sleep(1000);
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从本地文件中读取验证码的值.
	 * 
	 * @param filePath
	 *            Tesseract生成的验证码本地文本文件
	 * @return 验证码的值
	 * @throws IOException
	 */
	private static String readVerificationCodeFromFile(String filePath) throws IOException {

		InputStream in = new BufferedInputStream(new FileInputStream(new File(filePath)));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		StringBuffer sb = new StringBuffer();
		String content;
		while ((content = reader.readLine()) != null) {
			sb.append(content);
		}
		reader.close();
		return sb.toString();
	}

}
