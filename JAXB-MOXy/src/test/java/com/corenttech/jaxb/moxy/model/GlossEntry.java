package com.corenttech.jaxb.moxy.model;

import lombok.Data;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class GlossEntry {
    @XmlAttribute(name = "ID")
    private String id;
    @XmlPath("GlossDef/para/text()")
//    @XmlCDATA
    private String para;
    @XmlPath(value = "Abbrev/text()")
    private String abbrev;
}
