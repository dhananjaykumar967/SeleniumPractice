package seleniumsessions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementUtil {

	private WebDriver driver;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	private void nullCheck(String value) {
		if (value == null) {
			throw new ElementException("VALUE IS NULL" + value);
		}
	}

	public void doSendKeys(By locator, String value) {
		nullCheck(value);
		getElement(locator).sendKeys(value);
	}

	public void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).sendKeys(value);
	}

	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			return element;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page..." + locator);
			e.printStackTrace();
			return null;
		}
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	public boolean doIsDisplayed(By locator) {
		try {
			boolean flag = getElement(locator).isDisplayed();
			System.out.println("element is displayed: " + locator);
			return flag;
		} catch (NoSuchElementException e) {
			System.out.println("element with locator : " + locator + " is not displayed");
			return false;
		}

	}
	
	public boolean isElementDisplayed(By locator) {
		int elementCount = getElements(locator).size();
		if(elementCount == 1) {
			System.out.println("single element is displayed: " + locator);
			return true;
		}
		else {
			System.out.println("multiple or zero elements are displayed: " + locator);
			return false;
		}
	}
	
	public boolean isElementDisplayed(By locator, int expectedElementCount) {
		int elementCount = getElements(locator).size();
		if(elementCount == expectedElementCount) {
			System.out.println("element is displayed: " + locator + " with the occurrence of " + elementCount);
			return true;
		}
		else {
			System.out.println("multiple or zero elements are displayed: " + locator + " with the occurrence of " + elementCount);
			return false;
		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();// pc=0, size=0

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}

		return eleTextList;
	}
	
	public List<String> getElementAttributeList(By locator, String attrName) {
		List<WebElement> imagesList = getElements(locator);
		List<String> attrList = new ArrayList<String>();
		for (WebElement e : imagesList) {
			String attrVal = e.getAttribute(attrName);
			if (attrVal != null && attrVal.length()!=0) {
				attrList.add(attrVal);
				//System.out.println(attrVal);
			}
		}
		return attrList;
	}
	
	
	//********************** Select drop down utils **************//
	
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);		
	}
	
	public void doSelectByVisbleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}
	
	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
	
	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(driver.findElement(locator));
		return select.getOptions().size();

	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(driver.findElement(locator));

		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList = new ArrayList<String>();
		
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		
		return optionsTextList;
	}
	
	
	public void selectValueFromDropDown(By locator, String optionText) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(optionText.trim())) {
				e.click();
				break;
			}
		}

	}
	
	
	public void selectValueFromDropDownWithoutSelectClass(By locator, String optionText) {
		List<WebElement> optionsList = getElements(locator);
		for(WebElement e : optionsList) {
			String text = e.getText();
				if(text.equals(optionText)) {
					e.click();
					break;
				}
		}

	}
	
	
	public void doSearch(By searchField, String searchKey, By suggestions, String value) throws InterruptedException {
		doSendKeys(searchField, searchKey);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}
	
	
	
	//*****************Actions utils********************//
	
	public void handleParentSubMenu(By parentLocator, By childLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).perform();
		Thread.sleep(2000);
		doClick(childLocator);
	}
	
	public void doDragAndDrop(By sourcelocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourcelocator), getElement(targetLocator)).perform();
	}
	
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}
	
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	/**
	 * This method is used to enter the value in the text field with a pause.
	 * @param locator
	 * @param value
	 * @param pauseTime
	 */
	public void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}
	
	/**
	 * This method is used to enter the value in the text field with a pause of 500 ms (by default).
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeysWithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
		}
	}

}
