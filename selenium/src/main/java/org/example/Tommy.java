package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * Scrape the tommyhilfiger website using scrolling
 */
public class Tommy extends WebScraper {
    private String pageUrl;
    private ChromeOptions options;
    private WebDriver driver;
    private JavascriptExecutor js;
    public Tommy(String pageUrl) {

    }
    @Override
    public void run() {
        String tShirtUrl = "https://uk.tommy.com/mens-outlet#productgroup=s.productgroup_t2dshirts";
        String sweatShirtUrl = "https://uk.tommy.com/mens-outlet#productgroup=s.productgroup_sweatshirtshoodies";
        String shirtUrl = "https://uk.tommy.com/mens-outlet#productgroup=s.productgroup_shirts";
        String jacketsUrl = "https://uk.tommy.com/mens-outlet#productgroup=s.productgroup_coatsjackets";
//        String joggersUrl = "https://uk.tommy.com/mens-tracksuits#style=s.style_joggers";

//        scrape(tShirtUrl,"tShirt");
//        scrape(sweatShirtUrl,"sweatshirt");
//        scrape(shirtUrl,"shirt");
        scrape(jacketsUrl,"jacket");
//        scrape(joggersUrl,"joggers");

    }
    public void runChrome(){
        this.options = new ChromeOptions();

        this.options.setHeadless(false);
        this.options.addArguments("--remote-allow-origins=*");
        this.options.addArguments("--window-size=1579,970");
        this.options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                + "AppleWebKit/537.36 (KHTML, like Gecko)"
                + "Chrome/87.0.4280.141 Safari/537.36");

        //Create instance of web driver - this must be on the path.
        this.driver = new ChromeDriver(options);
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * Runs method in thread
     */
    public void scrape(String pageUrl,String productCategory) {
        runChrome();

        driver.get(pageUrl);

        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //executing javascript code
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        WebElement buttonCookiesTommy = driver.findElement(By.cssSelector(".Button.Button__primary.cookie-notice__agree-button_lAIob"));
        buttonCookiesTommy.click();

//        int maxPages = Integer.parseInt(driver.findElement(By.cssSelector(".pagination__page_1HBYg.pagination__page--total_2vGbA")).getText());
//        WebElement nextButton = driver.findElement(/**/By.cssSelector(".pagination__button_2iytJ.pagination__button--direction_1cqzj"));

        for (int i = 1; i <= 1; i++) {
            System.out.println('i');
            int scrollHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
            int currentScrollValue = 600;
            js.executeScript("window.scrollTo(0, 0)", "");
            //if modal pop - ups



        /**
         *Simulate scrolling
         */
        while (scrollHeight > currentScrollValue) {
            System.out.println("curentscroll value " + currentScrollValue);
            js.executeScript("window.scrollBy(0, 600)", "");
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            currentScrollValue = currentScrollValue + 600;
        }
        /**
         *Getting data from cards
         */
        List<WebElement> cardListTommy = driver.findElements(By.className("product-tile_3HY5J"));
        for (WebElement card : cardListTommy) {
            String productTitle = card.findElement(By.className("product-tile__name_Plmfz")).getText();
            String productPrice = card.findElement(By.cssSelector(".price-display__selling_Ub68r.price-display__was__selling_QczgV")).getText();
            String productImage = card.findElement(By.cssSelector(".product-image__image_3dU43")).getAttribute("srcset");
            //get second link from image src
            String productImageTrimmed = productImage.substring(productImage.lastIndexOf(',') + 1).trim();
            //delete unused characters
            productImageTrimmed = "https://"+productImageTrimmed.substring(2,productImageTrimmed.length()-2);
            String productUrl = card.findElement(By.cssSelector(".product-tile__anchor_1ujO3.product-tile__anchor--mobile-only_30_Mn")).getAttribute("href");
            System.out.println("product  title"+productTitle);
            scrapedProductsList.add(new Product(productTitle, productPrice, productImageTrimmed, productUrl,productCategory));
        }
            if (!driver.findElements(By.cssSelector(".Button.Button__close.Button__close_3zi6m")).isEmpty()) {
                WebElement globalStoreButton = driver.findElement(By.cssSelector(".Button.Button__close.Button__close_3zi6m"));
                globalStoreButton.click();
            }
//            currentScrollValue = currentScrollValue - 800;
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            js.executeScript("window.scrollTo(0, -700)", "");

//

            if (!driver.findElements(By.cssSelector(".pagination__chevron_2DqPp.pagination__chevron--right_1ouCm")).isEmpty()) {
                WebElement nextButton = driver.findElement(By.cssSelector(".pagination__chevron_2DqPp.pagination__chevron--right_1ouCm"));
                nextButton.click();
            }
//            nextButton.click();
        }

        //waiting google chrome to finish execution then close the browser
        try {
            Thread.sleep(8000);
            System.out.println("tommy");
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
