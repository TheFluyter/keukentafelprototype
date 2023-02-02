package com.keukentafelprototype.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {

    public CardDTO() {
    }

    public CardDTO(String name, String setName) {
        this.name = name;
        this.setName = setName;
    }

    private String name;
    private String setName;
}
