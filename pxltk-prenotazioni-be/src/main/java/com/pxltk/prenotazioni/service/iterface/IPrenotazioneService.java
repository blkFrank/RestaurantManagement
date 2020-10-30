package com.pxltk.prenotazioni.service.iterface;

import com.pxltk.prenotazioni.command.PrenotazioneCommand;
import com.pxltk.prenotazioni.dto.DataValidaDTO;
import com.pxltk.prenotazioni.dto.PrenotazioneDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

public interface IPrenotazioneService {
	
	LinkedList<DataValidaDTO> getDateValide() throws ParseException;
	
    LinkedList<PrenotazioneDTO> leggiPrenotazioniByData(String data) throws ParseException;

    LinkedList<PrenotazioneDTO> leggiTuttePrenotazioni();

    LinkedList<PrenotazioneDTO> leggiPrenotazioniByEmail(String email);

    PrenotazioneDTO leggiPrenotazioneByIdPrenotazione(int id);

    PrenotazioneDTO creaPrenotazione(PrenotazioneCommand prenotazioneCommand);

    PrenotazioneDTO modificaPrenotazione(int id, long data) throws ParseException;

    PrenotazioneDTO eliminaPrenotazione(int id);
    
    

}
