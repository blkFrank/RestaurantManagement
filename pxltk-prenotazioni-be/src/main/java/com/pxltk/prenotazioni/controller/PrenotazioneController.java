package com.pxltk.prenotazioni.controller;

import com.pxltk.prenotazioni.command.AccountCommand;
import com.pxltk.prenotazioni.command.PrenotazioneCommand;
import com.pxltk.prenotazioni.dto.AccountDTO;
import com.pxltk.prenotazioni.dto.DataValidaDTO;
import com.pxltk.prenotazioni.dto.PrenotazioneDTO;
import com.pxltk.prenotazioni.entity.Prenotazione;
import com.pxltk.prenotazioni.service.iterface.IPrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedList;

@RestController
@RequestMapping("/user/booking")
public class PrenotazioneController {

    @Autowired
    private IPrenotazioneService prenotazioneService;
    
    @GetMapping(value = "/getValidDates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkedList<DataValidaDTO>> getDateValide() throws ParseException{
    	return new ResponseEntity<LinkedList<DataValidaDTO>>(prenotazioneService.getDateValide(), HttpStatus.OK);
    }
    
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrenotazioneDTO> creaAccountUtente(@RequestBody PrenotazioneCommand prenotazioneCommand) throws ParseException {
        return new ResponseEntity<PrenotazioneDTO>(prenotazioneService.creaPrenotazione(prenotazioneCommand), HttpStatus.OK);
    }

    @GetMapping(value = "/getByEmail/{email}")
    public ResponseEntity<LinkedList<PrenotazioneDTO>> leggiPrenotazioniUtente(@PathVariable("email") String email){
        return new ResponseEntity<LinkedList<PrenotazioneDTO>>(prenotazioneService.leggiPrenotazioniByEmail(email), HttpStatus.OK);
    }

    @GetMapping(value = "/getByData/{data}")
    public ResponseEntity<LinkedList<PrenotazioneDTO>> leggiPrenotazioniByData(@PathVariable("data") String data) throws ParseException {
        return new ResponseEntity<LinkedList<PrenotazioneDTO>>(prenotazioneService.leggiPrenotazioniByData(data), HttpStatus.OK);
    }

    @PostMapping(value = "/update/{id}/{data}")
    public ResponseEntity<PrenotazioneDTO> aggiornaPrenotazione(@PathVariable("id") int id, @PathVariable("data") long data ) throws ParseException {
        return new ResponseEntity<PrenotazioneDTO>(prenotazioneService.modificaPrenotazione(id, data), HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<LinkedList<PrenotazioneDTO>> leggiTuttePrenotazioni () {
        return new ResponseEntity<LinkedList<PrenotazioneDTO>>(prenotazioneService.leggiTuttePrenotazioni(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<PrenotazioneDTO> eliminaPrenotazione(@PathVariable("id") int id ) throws ParseException {
        return new ResponseEntity<PrenotazioneDTO>(prenotazioneService.eliminaPrenotazione(id), HttpStatus.OK);
    }
}
