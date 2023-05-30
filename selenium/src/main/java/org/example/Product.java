package org.example;

import javax.persistence.*;

/**
 * Creates products table with columns
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    String id;
    @Column(name = "price")
    private String price;
    @Column(name = "title")
    private String title;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "url")
    private String productUrl;
    @Column(name = "product_category")
    private String productCategory;
    /**
     * Constructor with column info as arguments
     */
    public Product(String title, String price, String imgUrl, String productUrl,String productCategory) {
        this.price = price;
        this.title = title;
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
        this.productCategory = productCategory;
    }

    /**
     * Empty mandatory constructor
     */
    public Product() {
    }

    //getters setters
    public String getProductUrl() {
        return productUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
