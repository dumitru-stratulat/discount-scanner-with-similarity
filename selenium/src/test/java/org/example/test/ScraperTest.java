package org.example.test;

//Import the class that we are going to test
import org.example.*;

//JUnit 5 imports
import static org.junit.jupiter.api.Assertions.*;

import org.example.Timberland;
import org.example.Calvin;
import org.example.Gant;
import org.example.Tommy;
import org.example.NorthFace;
import org.junit.jupiter.api.*;


@DisplayName("Product test")
public class ScraperTest {
    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
    }

//    @Test
//    @DisplayName("Test create product using constructor with 2 args")
//    void testTimberlandScraper() throws InterruptedException {
//        Timberland timberland = new Timberland("https://www.timberland.co.uk/shop/en/tbl-uk/men-clothing-coats-jackets#facet=&beginIndex=0");
//        assertEquals(timberland.scrapedProductsList.isEmpty(),true);
//        timberland.run();
//        timberland.join();
//        assertEquals(timberland.scrapedProductsList.isEmpty(),false);
//    }
//    @Test
//    @DisplayName("Test create product using constructor with 2 args")
//    void testCalvinScraper() throws InterruptedException {
//        Calvin calvin = new Calvin("https://www.calvinklein.co.uk/womens-knitwear");
//        assertEquals(calvin.scrapedProductsList.isEmpty(),true);
//        calvin.run();
//        calvin.join();
//        assertEquals(calvin.scrapedProductsList.isEmpty(),false);
//    }
//    @Test
//    @DisplayName("Test create product using constructor with 2 args")
//    void testGantScraper() throws InterruptedException {
//        Gant gant = new Gant("https://gant.co.uk/mens-jackets-coats");
//        assertEquals(gant.scrapedProductsList.isEmpty(),true);
//        gant.run();
//        gant.join();
//        assertEquals(gant.scrapedProductsList.isEmpty(),false);
//    }
//    @Test
//    @DisplayName("Test create product using constructor with 2 args")
//    void testTommyScraper() throws InterruptedException {
//        Tommy tommy = new Tommy("https://uk.tommy.com/mens-coats-jackets");
//        assertEquals(tommy.scrapedProductsList.isEmpty(),true);
//        tommy.run();
//        tommy.join();
//        assertEquals(tommy.scrapedProductsList.isEmpty(),false);
//    }
    @Test
    @DisplayName("Test create product using constructor with 2 args")
    void testNorthFace() throws InterruptedException {
        NorthFace northFace = new NorthFace("https://www.thenorthface.co.uk/shop/en-gb/tnf-gb/men-jackets-coats-insulated-jackets");
        assertEquals(northFace.scrapedProductsList.isEmpty(),true);
        northFace.run();
        northFace.join();
        assertEquals(northFace.scrapedProductsList.isEmpty(),false);
    }
    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }
}
