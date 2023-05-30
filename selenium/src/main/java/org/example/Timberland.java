package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * Scrape the timberland website
 */
public class Timberland extends WebScraper {
    private String pageUrl;
    private ChromeOptions options;
    private WebDriver driver;
    private JavascriptExecutor js;

    public Timberland(String pageUrl) {

    }
    public void runChrome(){
        this.options = new ChromeOptions();

        options.setHeadless(false);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1579,970");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                + "AppleWebKit/537.36 (KHTML, like Gecko)"
                + "Chrome/87.0.4280.141 Safari/537.36");

        //Create instance of web driver - this must be on the path.
        this.driver = new ChromeDriver(options);
        this.js = (JavascriptExecutor) driver;
    }
    /**
     * Runs method in thread
     */
    @Override
    public void run() {
        String tShirtUrl = "https://www.timberland.co.uk/shop/en/tbl-uk/outlet-collection-mens-outlet?facet=CATEGORY%253DApparel,CATEGORY_TYPE%253DT%25E2%2580%2593Shirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2#facet=CATEGORY%253DApparel,CATEGORY_TYPE%253DT%25E2%2580%2593Shirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2&beginIndex=0";
        String sweatShirtUrl = "https://www.timberland.co.uk/shop/en/tbl-uk/outlet-collection-mens-outlet?facet=CATEGORY_TYPE%253DSweatshirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2#facet=CATEGORY_TYPE%253DSweatshirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2&beginIndex=0";
        String shirtUrl = "https://www.timberland.co.uk/shop/en/tbl-uk/outlet-collection-mens-outlet?facet=CATEGORY_TYPE%253DShirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2#facet=CATEGORY_TYPE%253DShirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2&beginIndex=0";
        String jacketsUrl = "https://www.timberland.co.uk/shop/en/tbl-uk/outlet-collection-mens-outlet?facet=CATEGORY_TYPE%253DBombers&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2#facet=CATEGORY_TYPE%253DBombers&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2&beginIndex=0";
        String joggersUrl = "https://www.timberland.co.uk/shop/en/tbl-uk/outlet-collection-mens-outlet?facet=CATEGORY_TYPE%253DJogger&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2#facet=CATEGORY_TYPE%253DJogger&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2&beginIndex=0";

//        scrape(tShirtUrl,"tShirt");
//        scrape(sweatShirtUrl,"sweatshirt");
//        scrape(shirtUrl,"shirt");
//        scrape(jacketsUrl,"jacket");
        scrape(joggersUrl,"joggers");
    }

    public void scrape(String pageUrl,String productCategory) {
        runChrome();
        driver.get(pageUrl);
//        //Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        //acceept cookie
        if (driver.findElement(By.id("onetrust-accept-btn-handler")).isDisplayed() == true) {
        WebElement buttonTimberland = driver.findElement(By.id("onetrust-accept-btn-handler"));
        buttonTimberland.click();
        }
        if (driver.findElement(By.cssSelector(".tbl-S23_community-and-newsletter-popover__close")).isDisplayed() == true) {
            WebElement memberPop = driver.findElement(By.cssSelector(".tbl-S23_community-and-newsletter-popover__close"));
            memberPop.click();
        }
//
//        int scrollHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
        int scrollHeight = ((Number) js.executeScript("return document.getElementById(\"product-list\").scrollHeight")).intValue();
        int currentScrollValue = 600;
        js.executeScript("window.scrollBy(0, 600)", "");
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//
        for (int i = 0; scrollHeight > currentScrollValue; i++) {

            System.out.println(i);
            while (scrollHeight > currentScrollValue) {
                System.out.println("curentscroll value " + currentScrollValue);
                System.out.print(" scroll height value " + scrollHeight);
                js.executeScript("window.scrollBy(0, 600)", "");
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentScrollValue = currentScrollValue + 600;

            }
//            if(i==0)
//            if (!driver.findElements(By.cssSelector(".COMPONENT-ESPOT-OVERLAY-CLOSE")).isEmpty()) {
//                WebElement closeSubscribeButton = driver.findElement(By.cssSelector(".COMPONENT-ESPOT-OVERLAY-CLOSE"));
//                closeSubscribeButton.click();
//            }
            js.executeScript("window.scrollBy(0, -700)", "");
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (driver.findElement(By.cssSelector(".load-more-btn-js")).isDisplayed() == true) {
//                WebElement loadMoreButton = driver.findElement(By.cssSelector(".load-more-btn-container"));
                  WebElement loadMoreButton = driver.findElement(By.cssSelector(".load-more-btn-js.load-more-btn.button.expand.tertiary"));
                  loadMoreButton.click();
            } else
                System.out.println("Load more button is not displayed");
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            scrollHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
        }
//
        List<WebElement> cardListTimberland = driver.findElements(By.cssSelector(".product-block.product-block-js"));
        for (WebElement card : cardListTimberland) {
            try {
                if (card.findElement(By.className("product-block-original-price")).isDisplayed()) {
                    String productTitle = card.findElement(By.className("product-block-name-wrapper")).getText();
                    String productPrice = card.findElement(By.className("product-price-amount-js")).getText();
                    String productImage = card.findElement(By.cssSelector(".product-block-views-selected-view-main-image.product-block-views-selected-view-image.product-views-selected-view-main-image.main-view-js")).getAttribute("srcset");
                    String productUrl = card.findElement(By.cssSelector(".product-block-name-link")).getAttribute("href");
                    System.out.println("product title" + productTitle);
                    System.out.println("product price" + productPrice);
                    System.out.println("product image" + productImage);
                    System.out.println("product yrl" + productUrl);
                    System.out.println("-----------");
                    scrapedProductsList.add(new Product(productTitle, productPrice, productImage, productUrl,productCategory));
                }
            } catch (Exception err) {

            }

        }
//        //waiting google chrome to finish execution then close the browser
        try {
            Thread.sleep(10000);
            driver.quit();
            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


