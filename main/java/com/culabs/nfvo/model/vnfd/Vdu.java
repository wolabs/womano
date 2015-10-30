//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-27 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.23 at 09:37:17 上午 CST 
//


package com.culabs.nfvo.model.vnfd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}vdu-id"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}num-instances"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}workflows"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}vdu-flavour"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}other-constraints"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}network-interfaces"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vduId",
    "numInstances",
    "workflows",
    "vduFlavour",
    "otherConstraints",
    "networkInterfaces"
})
@XmlRootElement(name = "vdu")
public class Vdu {

    @XmlElement(name = "vdu-id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String vduId;
    @XmlElement(name = "num-instances")
    protected BigInteger numInstances;
    protected Workflows workflows;
    @XmlElement(name = "vdu-flavour")
    protected VduFlavour vduFlavour;
    @XmlElement(name = "other-constraints")
    protected OtherConstraints otherConstraints;
    @XmlElement(name = "network-interfaces")
    protected NetworkInterfaces networkInterfaces;

    /**
     * Gets the value of the vduId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVduId() {
        return vduId;
    }

    /**
     * Sets the value of the vduId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVduId(String value) {
        this.vduId = value;
    }

    /**
     * Gets the value of the numInstances property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumInstances() {
        return numInstances;
    }

    /**
     * Sets the value of the numInstances property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumInstances(BigInteger value) {
        this.numInstances = value;
    }

    /**
     * Gets the value of the workflows property.
     * 
     * @return
     *     possible object is
     *     {@link Workflows }
     *     
     */
    public Workflows getWorkflows() {
        return workflows;
    }

    /**
     * Sets the value of the workflows property.
     * 
     * @param value
     *     allowed object is
     *     {@link Workflows }
     *     
     */
    public void setWorkflows(Workflows value) {
        this.workflows = value;
    }

    /**
     * Gets the value of the vduFlavour property.
     * 
     * @return
     *     possible object is
     *     {@link VduFlavour }
     *     
     */
    public VduFlavour getVduFlavour() {
        return vduFlavour;
    }

    /**
     * Sets the value of the vduFlavour property.
     * 
     * @param value
     *     allowed object is
     *     {@link VduFlavour }
     *     
     */
    public void setVduFlavour(VduFlavour value) {
        this.vduFlavour = value;
    }

    /**
     * Gets the value of the otherConstraints property.
     * 
     * @return
     *     possible object is
     *     {@link OtherConstraints }
     *     
     */
    public OtherConstraints getOtherConstraints() {
        return otherConstraints;
    }

    /**
     * Sets the value of the otherConstraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherConstraints }
     *     
     */
    public void setOtherConstraints(OtherConstraints value) {
        this.otherConstraints = value;
    }

    /**
     * Gets the value of the networkInterfaces property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkInterfaces }
     *     
     */
    public NetworkInterfaces getNetworkInterfaces() {
        return networkInterfaces;
    }

    /**
     * Sets the value of the networkInterfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkInterfaces }
     *     
     */
    public void setNetworkInterfaces(NetworkInterfaces value) {
        this.networkInterfaces = value;
    }

}
