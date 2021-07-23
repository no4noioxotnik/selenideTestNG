package Helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import parallel.StepDefs;

import java.util.Base64;

public class TestAllureListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    // Text attachments for Allure
//    @Attachment(value = "Page screenshot", type = "image/png")
//    public byte[] saveScreenshotPNG(WebDriver driver) {
//        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    }
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriverRunner driver) {
        String screenshotAsBase64 = Selenide.screenshot(OutputType.BASE64);
        return Base64.getDecoder().decode(screenshotAsBase64);
    }

    // Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    // HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", StepDefs.getSeleideDriver());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("I am in onFinish method " + iTestContext.getName());
//        StepDefs.getDriver().quit();
        Selenide.closeWebDriver();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
//        StepDefs.getDriver().quit();
        Selenide.closeWebDriver();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
//        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
//        Object testClass = iTestResult.getInstance();
//        WebDriver driver = StepDefs.getDriver();
//        // Allure ScreenShotRobot and SaveTestLog
//        if (driver instanceof WebDriver) {
//            System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
//            saveScreenshotPNG(driver);
//        }
//        // Save a log on allure.
//        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");

//        saveScreenshotPNG(StepDefs.getDriver());
        saveScreenshotPNG(StepDefs.getSeleideDriver());
        saveTextLog(iTestResult.getMethod().getConstructorOrMethod().getName());
//        StepDefs.getDriver().quit();
        Selenide.closeWebDriver();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

}

