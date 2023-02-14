package com.keukentafelprototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MtgCard {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "set_name")
    private String setName;

    @JsonProperty(value = "mana_cost")
    private String manaCost;

    @JsonProperty(value = "cmc")
    private float cmc;

    @JsonProperty(value = "id")
    private String scryfallId;

    @JsonProperty(value = "legalities")
    private Legalities legalities;

    @JsonProperty(value = "image_uris")
    private ImageUris imageUris;

    @Override
    public String toString() {
        return "MtgCard{" +
            "name='" + name + '\'' +
            ", setName='" + setName + '\'' +
            ", manaCost='" + manaCost + '\'' +
            ", cmc=" + cmc +
            ", id='" + scryfallId + '\'' +
            ", legalities=" + legalities +
            ", imageUris=" + imageUris +
            '}';
    }
}
