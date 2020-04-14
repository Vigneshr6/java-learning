package com.corenttech.jaxb.moxy.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Unit test for simple App.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Glossary {
    @XmlElement
    private String title;

    @XmlElementWrapper(name = "GlossList")
    @XmlElement(name = "GlossEntry")
    private List<GlossEntry> glossList;
}
