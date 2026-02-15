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
        boolean configLoaded = false;
        try (InputStream is = (configPath != null)
                ? new FileInputStream(configPath)
                : BaseClass.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                config.load(is);
                configLoaded = true;
            } else {
                System.out.println("config.properties not found on classpath and no -Dconfig.file provided. Using defaults.");
            }
        } catch (IOException e) {
            System.out.println("Failed to load config: " + e.getMessage());
        }

        // log where config came from and some key properties
        if (configPath != null && configLoaded) {
            System.out.println("Loaded config from path: " + configPath);
        } else if (configLoaded) {
            System.out.println("Loaded config from classpath: config.properties");
        } else {
            System.out.println("No config file loaded; using builtin defaults.");
        }
        System.out.println("Configured browser (raw): " + System.getProperty("browser", config.getProperty("browser", "chrome")));
        System.out.println("Configured implicit.wait: " + config.getProperty("implicit.wait", "10"));
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
                wd = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("-headless");
                wd = new FirefoxDriver(firefoxOptions);
                break;
            case "safari":
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
        int implicitWait = 10;
        try {
            String iw = config.getProperty("implicit.wait");
            if (iw != null && !iw.isBlank()) implicitWait = Integer.parseInt(iw);
        } catch (Exception e) { /* ignore and use default */ }
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // determine environment and base URL
        // priority: -Denv, config property "env", default "staging"
        String env = System.getProperty("env", config.getProperty("env", "staging")).trim();
        String baseUrl = null;
        try {
            baseUrl = config.getProperty(env + ".url");
        } catch (Exception e) {
            baseUrl = null;
        }
        if (baseUrl == null || baseUrl.isBlank()) {
            baseUrl = config.getProperty("base.url", "https://stg-dad-website.indifi.com/");
            System.out.println("No URL found for env '" + env + "'. Falling back to base.url: " + baseUrl);
        } else {
            System.out.println("Using env '" + env + "' -> " + baseUrl);
        }

        // additional logging: final selection summary
        System.out.println("Final test configuration:");
        System.out.println("  env: " + env);
        System.out.println("  baseUrl: " + baseUrl);
        System.out.println("  browser: " + browser + (headless ? " (headless)" : ""));
        System.out.println("  implicit.wait (s): " + implicitWait);

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
