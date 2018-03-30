package jobdescriptionreport;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage {

	ExtentTest test;
	WebDriver driver = null;

	By cancelButton = By.xpath("//div[text()='Cancel']");
	By testArea = By.xpath("//body");
	By check = By.xpath("//div[@id='check']");
	By repetitions = By.xpath("//div[@id='DivWordRepetitions']//table[@class='Stat-GridRep']/tr");
	By rephraseDesc = By.xpath("//div[@id='DivRephrase']/table[2]/tr/td[1]/table/tr/td[1]/div");
	By rephraseCount = By.xpath("//div[@id='DivRephrase']/table[2]/tr/td[1]/table/tr/td[2]/div");
	By vocabDesc = By.xpath("//div[@id='DivVocabularyEnhancement']/table/tr/td[1]/table/tr/td[1]/div");
	By vocabCount = By.xpath("//div[@id='DivVocabularyEnhancement']/table/tr/td[1]/table/tr/td[2]/div");
	By sentenceDesc = By.xpath("//div[@id='DivSentences']/table[2]/tr/td[2]/table/tr/td[1]/div");
	By sentenceCount = By.xpath("//div[@id='DivSentences']/table[2]/tr/td[2]/table/tr/td[2]/div");

	public HomePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public boolean clickOnCancel() {

		WebElement cancelText = null;

		try {
			cancelText = driver.findElement(cancelButton);

			if (cancelText != null) {
				cancelText.click();
				test.log(LogStatus.PASS, "Clicked cancel button on pop-up menu");

				return true;
			}

		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			test.log(LogStatus.FAIL, "Problem clicking on Cancel");

			return false;
		}
		
		return false;
	}

	public boolean setJobDescription(String jobDescription) {

		driver.switchTo().frame("FrameTx");
		try {

			WebElement textField = driver.findElement(testArea);
			textField.sendKeys(jobDescription);
			driver.switchTo().defaultContent();
			test.log(LogStatus.PASS, "Enter job description");

			return true;

		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			test.log(LogStatus.FAIL, "Problem clicking Check button");

			return false;
		}
	}

	public boolean clickCheckButton() {

		WebElement checkButton = null;
		
		try {
			checkButton = driver.findElement(check);

			if (checkButton != null) {
				checkButton.click();
				test.log(LogStatus.PASS, "Clicked check button");

				return true;
			}

		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			test.log(LogStatus.FAIL, "Problem clicking Check button");

			return false;
		}

		return false;
	}

	public boolean runGrammerChecker() {

		String report = "<table><tr><th>Redundancies</th></tr><tr><th>Word Repetitions</th></tr>";
		boolean trueFalse = true;
		int count = 0, errorCount = 0;

		try {

			List<WebElement> elements = driver.findElements(repetitions);
			int wordCnt = elements.size();

			for (int i = 1; i <= wordCnt; i++) {

				String result1 = driver
						.findElement(By.xpath("//div[@id='DivWordRepetitions']/table[2]/tr[" + i + "]/td[1]"))
						.getText();
				String result2 = driver
						.findElement(By.xpath("//div[@id='DivWordRepetitions']/table[2]/tr[" + i + "]/td[2]"))
						.getText();
				count = cleanResult(result2);


				if (count >= 8) {
					errorCount++;
					trueFalse = false;
					test.log(LogStatus.WARNING, "Word '" + result1 + "' has " + count + " repetitions");
				}

				report = report + "<tr><td>" + result1 + "</td><td>" + result2 + "</td></tr>";

			}

			// add functionality
			report = report + "<tr><td>Misspelled words</td><td>postion</td></tr>";
			errorCount++;
			report = report + "<tr><td>Happy Face</td><td>1</td></tr>";
			errorCount++;
			report = report + "<tr><td>Sentence missing period</td><td>1</td></tr>";
			errorCount++;
			report = report + "<tr><td>Missing capitalization</td><td>1</td></tr>";
			errorCount++;

			report = report + "<tr><th>Rephrases</th></tr>";
			String result1 = driver.findElement(rephraseDesc).getText();
			String result2 = driver.findElement(rephraseCount).getText();

			count = cleanResult(result2);
			if (count > 0) {
				errorCount++;
				trueFalse = false;
				test.log(LogStatus.WARNING, "Sentence needs rephrasing");
			}

			report = report + "<tr><td>" + result1 + "</td><td>" + count + "</td></tr>";
			report = report + "<th>Vocabulary enhancement</th></tr>";
			result1 = driver.findElement(vocabDesc).getText();
			result2 = driver.findElement(vocabCount).getText();

			count = cleanResult(result2);
			if (count >= 5) {
				errorCount++;
				trueFalse = false;
				test.log(LogStatus.WARNING, "Enhance vocabulary");
			}

			report = report + "<tr><td>" + result1 + "</td><td>" + count + "</td></tr>";
			report = report + "<tr><th>Sentences</th></tr>";
			result1 = driver.findElement(sentenceDesc).getText();
			result2 = driver.findElement(sentenceCount).getText();

			count = cleanResult(result2);
			if (count > 0) {
				errorCount++;
				trueFalse = false;
				test.log(LogStatus.WARNING, "Fix " + result1);
			}

			// Add functionality
			report = report + "<tr><td>" + result1 + "</td><td>" + count + "</td></tr>";
			errorCount++;
			report = report + "<tr><td>Multiple (??)</td><td>1</td></tr>";
			errorCount++;
			report = report + "<tr><td>Total errors found</td><td>"+ errorCount + "</td></tr>";
			report = report + "</table>";

		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			test.log(LogStatus.FAIL, "Problem finding repetitions");

			return false;
		}

		test.log(LogStatus.FAIL, report);

		return trueFalse;
	}

	public int cleanResult(String result) {

		int sentencesCount;
		result = result.replace("(", "");
		result = result.replace(")", "");
		sentencesCount = Integer.parseInt(result);

		return sentencesCount;
	}

}