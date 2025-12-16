package com.pfe.gestion.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://pfe.com/soap", name = "getProjetRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetProjetRequest {
    private long id;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
}