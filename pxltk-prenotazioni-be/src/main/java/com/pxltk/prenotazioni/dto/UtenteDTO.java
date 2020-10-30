package com.pxltk.prenotazioni.dto;
import com.pxltk.prenotazioni.entity.Utente;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class UtenteDTO {

    public UtenteDTO(Utente utente) {
        nome = utente.getNome();
        cognome = utente.getCognome();
        data = utente.getDataNascita();
    }

    public UtenteDTO() {
    }

    private String nome;

    private String cognome;

    private Date data;
    
}
