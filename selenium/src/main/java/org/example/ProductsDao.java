package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

/**
 * Class to access database objects
 */
public class ProductsDao {
    /**
     * Hibernate sesssion
     */
    SessionFactory sessionFactory;

    public ProductsDao() {
    }

    /**
     * getting transaction, check if same productDescription exist in database merge and save
     */
    public void saveToDB(List<Product> scrapedProductsList) throws Exception {
        //Get a new Session instance from the session factory and start transaction
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        for (Product product : scrapedProductsList) {
            String queryStr = "from ProductDescription where description=:descriptionOfProduct";
            List<ProductDescription> productDescriptionList = session.createQuery(queryStr).setParameter("descriptionOfProduct", product.getTitle()).getResultList();
            //if product description not found in db then create one
            if (productDescriptionList.size() == 0) {
                ProductDescription productDescription = new ProductDescription(product.getTitle());
                session.saveOrUpdate(productDescription);
                product.setTitle(Integer.toString(productDescription.getId()));
            }
            //if product description found with same id then update description id in products table
            else if (productDescriptionList.size() == 1) {
                product.setTitle(Integer.toString(productDescriptionList.get(0).getId()));
            }

            //Error
            else {
                throw new Exception("Multiple desscription in table");
            }
            //save in db
            session.saveOrUpdate(product);
        }
        //Commit transaction to save it to database
        session.getTransaction().commit();
        //Close the session and release database connection
        session.close();
    }

    public void init() {
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file.
            //Here we are using a configuration file that specifies Java annotations.
            standardServiceRegistryBuilder.configure("hibernate.cfg.xml");

            //Create the registry that will be used to build the session factory
            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
            try {
                //Create the session factory - this is the goal of the init method.
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory,
                        but we had trouble building the SessionFactory, so destroy it manually */
                System.err.println("Session Factory build failed.");
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);
            }
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
        }
    }
}
