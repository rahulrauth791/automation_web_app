package utils;

import java.time.Duration;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.safari.SafariDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BaseClass {
    
    // Use ThreadLocal to support parallel test execution
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    // Backwards-compatible getter (update callers to use getDriver())
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void initializeDriver() {
        // load config.properties from classpath or from path provided via -Dconfig.file
        Properties config = new Properties();
        String configPath = System.getProperty("config.file");
        try (InputStream is = (configPath != null)
                ? new FileInputStream(configPath)
                : BaseClass.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                config.load(is);
            } else {
                System.out.println("config.properties not found on classpath and no -Dconfig.file provided. Using defaults.");
            }
        } catch (IOException e) {
            System.out.println("Failed to load config: " + e.getMessage());
        }

        // Decide browser: system property 'browser' overrides config property 'browser'
        String browserProp = System.getProperty("browser");
        String browser = (browserProp != null && !browserProp.isBlank()) ? browserProp : config.getProperty("browser", "chrome");
        boolean headless = false;
        // support values like "chrome-headless" or "firefox-headless"
        if (browser.toLowerCase().endsWith("-headless")) {
            headless = true;
            browser = browser.substring(0, browser.lastIndexOf("-headless"));
        }
        browser = browser.toLowerCase();

        WebDriver wd;
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");
                // add other common options if needed
                wd = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("-headless");
                wd = new FirefoxDriver(firefoxOptions);
                break;
            case "safari":
                // SafariDriver does not support headless mode
                wd = new SafariDriver();
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("headless");
                wd = new EdgeDriver(edgeOptions);
                break;
            default:
                System.out.println("Unsupported browser '" + browser + "', falling back to Chrome.");
                wd = new ChromeDriver(new ChromeOptions());
        }

        // store in ThreadLocal for this thread
        driverThread.set(wd);

        // common setup (use the thread-local driver)
        getDriver().manage().window().maximize();
        // read implicit wait from config if present, fallback to 10
        int implicitWait = 10;
        try {
            String iw = config.getProperty("implicit.wait");
            if (iw != null && !iw.isBlank()) implicitWait = Integer.parseInt(iw);
        } catch (Exception e) { /* ignore and use default */ }
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        String baseUrl = config.getProperty("base.url", "https://stg-dad-website.indifi.com/");
        getDriver().get(baseUrl);
    }
    
    public static void quitDriver() {
        WebDriver wd = getDriver();
        if (wd != null) {
            try {
                wd.quit();
            } catch (Exception e) {
                System.out.println("Error quitting driver: " + e.getMessage());
            } finally {
                // remove ThreadLocal reference to avoid leaks
                driverThread.remove();
            }
        }
    }
}
