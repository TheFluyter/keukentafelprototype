package com.keukentafelprototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUris {

    private String normal;
    private String large;

    @JsonProperty(value = "art_crop")
    private String artCrop;
}
