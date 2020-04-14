package com.corenttech.jaxb.moxy.service;

import com.corenttech.jaxb.moxy.model.Glossary;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.Objects;

public class JAXBMOXyTest {

    private JAXBContext jaxbContext;

    @Before
    public void setup() throws JAXBException {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        jaxbContext = JAXBContext.newInstance(Glossary.class);
    }

    @Test
    public void testXMLParsing() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Glossary glossary = unmarshaller.unmarshal(new StreamSource(getFile("input.xml")), Glossary.class).getValue();
        System.out.println("glossary = " + glossary);
    }

    @Test
    public void testJSONParsing() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
//        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT,false);
        Glossary glossary = unmarshaller.unmarshal(new StreamSource(getFile("input.json")), Glossary.class).getValue();
        System.out.println("glossary = " + glossary);
    }

    private File getFile(String filePath) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).getFile());
    }
}
