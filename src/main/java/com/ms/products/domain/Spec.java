package com.ms.products.domain;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

@XmlRootElement(name = "productSpec")
public class Spec {
	@XmlElement(name = "details")
	private Map<String, String> specifications = new HashMap<String, String>();

	@JsonAnyGetter
	public Map<String, String> getSpecifications() {
		return specifications;
	}

	@JsonAnySetter
	public void setSpecifications(String key, String value) {
		specifications.put(key, value);
	}

}
