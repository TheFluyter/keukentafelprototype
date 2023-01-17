package com.keukentafelprototype.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cards")
public class Card {

    public Card() {
    }

    public Card(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "set_name")
    private String setName;

}
