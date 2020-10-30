package com.pxltk.prenotazioni.dto;

import com.pxltk.prenotazioni.entity.Prenotazione;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PrenotazioneDTO {

    public PrenotazioneDTO(){}

    public PrenotazioneDTO(long data, int id){
        this.data = data;
        this.id = id;
    }
    private int id;
    private long data;
}
