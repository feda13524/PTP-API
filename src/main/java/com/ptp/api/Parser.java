package com.ptp.api;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;

public class Parser {
    private static WebDriver driver;
    private static boolean initialized = false;
    
    public static void initialize() {
        if (initialized) return;
        
        try {
            System.out.println("🔄 Инициализация драйвера...");
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                "--headless",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080"
            );
            
            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
            initialized = true;

            System.out.println("✅ Драйвер инициализирован успешно");
        } catch (Exception e) {
            System.err.println("⚠️ Ошибка инициализации драйвера: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static <T> T execute(String url, Function<WebDriver, T> action) {
        if (!initialized) { initialize(); }
        
        try {

            if (url == null || url.trim().isEmpty()) {
                System.out.println("❌ URL не указан");
                return null;
            }
            
            System.out.println("🌐 Парсинг: " + url);
            
            try {
                driver.get(url);
                Thread.sleep(2000);
            } catch (TimeoutException e) {
                System.out.println("⚠️ Таймаут загрузки страницы, продолжаем...");
            }

            return action.apply(driver);

        } catch (Exception e) {
            System.err.println("💥 Ошибка: " + e.getMessage());
            return null;
        }
    }

    public static String getRawText(String url){
        return execute(url, driver -> {
            String text = driver.findElement(By.tagName("body"))
                .getText().replaceAll("\\s+", " ").trim();
            
            System.out.println("✅ Текст получен, длина: " + text.length() + " символов");

            return text;
        });
    }

    public static Double findPrice(String url){
        return execute(url, driver -> {
            String[] selectors = {
                // Общие селекторы
                "span.price", "div.price", "span.product-price", "div.product-price",
                "span.current-price", "div.current-price", ".price-current", ".final-price",
                "span[class*='price']", "div[class*='price']", "[class*='price']",
                
                // Data-атрибуты
                "[data-price]", "[itemprop='price']", "[data-product-price]",
                "[data-price-value]", "[data-final-price]",
                
                // Специфичные для популярных сайтов
                ".price-block__final-price",  // Wildberries
                ".product-buy__price",        // DNS
                ".product-card-price__current", // Ozon
                ".cost-price",                // М.Видео
                ".price__value",              // Яндекс.Маркет
                ".product__price",            // Ситилинк
                ".product-view-price",        // Ozon
                ".sale-price",                // Скидочная цена
                ".special-price"              // Специальная цена
            };
            
            for (String selector : selectors) {
                try {
                    List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                    for (WebElement element : elements) {
                        Double price = 0.;
                        String text = element.getText().replaceAll("[^\\d.,]", "");
                        if (text.split("[.,]").length > 2) text = text.replace(".", "");
                        if (text.split(",").length > 2) text = text.replace(",", "");
                        text = text.replace(",", ".");
                        
                        if (!text.replace(".", "").isEmpty()) price = Double.parseDouble(text);

                        if (price != null && price > 0) {
                            System.out.println("💰 Цена найдена: " + price);
                            return price;
                        }
                    }
                } catch (Exception ignored) {}
            }
            
            return null;
        });
    }
    
    public static void shutdown() {
        if (driver != null) {
            try {
                driver.quit();
                initialized = false;
            } catch (Exception e) {
                System.err.println("⚠️ Ошибка при закрытии драйвера: " + e.getMessage());
            }
        }
    }
}
