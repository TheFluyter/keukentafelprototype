package com.keukentafelprototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Legalities {

    private String standard;
    private String modern;
    private String legacy;
    private String vintage;
    private String pauper;
    private String commander;

    @Override
    public String toString() {
        return "Legalities{" +
            "standard='" + standard + '\'' +
            ", modern='" + modern + '\'' +
            ", legacy='" + legacy + '\'' +
            ", vintage='" + vintage + '\'' +
            ", pauper='" + pauper + '\'' +
            ", commander='" + commander + '\'' +
            '}';
    }
}
