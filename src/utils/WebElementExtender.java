package utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
 
import javax.imageio.ImageIO;
 
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
 
/**
 * This class provides various additional helper methods on elements
 *
 * @author upgundecha
 *
 */
 
public class WebElementExtender {
 
    /**
     * Gets a picture of specific element displayed on the page
     * @param element The element
     * @return File
     * @throws Exception
     */
    public static File captureElementPicture(WebElement element)
            throws Exception {
 
        // get the WrapsDriver of the WebElement
        WrapsDriver wrapsDriver = (WrapsDriver) element;
 
        // get the entire screenshot from the driver of passed WebElement
        File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver())
                .getScreenshotAs(OutputType.FILE);
 
        // create an instance of buffered image from captured screenshot
        BufferedImage img = ImageIO.read(screen);
 
        // get the width and height of the WebElement using getSize()
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
 
        // create a rectangle using width and height
        Rectangle rect = new Rectangle(width, height);
 
        // get the location of WebElement in a Point.
        // this will provide X & Y co-ordinates of the WebElement
        Point p = element.getLocation();
 
        // create image  for element using its location and size.
        // this will give image data specific to the WebElement
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width,
                rect.height);
 
        // write back the image data for element in File object
        ImageIO.write(dest, "png", screen);
 
        // return the File object containing image data
        return screen;
    }
}
