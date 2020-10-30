package com.pxltk.prenotazioni.dto;

import java.util.Date;

import com.pxltk.prenotazioni.entity.Credenziali;
import com.pxltk.prenotazioni.entity.Utente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private UtenteDTO utenteDTO;
    private CredenzialiDTO credenzialiDTO;
    private String token;
    private String nome;
    private String cognome;
    private long dataNascita;
    private String email;
    private int idRuolo;
    
    public AccountDTO(){}
    public AccountDTO (Utente utente, Credenziali credenziali) {
        utenteDTO = new UtenteDTO();
        credenzialiDTO = new CredenzialiDTO();
        utenteDTO.setNome(utente.getNome());
        utenteDTO.setCognome(utente.getCognome());
        utenteDTO.setData(utente.getDataNascita());
        credenzialiDTO.setEmail(credenziali.getEmail());
        credenzialiDTO.setPassword(credenziali.getPassword());
        credenzialiDTO.setCreated_at(credenziali.getCreated_at());
        credenzialiDTO.setEdited_at(credenziali.getEdited_at());
    }
    
    

}
