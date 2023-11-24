import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FilterSelectionTest {

	WebDriver driver;

	@Test
	public void initialization() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.t-mobile.com/tablets");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		
		
		filterSelection("Brands","Apple","Samsung","TCL");
		filterSelection("Brands","TCL");
		
		filterSelection("Deals","New","Special offer");
		filterSelection("Operating System", "iPadOS","Android");
		filterSelection("Brands", "All");
		
//		driver.quit();
	}

	public void filterSelection(String fileterName, String... parameters) throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(3000);
		WebElement expandFilter=driver.findElement(By.xpath(".//*[@data-testid='desktop-filter-group-name' and text()=' " + fileterName
				+ " ']//parent::mat-panel-title//tmo-icon"));
		expandFilter.click();
		
		Thread.sleep(2000);

		// getting the number of checkboxes
		List<WebElement> noOfCheckBoxes = driver.findElements(By.xpath(
				".//*[@aria-label='" + fileterName + "']//mat-checkbox//*[@class='mat-checkbox-inner-container']"));
		Thread.sleep(3000);
		System.out.println(noOfCheckBoxes.size());

		// condition for checking all check boxes
		if (parameters[0].equalsIgnoreCase("all")) {
			Thread.sleep(2000);
			for (int y = 1; y <= noOfCheckBoxes.size(); y++) {
				Thread.sleep(2000);
				WebElement ele = driver.findElement(By.xpath("(.//*[@aria-label='" + fileterName
						+ "']//mat-checkbox//*[@class='mat-checkbox-inner-container']/input)[" + y + "]"));

				if (!(ele.isSelected())) {
					System.out.println(ele.isSelected()+" --------- ");
					js.executeScript("arguments[0].click()", ele);
					System.out.println("checkbox checked");
					Thread.sleep(2000);
					expandFilter.click();
				}
				else
				{
					System.out.println("element is already checked");
				}
			}
		} 
		//  for checking specify check boxes
		else 
		{
			for (int i = 0; i < parameters.length; i++) {
				Thread.sleep(2000);
				System.out.println("inside for");
				WebElement chk = driver.findElement(By.xpath(".//*[text()=' " + parameters[i]
						+ " ']//parent::span//preceding-sibling::span[@class='mat-checkbox-inner-container']/input"));
				if (!(chk.isSelected())) {
					js.executeScript("arguments[0].click()", chk);
					System.out.println("checkbox checked");
					Thread.sleep(2000);
					expandFilter.click();
				}
				else
				{
					System.out.println("checkbox is already checked");
				}
			}
		}

	}
}
