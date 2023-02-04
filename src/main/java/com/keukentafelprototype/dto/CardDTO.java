package com.keukentafelprototype.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CardDTO {

    public CardDTO() {
    }

    public CardDTO(String name, String setName) {
        this.name = name;
        this.setName = setName;
    }

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Set name is mandatory")
    private String setName;

}
