package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * Scrape the gant website
 */
public class Gant extends WebScraper {
    private ChromeOptions options;
    private WebDriver driver;
    private JavascriptExecutor js;

    public Gant(String pageUrl) {
    }
    @Override
    public void run() {
        String tShirtUrl = "https://www.gant.co.uk/mens/clothing/t-shirts";
        String sweatShirtUrl = "https://www.gant.co.uk/mens/clothing/sweatshirts-hoodies";
        String shirtUrl = "https://www.gant.co.uk/mens/clothing/shirts";
        String jacketsUrl = "https://www.gant.co.uk/mens/clothing/outerwear";
        String joggersUrl = "https://www.gant.co.uk/mens/clothing/trousers";


//        scrape(tShirtUrl,"tShirt");
//        scrape(sweatShirtUrl,"sweatshirt");
//        scrape(shirtUrl,"shirt");
//        scrape(jacketsUrl,"jacket");
        scrape(joggersUrl,"joggers");
    }
    /**
     * Runs method in thread
     */
    public void scrape(String pageUrl,String productCategory) {
        //Creating google chrome instance,setting browser size and user agent
        this.options = new ChromeOptions();

        this.options.setHeadless(false);
        this.options.addArguments("--remote-allow-origins=*");
        this.options.addArguments("--window-size=1579,970");
        this.options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                + "AppleWebKit/537.36 (KHTML, like Gecko)"
                + "Chrome/87.0.4280.141 Safari/537.36");

        //Create instance of web driver - this must be on the path.
        this.driver = new ChromeDriver(options);
        this.driver.get(pageUrl);
//        //Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        WebElement buttonGant = driver.findElement(By.id("onetrust-accept-btn-handler"));
        buttonGant.click();
        this.js = (JavascriptExecutor) driver;
        int currentScrollValue = 600;
        int scrollHeight = ((Number) js.executeScript("return document.getElementsByClassName(\"product-grid__container\")[0].scrollHeight")).intValue();

        for (int i = 0; scrollHeight > currentScrollValue ; i++) {
            while (scrollHeight > currentScrollValue) {
                js.executeScript("window.scrollBy(0, 600)", "");
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentScrollValue = currentScrollValue + 600;
            }

            js.executeScript("window.scrollBy(0, -900)", "");
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
                try {
                    WebElement loadMoreButton = driver.findElement(By.cssSelector(".search-results__next-button"));
                    loadMoreButton.click();
                }
                catch(Exception e){
                    System.out.println("Load more button not found");
                    break;
                }

            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            scrollHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
        }
//}

        List<WebElement> cardListGant = driver.findElements(By.cssSelector(".c-product-tile.in--view"));
        for (WebElement card : cardListGant) {
            String productTitle = card.findElement(By.cssSelector(".product-tile__title")).getText();
            String productPrice = card.findElement(By.cssSelector(".u-sr-only")).getText();
            String productImage = card.findElement(By.cssSelector(".image__default.image--object-fit-cover")).getAttribute("src");
            String productUrl = card.findElement(By.xpath("//*[@id=\"content\"]/section[1]/div[2]/div[1]/div[1]/div/div[2]/div/div[2]/a")).getAttribute("href");
            System.out.println(productTitle);
            System.out.println(productPrice);
            System.out.println(productImage);
            System.out.println(productUrl);
            scrapedProductsList.add(new Product(productTitle, productPrice, productImage, productUrl,productCategory));
        }
        try {
            Thread.sleep(8000);
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

