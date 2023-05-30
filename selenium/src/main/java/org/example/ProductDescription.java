package org.example;

import javax.persistence.*;

/**
 * Creates products productDescription with columns
 */
@Entity
@Table(name = "product_description")
public class ProductDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "description")
    String description;

    public ProductDescription() {
    }

    public String toString() {
        return "id: " + id + "; description: " + description;
    }

    /**
     * Productdescription constructor with table column as argument
     */
    public ProductDescription(String description) {
        this.description = description;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


