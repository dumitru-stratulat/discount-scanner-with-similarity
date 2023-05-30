package org.example;

import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains main method that runs spring configuration
 */
public class Main {
    public static void main(String[] args) throws Exception {
        runApplicationAnnotationsConfig();
    }

    /**
     * Spring app configuration
     */
    static void runApplicationAnnotationsConfig() throws Exception {
        //Instruct Spring to create and wire beans using annotations.
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Calvin calvin = (Calvin) context.getBean("calvinBean");
        Timberland timberland = (Timberland) context.getBean("timberlandBean");
        NorthFace northFace = (NorthFace) context.getBean("northFaceBeans");
        Tommy tommy = (Tommy) context.getBean("tommyBean");
        Gant gant = (Gant) context.getBean("gantBeans");

        List<WebScraper> webScraperList = new ArrayList<>();
//        webScraperList.add(timberland);
//        webScraperList.add(calvin);
//        webScraperList.add(gant);
//        webScraperList.add(northFace);
        webScraperList.add(tommy);

        WebScraperManager webScraperManager = new WebScraperManager();
        webScraperManager.startScraping(webScraperList);

    }
}


