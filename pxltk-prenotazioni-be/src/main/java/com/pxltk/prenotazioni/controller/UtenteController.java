package com.pxltk.prenotazioni.controller;

import com.pxltk.prenotazioni.command.AccountCommand;
import com.pxltk.prenotazioni.dto.AccountDTO;
import com.pxltk.prenotazioni.dto.CredenzialiDTO;
import com.pxltk.prenotazioni.dto.UtenteDTO;
import com.pxltk.prenotazioni.exception.MngException;
import com.pxltk.prenotazioni.service.iterface.IUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
/**
 * @author Gregorio Graziano
 * @email gregoriograziano@gmail.com
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    private IUtenteService utenteService;

    @GetMapping(value = "/getData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtenteDTO> leggiUtente(@RequestBody AccountCommand accountCommand){
        UtenteDTO userDTO = utenteService.leggiDati(accountCommand);
        return new ResponseEntity<UtenteDTO>(userDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/updateData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtenteDTO> aggiornaUtente(@RequestBody AccountCommand accountCommand){
        UtenteDTO userDTO = utenteService.modificaDati(accountCommand);
        return new ResponseEntity<UtenteDTO>(userDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/getCredential", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CredenzialiDTO> leggiCredenziali(@RequestBody AccountCommand accountCommand){
        CredenzialiDTO credenzialiDTO = utenteService.leggiCredenziali(accountCommand);
        return new ResponseEntity<CredenzialiDTO>(credenzialiDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/updateCredential", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CredenzialiDTO> aggiornaCredenziali(@RequestBody AccountCommand accountCommand){
        CredenzialiDTO credenzialiDTO = utenteService.modificaCredenziali(accountCommand);
        return new ResponseEntity<CredenzialiDTO>(credenzialiDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> leggiAccountUtente(@RequestBody AccountCommand accountCommand){
        return new ResponseEntity<AccountDTO>(utenteService.leggiAccount(accountCommand), HttpStatus.OK);
    }

    @PostMapping(value = "/updateAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> modificaAccountUtente(@RequestBody AccountCommand accountCommand) throws ParseException {
        return new ResponseEntity<AccountDTO>(utenteService.modificaAccount(accountCommand), HttpStatus.OK);
    }

    @PostMapping(value = "/deleteAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> eliminaAccountUtente(@RequestBody AccountCommand accountCommand){
        return new ResponseEntity<AccountDTO>(utenteService.eliminaAccount(accountCommand), HttpStatus.OK);
    }

    @GetMapping(value = "/getUtenteByMail/{email}")
    public ResponseEntity<UtenteDTO> getUtenteByEmail(@PathVariable("email") String email){
        return new ResponseEntity<UtenteDTO>(utenteService.leggiUtenteByMail(email), HttpStatus.OK);
    }

}
