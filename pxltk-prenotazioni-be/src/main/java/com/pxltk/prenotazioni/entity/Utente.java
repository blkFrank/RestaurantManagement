package com.pxltk.prenotazioni.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Gregorio Graziano
 * @email gregoriograziano@gmail.com
 */

@Getter
@Setter
@Entity
@Table(name = "utente", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@SecondaryTables({
    @SecondaryTable(name="ruolo")
})
public class Utente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascita")
    private Date dataNascita;
    
    @Column(table = "ruolo", nullable = false)
	private Integer idRuolo;
    
    @OneToOne(cascade =  CascadeType.ALL)
    private Credenziali credenziali;

    @OneToMany(mappedBy = "utente", cascade =  CascadeType.ALL)
    private Set<Prenotazione> prenotazioni;

    public Utente() {
    }
}
