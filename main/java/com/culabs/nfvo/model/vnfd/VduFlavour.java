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
 *       &lt;sequence>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}vdu-flavorur-id"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}description"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}vm_image"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}storage"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}cpu"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.vnfd}memory"/>
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
    "vduFlavorurId",
    "description",
    "vmImage",
    "storage",
    "cpu",
    "memory"
})
@XmlRootElement(name = "vdu-flavour")
public class VduFlavour {

    @XmlElement(name = "vdu-flavorur-id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String vduFlavorurId;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(name = "vm_image", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String vmImage;
    @XmlElement(required = true)
    protected Storage storage;
    @XmlElement(required = true)
    protected BigInteger cpu;
    @XmlElement(required = true)
    protected BigInteger memory;

    /**
     * Gets the value of the vduFlavorurId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVduFlavorurId() {
        return vduFlavorurId;
    }

    /**
     * Sets the value of the vduFlavorurId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVduFlavorurId(String value) {
        this.vduFlavorurId = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the vmImage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmImage() {
        return vmImage;
    }

    /**
     * Sets the value of the vmImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmImage(String value) {
        this.vmImage = value;
    }

    /**
     * Gets the value of the storage property.
     * 
     * @return
     *     possible object is
     *     {@link Storage }
     *     
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Sets the value of the storage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Storage }
     *     
     */
    public void setStorage(Storage value) {
        this.storage = value;
    }

    /**
     * Gets the value of the cpu property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCpu() {
        return cpu;
    }

    /**
     * Sets the value of the cpu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCpu(BigInteger value) {
        this.cpu = value;
    }

    /**
     * Gets the value of the memory property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMemory() {
        return memory;
    }

    /**
     * Sets the value of the memory property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMemory(BigInteger value) {
        this.memory = value;
    }

}
