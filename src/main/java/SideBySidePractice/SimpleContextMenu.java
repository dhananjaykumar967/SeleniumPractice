package SideBySidePractice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SimpleContextMenu {
    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");

        Thread.sleep(3000);

        By rightClickMeButton = By.cssSelector("span.context-menu-one");
        By rightClickMeContext = By.cssSelector("li.context-menu-icon>span");

        doRightClick(rightClickMeButton);
        doClickOnElementWithGivenText(rightClickMeContext, "Cut");

        String alertText = getAlertText();
        System.out.println(alertText);
        doAcceptAlert();

    }

    public static WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public static List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Do right-click on element with Actions class.
     *
     * @param locator is for right-click on the element.
     * @author www.linkedin.com/in/dhananjay-kumar-b72452117
     */
    public static void doRightClick(By locator) {
        Actions actions = new Actions(driver);
        actions.contextClick(getElement(locator)).perform();
    }

    /**
     *
     *
     * @param element is a WebElement for list of elements
     * @author www.linkedin.com/in/dhananjay-kumar-b72452117
     */
    public static void doClickWithAction(WebElement element) {
        Actions actions = new Actions(driver);
        actions.click(element).perform();
    }

    /**
     * Do click on element with the help of Actions class and a specific text only.
     *
     * @param locator for list of elements
     * @param text    to get specific text value element.
     * @author www.linkedin.com/in/dhananjay-kumar-b72452117
     */
    public static void doClickOnElementWithGivenText(By locator, String text) {
        List<WebElement> listOfContextKeys = getElements(locator);
        for (WebElement ele : listOfContextKeys) {
            String tx = ele.getText();
            if (tx.equals(text)) {
                doClickWithAction(ele);
                break;
            }
        }
    }

    /**
     * To get alert reference
     *
     * @author www.linkedin.com/in/dhananjay-kumar-b72452117
     */
    public static Alert getAlertReference() {
        return driver.switchTo().alert();
    }

    /**
     * Get text value of JS Alert with .getText();
     *
     * @author www.linkedin.com/in/dhananjay-kumar-b72452117
     */
    public static String getAlertText() {
        return getAlertReference().getText();
    }

    /**
     * Click on JS Alert with .accept();
     *
     * @author www.linkedin.com/in/dhananjay-kumar-b72452117
     */
    public static void doAcceptAlert() {
        getAlertReference().accept();
    }

}
