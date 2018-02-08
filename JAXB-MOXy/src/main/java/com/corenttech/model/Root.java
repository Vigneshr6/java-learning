/*
 * Copyright (c) All Right Reserved, http://www.corenttech.com
 * 
 * This file is subject to the terms and conditions defined in file 'LICENSE.txt', which is part of this source code package.
 */
package com.corenttech.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vignesh R
 * @created Dec 7, 2017
 */
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {

    @XmlElement
    SomeText sometext;

    public SomeText getSometext() {
        return sometext;
    }

    public void setSometext(SomeText sometext) {
        this.sometext = sometext;
    }
    
}
