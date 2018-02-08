/*
 * Copyright (c) All Right Reserved, http://www.corenttech.com
 * 
 * This file is subject to the terms and conditions defined in file 'LICENSE.txt', which is part of this source code package.
 */

package com.corenttech.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlValue;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;

/**
 *
 * @author Vignesh R
 * @created Feb 7, 2018
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SomeText {
    @XmlValue
    @XmlCDATA
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
