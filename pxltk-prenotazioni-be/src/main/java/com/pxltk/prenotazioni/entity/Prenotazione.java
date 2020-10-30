package com.pxltk.prenotazioni.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="utente_id")
    private Utente utente;

    @Temporal(TemporalType.DATE)
    @Column(name = "data", nullable = false)
    private Date data;
}
