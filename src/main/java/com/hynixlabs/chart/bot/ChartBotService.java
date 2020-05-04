package com.hynixlabs.chart.bot;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
public class ChartBotService {
    private WebDriver driver;

    private String driverPath = "src/main/java/com/hynixlabs/chart/chromedriver";

    public ChartBotService() {
        System.setProperty("webdriver.chrome.driver", driverPath);
    }

    // 드라이버 생성
    public WebDriver webDriver() throws Exception {
        ChromeOptions options = new ChromeOptions()
                .addArguments("window-size=1920x1080")
                .addArguments("disable-gpu")
                .addArguments("--no-sandbox")
                .addArguments("--headless")
                .addArguments("--disable-dev-shm-usage")
                .addArguments("lang=ko_KR")
                .addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        ChromeDriver driver = new ChromeDriver(options);
        ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5]}})");
        ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})");
        ((JavascriptExecutor) driver).executeScript("const getParameter = WebGLRenderingContext.getParameter;WebGLRenderingContext.prototype.getParameter = function(parameter) {if (parameter === 37445) {return 'NVIDIA Corporation'} if (parameter === 37446) {return 'NVIDIA GeForce GTX 980 Ti OpenGL Engine';}return getParameter(parameter);};");
        //        driver.executeScript("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5];},});", "");
        Thread.sleep(3 * 1000);
        driver.setLogLevel(Level.WARNING);
        return driver;
    }

}
