package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * Scrape the calvinklein website using scrolling
 */
public class Calvin extends WebScraper {
    private String pageUrl;
    private ChromeOptions options;
    private WebDriver driver;
    private JavascriptExecutor js;

    public Calvin(String pageUrl) {

    }
    @Override
    public void run() {
        String tShirtUrl = "https://www.calvinklein.co.uk/outlet-men#productgroup=s.productgroup_t2dshirts";
        String sweatShirtUrl = "https://www.calvinklein.co.uk/outlet-men#productgroup=s.productgroup_sweatshirts";
        String shirtUrl = "https://www.calvinklein.co.uk/outlet-men#productgroup=s.productgroup_shirts";
        String jacketsUrl = "https://www.calvinklein.co.uk/outlet-men#productgroup=s.productgroup_jacketsandcoats";
        String joggersUrl = "https://www.calvinklein.co.uk/outlet-men#productgroup=s.productgroup_trousers";


//        scrape(tShirtUrl,"tShirt");
//        scrape(sweatShirtUrl,"sweatshirt");
//        scrape(shirtUrl,"shirt");
//        scrape(jacketsUrl,"jacket");
        scrape(joggersUrl,"joggers");
    }

    public void scrape(String pageUrl,String productCategory) {
        //Creating google chrome instance,setting browser size and user agent
        this.options = new ChromeOptions();
        options.setHeadless(false);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1579,970");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                + "AppleWebKit/537.36 (KHTML, like Gecko)"
                + "Chrome/87.0.4280.141 Safari/537.36");

        //Create instance of web driver - this must be on the path.
        this.driver = new ChromeDriver(options);
        driver.get(pageUrl);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int maxPages = 1;
        try {
            for (int i = 1; i <= maxPages; i++) {
                //Wait for page to load
                try {
                    Thread.sleep(7000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    //scroll to triger coockie
                    if (i == 1) {
                        js.executeScript("window.scrollBy(0, 100)", "");
                        try {
                            Thread.sleep(5000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        //accepting cookie
                        WebElement button = driver.findElement(By.cssSelector(".ck-Button.ck-Button__primary.cookie-notice__agree-button"));
                        button.click();
                        try{
                            maxPages = Integer.parseInt(driver.findElement(By.cssSelector("._1vnktZ1t.GDonn_oT")).getText());
                        }catch(Exception e){
                            maxPages = 1;
                        }

                    }
                    //get scroll height
                    int currentScrollValue = 600;
                    int scrollHeight = ((Number) js.executeScript("return document.getElementsByClassName('products')[0].scrollHeight")).intValue();

                    scrollHeight -= 200;
                    //scrolling to lazy load products
                    while (scrollHeight > currentScrollValue) {
                        //cookie handler while scrolling
                        if (!driver.findElements(By.cssSelector(".ck-Button.ck-Button__primary.cookie-notice__agree-button")).isEmpty()) {
                            WebElement closeCookieModal = driver.findElement(By.cssSelector(".ck-Button__no-style.ck-modal__close-btn"));
                            closeCookieModal.click();
                        }
                        ;
                        //subscribition modal handler while scrolling
                        if (!driver.findElements(By.cssSelector(".ck-Button__no-style.ck-modal__close-btn")).isEmpty()) {
                            WebElement closeSubscriptionModalButton = driver.findElement(By.cssSelector(".ck-Button__no-style.ck-modal__close-btn"));
                            closeSubscriptionModalButton.click();
                        }
                        //country selector modal handler while scrolling
                        if (!driver.findElements(By.cssSelector(".ck-Button__no-style.country-switch__button--close")).isEmpty()) {
                            WebElement closeSubscriptionModalButton = driver.findElement(By.cssSelector(".ck-Button__no-style.country-switch__button--close"));
                            closeSubscriptionModalButton.click();
                        }

                        ;
                        System.out.println("curentscroll value " + currentScrollValue);
                        //scrolling
                        js.executeScript("window.scrollBy(0, 600)", "");
                        try {
                            Thread.sleep(3000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        currentScrollValue = currentScrollValue + 600;
                    }
                    List<WebElement> cardList = driver.findElements(By.className("product-list__product"));
                    for (WebElement card : cardList) {
                        String productTitle = card.findElement(By.className("product-tile__name")).getText();
                        String productPrice = card.findElement(By.className("PriceDisplay--price")).getText();
                        String productImage = card.findElement(By.cssSelector("._1pt2gelH > img")).getAttribute("src");
                        String productUrl = card.findElement(By.cssSelector(".link.product-tile__product-link")).getAttribute("href");
                        System.out.println(productTitle);
                        System.out.println(productPrice);
                        System.out.println(productImage);
                        System.out.println(productUrl);
                        scrapedProductsList.add(new Product(productTitle, productPrice, productImage, productUrl,productCategory));
                    }
                    //navigate to next page
                    if (i < maxPages) {
                        System.out.println(i);
                        try {
                            Thread.sleep(5000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        js.executeScript("window.scrollTo(0, 0)", "");
                        WebElement buttonNext = driver.findElement(By.cssSelector(".pagination-button.pagination-button--direction.pagination-button--next-page"));
                        buttonNext.click();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //scroll back to the top
                js.executeScript("window.scrollTo(0, 0)", "");
            }
            try {
                Thread.sleep(5000);
                driver.quit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            driver.quit();
        }
    }
};

