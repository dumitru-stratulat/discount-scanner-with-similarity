package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * Scrape the northface website
 */
public class NorthFace extends WebScraper {
    private String pageUrl;
    private ChromeOptions options;
    private WebDriver driver;
    private JavascriptExecutor js;

    public NorthFace(String pageUrl) {

    }
    @Override
    public void run() {
        String tShirtUrl = "https://www.thenorthface.co.uk/shop/en-gb/tnf-gb/men-men-men-highlights-outlet#esp_cf=filter_style&esp_filter_filter_style=Tops%20and%20t-shirts";
        String sweatShirtUrl = "https://www.thenorthface.co.uk/shop/en-gb/tnf-gb/men-men-men-highlights-outlet#esp_cf=filter_style&esp_filter_filter_style=Hoodies%20and%20sweatshirts";
        String shirtUrl = "https://www.thenorthface.co.uk/shop/en-gb/tnf-gb/men-men-men-highlights-outlet#esp_cf=filter_style&esp_filter_filter_style=Shirts";
        String jacketsUrl = "https://www.thenorthface.co.uk/shop/en-gb/tnf-gb/men-men-men-highlights-outlet#esp_cf=filter_style&esp_filter_filter_style=Puffer%20Jackets";
        String joggersUrl = "https://www.thenorthface.co.uk/shop/en-gb/tnf-gb/men-men-men-highlights-outlet#esp_cf=filter_style&esp_filter_filter_style=Joggers";


//        scrape(tShirtUrl,"tShirt");
//        scrape(sweatShirtUrl,"sweatshirt");
//        scrape(shirtUrl,"shirt");
//        scrape(jacketsUrl,"jacket");
        scrape(joggersUrl,"joggers");
    }
    public void scrape(String pageUrl,String productCategory) {
        //Creating google chrome instance,setting browser size and user agentt
        this.options = new ChromeOptions();

        this.options.setHeadless(false);
        this.options.addArguments("--remote-allow-origins=*");
        this.options.addArguments("--window-size=1579,970");
        this.options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                + "AppleWebKit/537.36 (KHTML, like Gecko)"
                + "Chrome/87.0.4280.141 Safari/537.36");

        //Create instance of web driver - this must be on the path
        this.driver = new ChromeDriver(options);
        this.js = (JavascriptExecutor) driver;
        driver.get(pageUrl);
        //Wait for page to load
        int scrollHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
        int currentScrollValue = 600;

        while (scrollHeight > currentScrollValue) {
            System.out.println("curentscroll value " + currentScrollValue);
            js.executeScript("window.scrollBy(0, 600)", "");
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            currentScrollValue = currentScrollValue + 600;
            scrollHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
        }

        try {
            //accept cookie
            WebElement buttonNorthFace = driver.findElement(By.id("onetrust-close-btn-container"));
            buttonNorthFace.click();

            List<WebElement> cardListHugoBoss = driver.findElements(By.cssSelector(".product-block.product-block-js.lanes"));
            for (WebElement card : cardListHugoBoss) {
                String productTitle = card.findElement(By.cssSelector(".product-block-name-wrapper")).getText();
                String productPrice = card.findElement(By.cssSelector(".product-price-amount-js")).getText();
                String productImage ="https:"+card.findElement(By.cssSelector(".product-block-views-selected-view-main-image.product-block-views-selected-view-image.product-views-selected-view-main-image.main-view-js")).getAttribute("srcset");
                String productUrl = card.findElement(By.cssSelector(".product-block-name-link")).getAttribute("href");
                System.out.println(productTitle);
                System.out.println(productPrice);
                System.out.println(productImage);
                System.out.println(productUrl);
                scrapedProductsList.add(new Product(productTitle, productPrice, productImage, productUrl,productCategory));
            }
        } catch (Exception err) {
            System.out.println(err);
            driver.quit();
        }
        try {
            Thread.sleep(8000);
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


