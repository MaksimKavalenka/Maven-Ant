package by.training.jaxb;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import by.training.element.Products;

public abstract class JAXBParser {

    private static final String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    private static final String XMD_SCHEMA = "products.xsd";

    public static boolean isValid(final File file) throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource(XMD_SCHEMA);
            JAXBContext context = JAXBContext.newInstance(Products.class);
            SchemaFactory factory = SchemaFactory.newInstance(XML_SCHEMA);
            Schema schema = factory.newSchema(new StreamSource(resource.getInputStream()));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.unmarshal(file);
        } catch (JAXBException | SAXException e) {
            return false;
        }
        return true;
    }

    public static void createOrUpdateElement(final Products element, final File file)
            throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Products.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(element, file);
    }

    public static Products getElement(final File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Products.class);
        return (Products) context.createUnmarshaller().unmarshal(file);
    }

}
