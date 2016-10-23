package by.training;

import static by.training.constants.PropertyConstants.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import by.training.element.ObjectFactory;
import by.training.element.Product;
import by.training.element.ProductType;
import by.training.element.Products;
import by.training.jaxb.JAXBParser;

public class Main {

    public static void main(final String[] args) throws JAXBException, IOException {
        File in = new File(System.getProperty(IN));
        File out = new File(System.getProperty(OUT));

        if (JAXBParser.isValid(in)) {
            Products products = JAXBParser.getElement(in);

            for (Product product : products.getProduct()) {
                System.out.println("Name: " + product.getName());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Amount: " + product.getAmount());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Type: " + product.getType().value());
                System.out.println();
            }
        }

        ObjectFactory factory = new ObjectFactory();
        Products products = factory.createProducts();
        Product product = factory.createProduct();

        product.setName("Potato");
        product.setPrice(BigInteger.valueOf(50));
        product.setAmount(BigInteger.valueOf(25));
        product.setDescription("For health");
        product.setType(ProductType.SPECIALITY);

        products.getProduct().add(product);
        JAXBParser.createOrUpdateElement(products, out);

    }

}
