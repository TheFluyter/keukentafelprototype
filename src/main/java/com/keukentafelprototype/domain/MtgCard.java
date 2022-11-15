package com.keukentafelprototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MtgCard {

    private String name;
    private String id;

    @JsonProperty(value = "image_uris")
    private ImageUris imageUris;

    @Override
    public String toString() {
        return "MtgCard{" +
            "name='" + name + '\'' +
            ", id='" + id + '\'' +
            '}';
    }
}
