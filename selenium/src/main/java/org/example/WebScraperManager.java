package org.example;

import java.util.List;

/**
 * Class WebScraperManager manages all program
 * Contains method startScraping, that runs scraping and save to database
 */
public class WebScraperManager {
    /**
     * Connects to database,creates a hibernate connection,scrape products using threads,save to db
     */
    public void startScraping(List<WebScraper> webScraperList) throws Exception {

        ProductsDao dao = new ProductsDao();
        dao.init();

        for (WebScraper webScraper : webScraperList) {
            webScraper.start();
        }

        for (WebScraper webScraper : webScraperList) {
            webScraper.join();
        }

        for (WebScraper webScraper : webScraperList) {
            dao.saveToDB(webScraper.scrapedProductsList);
        }
    }

}
