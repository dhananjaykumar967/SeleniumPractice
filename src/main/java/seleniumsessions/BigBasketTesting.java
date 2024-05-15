package seleniumsessions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class BigBasketTesting {
    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("https://www.bigbasket.com/");

        By shopByCategory = By.id("headlessui-menu-button-:R5bab6:");
        By beverages = By.xpath("(//a[text()='Beverages'])[2]");
        By fruitJuicesDrinks = By.xpath("//a[text()='Fruit Juices & Drinks']");
        By juices = By.xpath("//a[text()='Juices']");

        Thread.sleep(3000);

        doClickElement(shopByCategory);
        doMoveToElement(beverages);
        Thread.sleep(3000);
        doMoveToElement(fruitJuicesDrinks);
        doMoveToElementAndClick(juices);
    }

    public static WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public static void doClickElement(By locator) {
        getElement(locator).click();
    }

    public static void doMoveToElement(By locator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(locator)).pause(500).perform();
    }

    public static void doMoveToElementAndClick(By locator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(locator)).click().perform();
    }
}
