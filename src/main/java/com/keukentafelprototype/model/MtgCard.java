package com.keukentafelprototype.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "mtg_cards")
@ToString
public class MtgCard {

    @Id
    @Column(name = "id", nullable = false)
    private String scryfallId;

    @Column(name = "name")
    private String name;
}
