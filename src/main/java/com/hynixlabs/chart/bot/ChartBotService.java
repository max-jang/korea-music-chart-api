package com.hynixlabs.chart.bot;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.logging.Level;

@Service
public class ChartBotService {
    private WebDriver driver;

    //절대경로로 변경예정
    private String driverPath = "src/main/java/com/hynixlabs/chart/chromedriver";
    private String url = "https://www.dcinside.com/";
    private String shineeGallery = "https://gall.dcinside.com/mgallery/board/write/?id=shinee_new";
    private String taeminGallery = "https://gall.dcinside.com/mgallery/board/write/?id=taemin";

    @Value("${id}")
    private String id;

    @Value("${password}")
    private String password;

    public ChartBotService(){
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

    public void writePost() throws Exception {
        // 드라이버 로딩
        driver = webDriver();

        // 디시인사이드 메인 페이지 접속
        driver.get(url);
        Thread.sleep(3 * 1000);

        // 로그인
        driver.findElement(By.name("user_id")).sendKeys(id);
        driver.findElement(By.id("pw")).sendKeys(password);
        driver.findElement(By.id("login_ok")).click();

        // 탬갤 글쓰기로 이동
        driver.get(taeminGallery);
        Thread.sleep(3 * 1000);

        // 글작성 구간
        driver.findElement(By.name(("subject"))).sendKeys("차트봇 테스트(애치목장)");
        driver.findElement(By.id("tx_switchertoggle")).click();
        Thread.sleep(3 * 1000);

        driver.switchTo().frame("tx_canvas_wysiwyg");
        driver.findElement(By.tagName("body"))
                .sendKeys("<img src='https://pbs.twimg.com/media/ENPS3nKUYAA_aCE?format=jpg'><br><br><br><a href='https://twitter.com/farm718/' target='_blank' class='tx-link'>애치목장</a>");

        driver.switchTo().defaultContent();
        Thread.sleep(3 * 1000);
        driver.findElement(By.cssSelector("button.btn_blue.btn_svc.write")).click();
        Thread.sleep(5 * 1000);
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere
        FileCopyUtils.copy(srcFile, new File("testscreenshot.png"));
        driver.close();  // Driver 종료
    }

    public void test() throws Exception {
        driver = webDriver();
        driver.get("https://intoli.com/blog/making-chrome-headless-undetectable/chrome-headless-test.html");
        System.out.println(driver.findElement(By.cssSelector("#user-agent")).getText());
        System.out.println("Plugin: " + driver.findElement(By.cssSelector("#plugins-length")).getText());
    }



}
