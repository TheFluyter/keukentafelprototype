package com.keukentafelprototype.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "scryfall_id")
    private String scryfallId;

    @Column(name = "name")
    private String name;

    @Column(name = "set_name")
    private String setName;

    @Override
    public String toString() {
        return "Card{" +
            "scryfallId='" + scryfallId + '\'' +
            ", name='" + name + '\'' +
            ", setName='" + setName + '\'' +
            '}';
    }
}
