package com.pxltk.prenotazioni.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pxltk.prenotazioni.command.AccountCommand;
import com.pxltk.prenotazioni.dto.AccountDTO;
import com.pxltk.prenotazioni.exception.MngException;
import com.pxltk.prenotazioni.service.iterface.IUtenteService;

@CrossOrigin
@RestController
@RequestMapping("/noauth")
public class LoginController {
	@Autowired
	private IUtenteService utenteService;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AccountDTO> getUtenteByCredenziali(@RequestBody AccountCommand accountCommand) throws MngException{
        return new ResponseEntity<AccountDTO>(utenteService.login(accountCommand), HttpStatus.OK);
    }
	
	@PostMapping(value = "/createAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> creaAccountUtente(@RequestBody AccountCommand accountCommand) throws ParseException {
        return new ResponseEntity<AccountDTO>(utenteService.creaAccount(accountCommand), HttpStatus.OK);
    }
}
