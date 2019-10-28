package com.italomlaino.swspeciesmatcher.api.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeciesDTO {

    @JsonProperty("name")
    private String name;
    @JsonProperty("classification")
    private String classification;
    @JsonProperty("designation")
    private String designation;

    public String getName() {
        return name;
    }

    public String getClassification() {
        return classification;
    }

    public String getDesignation() {
        return designation;
    }
}
