package org.example;

import org.springframework.context.annotation.*;

/**
 * Create beans for everh classs scraper
 */
@Configuration
public class AppConfig {

    @Bean
    public Calvin calvinBean() {
        Calvin calvinInstance = new Calvin("https://www.calvinklein.co.uk/mens-shirts");
        return calvinInstance;
    }

    @Bean
    public Tommy tommyBean() {
        Tommy tommyInstance = new Tommy("https://uk.tommy.com/mens-lounge-sleepwear");
        return tommyInstance;
    }

    @Bean
    public NorthFace northFaceBeans() {
        NorthFace northFaceInstance = new NorthFace("https://www.thenorthface.co.uk/shop/en-gb/tnf-gb/men-tops-shirts-t-shirts");
        return northFaceInstance;
    }

    @Bean
    public Gant gantBeans() {
        Gant gantInstance = new Gant("https://gant.co.uk/mens-jackets-coats");
        return gantInstance;
    }

    @Bean
    public Timberland timberlandBean() {
        Timberland timberlandInstance = new Timberland("https://www.timberland.co.uk/shop/en/tbl-uk/outlet-collection-mens-outlet?facet=CATEGORY%253DApparel,CATEGORY_TYPE%253DSweaters,CATEGORY_TYPE%253DShirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2#facet=CATEGORY%253DApparel,CATEGORY_TYPE%253DSweaters,CATEGORY_TYPE%253DShirts&banner=S23_02.MEN_APPAREL.OUTLET_LP.CTA2&beginIndex=0");
        return timberlandInstance;
    }
}