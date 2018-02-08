package com.corenttech.app;

import com.corenttech.model.Root;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

/**
 * @author Vignesh R
 *
 */
public class Main {

    String inputXML;

    void loadXML() throws FileNotFoundException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource("sample.xml").getFile())));
        String line;
        while ((line = br.readLine()) != null) {
//            System.out.println(line);
            sb.append(line);
            sb.append("\n");
        }
        inputXML = sb.toString().trim();
        System.out.println("Input XML:");
        System.out.println(inputXML);
        System.out.println("\n\n");
    }

    void xmlReaderWriter(JAXBContext jaxbContext) throws JAXBException {
//        System.out.println("jaxbContext is=" + jaxbContext.toString());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Root root = (Root) unmarshaller.unmarshal(new StringReader(inputXML));
        System.out.println("Read :");
        System.out.println("Text:" + root.getSometext().getText());
        System.out.println("\n\n");
        System.out.println("Write :");
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(root, sw);
        System.out.println(sw.toString());
        System.out.println("\n\n");
    }

    public static void main(String[] args) throws JAXBException, FileNotFoundException, IOException {
        Main app = new Main();
        JAXBContext jaxbContext;
        app.loadXML();
        System.out.println("using JAXB");
        System.out.println("=================================");
        jaxbContext = JAXBContext.newInstance(Root.class);
        app.xmlReaderWriter(jaxbContext);
        System.out.println("using MOXy");
        System.out.println("=================================");
        jaxbContext = JAXBContextFactory.createContext(new Class[]{Root.class}, null);
        app.xmlReaderWriter(jaxbContext);
    }
}
