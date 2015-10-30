//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-27 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.23 at 09:36:36 上午 CST 
//


package com.culabs.nfvo.model.nsd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}description"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}name"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}provider"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}version"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}latency-assurance"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}max-end-points"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}properties"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}vld-id"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "vld")
public class Vld {

    @XmlElementRefs({
        @XmlElementRef(name = "latency-assurance", namespace = "urn:com.culabs.nfvo.model.nsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "vld-id", namespace = "urn:com.culabs.nfvo.model.nsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "provider", namespace = "urn:com.culabs.nfvo.model.nsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "description", namespace = "urn:com.culabs.nfvo.model.nsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "max-end-points", namespace = "urn:com.culabs.nfvo.model.nsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "version", namespace = "urn:com.culabs.nfvo.model.nsd", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "properties", namespace = "urn:com.culabs.nfvo.model.nsd", type = Properties.class, required = false),
        @XmlElementRef(name = "name", namespace = "urn:com.culabs.nfvo.model.nsd", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Object> content;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * {@link Properties }
     * {@link String }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

}