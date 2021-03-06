//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-27 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.23 at 09:36:36 上午 CST 
//


package com.culabs.nfvo.model.nsd;

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
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}ns-flavour-id"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}description"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}assurance-params"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}member-vnfs"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}member-vlds"/>
 *         &lt;element ref="{urn:com.culabs.nfvo.model.nsd}forwarding-graphs"/>
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
    "nsFlavourId",
    "description",
    "assuranceParams",
    "memberVnfs",
    "memberVlds",
    "forwardingGraphs"
})
@XmlRootElement(name = "ns-flavour")
public class NsFlavour {

    @XmlElement(name = "ns-flavour-id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String nsFlavourId;
    protected String description;
    @XmlElement(name = "assurance-params")
    protected AssuranceParams assuranceParams;
    @XmlElement(name = "member-vnfs")
    protected MemberVnfs memberVnfs;
    @XmlElement(name = "member-vlds")
    protected MemberVlds memberVlds;
    @XmlElement(name = "forwarding-graphs")
    protected ForwardingGraphs forwardingGraphs;

    /**
     * Gets the value of the nsFlavourId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNsFlavourId() {
        return nsFlavourId;
    }

    /**
     * Sets the value of the nsFlavourId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNsFlavourId(String value) {
        this.nsFlavourId = value;
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
     * Gets the value of the assuranceParams property.
     * 
     * @return
     *     possible object is
     *     {@link AssuranceParams }
     *     
     */
    public AssuranceParams getAssuranceParams() {
        return assuranceParams;
    }

    /**
     * Sets the value of the assuranceParams property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssuranceParams }
     *     
     */
    public void setAssuranceParams(AssuranceParams value) {
        this.assuranceParams = value;
    }

    /**
     * Gets the value of the memberVnfs property.
     * 
     * @return
     *     possible object is
     *     {@link MemberVnfs }
     *     
     */
    public MemberVnfs getMemberVnfs() {
        return memberVnfs;
    }

    /**
     * Sets the value of the memberVnfs property.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberVnfs }
     *     
     */
    public void setMemberVnfs(MemberVnfs value) {
        this.memberVnfs = value;
    }

    /**
     * Gets the value of the memberVlds property.
     * 
     * @return
     *     possible object is
     *     {@link MemberVlds }
     *     
     */
    public MemberVlds getMemberVlds() {
        return memberVlds;
    }

    /**
     * Sets the value of the memberVlds property.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberVlds }
     *     
     */
    public void setMemberVlds(MemberVlds value) {
        this.memberVlds = value;
    }

    /**
     * Gets the value of the forwardingGraphs property.
     * 
     * @return
     *     possible object is
     *     {@link ForwardingGraphs }
     *     
     */
    public ForwardingGraphs getForwardingGraphs() {
        return forwardingGraphs;
    }

    /**
     * Sets the value of the forwardingGraphs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForwardingGraphs }
     *     
     */
    public void setForwardingGraphs(ForwardingGraphs value) {
        this.forwardingGraphs = value;
    }

}
