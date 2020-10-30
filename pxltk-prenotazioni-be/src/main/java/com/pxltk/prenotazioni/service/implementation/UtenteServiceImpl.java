package com.pxltk.prenotazioni.service.implementation;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import com.auth0.jwt.JWT;
import com.pxltk.prenotazioni.command.AccountCommand;
import com.pxltk.prenotazioni.dto.AccountDTO;
import com.pxltk.prenotazioni.dto.CredenzialiDTO;
import com.pxltk.prenotazioni.dto.UtenteDTO;
import com.pxltk.prenotazioni.entity.Credenziali;
import com.pxltk.prenotazioni.entity.Utente;
import com.pxltk.prenotazioni.exception.MngException;
import com.pxltk.prenotazioni.query.QueryUtente;
import com.pxltk.prenotazioni.repository.CredenzialiRepository;
import com.pxltk.prenotazioni.repository.UtenteRepository;
import com.pxltk.prenotazioni.security.SecurityConstants;
import com.pxltk.prenotazioni.service.iterface.IUtenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * @author Gregorio Graziano
 * @email gregoriograziano@gmail.com
 */

@Service("UtenteService")
public class UtenteServiceImpl implements IUtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private CredenzialiRepository credenzialiRepository;
    
    @Autowired
	private QueryUtente queryUtente;

    @Override
    public AccountDTO login(AccountCommand accountCommand) throws MngException{
        Credenziali credenziali = credenzialiRepository.findByEmail(accountCommand.getEmail());
        boolean samePassword = false;
        if (credenziali.getPassword().equals(accountCommand.getPassword())) {
        	samePassword = true;
        }
        
        if (samePassword && credenziali != null) {
            Utente utente = utenteRepository.findByCredenziali_Id(credenziali.getId());
            String token = JWT.create()
            		.withSubject(accountCommand.getEmail())
            		.withClaim("ROLE", utente.getIdRuolo())
            		.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            		.sign(HMAC512(SecurityConstants.SECRET.getBytes()));
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setNome(utente.getNome());
            accountDTO.setCognome(utente.getCognome());
            accountDTO.setDataNascita(utente.getDataNascita().getTime());
            accountDTO.setEmail(credenziali.getEmail());
            accountDTO.setToken(SecurityConstants.TOKEN_PREFIX + token);
            return accountDTO;

        }
        
        throw new MngException("500", "Credenziali non valide!");
    }

    @Override
    public UtenteDTO leggiUtenteByMail(String email) {
        Credenziali credenziali = credenzialiRepository.findByEmail(email);
        Utente utente = utenteRepository.findByCredenziali_Id(credenziali.getId());
        return new UtenteDTO(utente);
    }

    @Override
    public CredenzialiDTO leggiCredenziali(AccountCommand accountCommand) {
        Optional<Credenziali> credenzialiOptional = credenzialiRepository.findById(accountCommand.getId());
        if(credenzialiOptional.isPresent()){
            return new CredenzialiDTO(credenzialiOptional.get());
        }
        return new CredenzialiDTO();
    }

    @Override
    public CredenzialiDTO modificaCredenziali(AccountCommand accountCommand) {
        if (credenzialiRepository.existsById(accountCommand.getId())) {
            Date date = new Date();
            Timestamp edited_at = new Timestamp(date.getTime());
            Credenziali credenziali = credenzialiRepository.aggiornaCredenziali(accountCommand.getId(), accountCommand.getEmail(), accountCommand.getPassword(), edited_at);
            return new CredenzialiDTO(credenziali);
        }
        return new CredenzialiDTO();
    }

    @Override
    public UtenteDTO leggiDati(AccountCommand accountCommand) {
        Optional<Utente> utenteOptional = utenteRepository.findById(accountCommand.getId());
        if(utenteOptional.isPresent()) {
            return new UtenteDTO(utenteOptional.get());
        }
        return new UtenteDTO();
    }

    @Override
    public UtenteDTO modificaDati(AccountCommand accountCommand){
        if (utenteRepository.existsById(accountCommand.getId())) {
            Date date = new Date(accountCommand.getDataNascita());
            Utente utente = utenteRepository.aggiornaUtente(accountCommand.getId(), accountCommand.getNome(), accountCommand.getCognome(), date);
            return new UtenteDTO(utente);
        }
        return new UtenteDTO();
    }

    @Override
    public AccountDTO leggiAccount(AccountCommand accountCommand) {
        AccountDTO accountDTO = new AccountDTO();
        if (utenteRepository.existsById(accountCommand.getId())) {
            Utente utente = utenteRepository.getUtenteECredenziali(accountCommand.getId());
            accountDTO.setUtenteDTO(new UtenteDTO(utente));
            accountDTO.setCredenzialiDTO(new CredenzialiDTO(utente.getCredenziali()));
        }
        return accountDTO;
    }

    @Override
    public AccountDTO creaAccount(AccountCommand accountCommand) throws ParseException {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Credenziali credenziali = new Credenziali();
        credenziali.setEmail(accountCommand.getEmail());
        credenziali.setPassword(accountCommand.getPassword());
        credenziali.setCreated_at(timestamp);
        credenziali.setEdited_at(timestamp);
        credenzialiRepository.save(credenziali);
        System.out.println(accountCommand.getDataNascita());
        Date result = new Date(accountCommand.getDataNascita());
        System.out.println(result);
        Utente utente = new Utente();
        utente.setIdRuolo(accountCommand.getIdRuolo());
        utente.setNome(accountCommand.getNome());
        utente.setCognome(accountCommand.getCognome());
        utente.setDataNascita(result);
        utente.setCredenziali(credenziali);
        utenteRepository.save(utente);
        
        AccountDTO accountDTO = new AccountDTO();
        String token = JWT.create()
        		.withSubject(accountCommand.getEmail())
        		.withClaim("ROLE", utente.getIdRuolo())
        		.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        		.sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        accountDTO.setNome(accountCommand.getNome());
        accountDTO.setCognome(accountCommand.getCognome());
        accountDTO.setDataNascita(accountCommand.getDataNascita());
        accountDTO.setEmail(accountCommand.getEmail());
        accountDTO.setIdRuolo(accountCommand.getIdRuolo());
        accountDTO.setToken(SecurityConstants.TOKEN_PREFIX + token);
        System.out.println(accountDTO.toString());
        return accountDTO;
    }

    @Override
    public AccountDTO modificaAccount(AccountCommand accountCommand) throws ParseException {
        if (utenteRepository.existsById(accountCommand.getId())) {
        	Date date = new Date(accountCommand.getDataNascita());
            Utente utente = utenteRepository.aggiornaUtente(accountCommand.getId(), accountCommand.getNome(), accountCommand.getCognome(), date);
            date = new Date();
            Timestamp edited_at = new Timestamp(date.getTime());
            Credenziali credenziali = credenzialiRepository.aggiornaCredenziali(accountCommand.getId(), accountCommand.getEmail(), accountCommand.getPassword(), edited_at);
            return new AccountDTO(utente, credenziali);
        }
        return new AccountDTO();
    }

    @Override
    public AccountDTO eliminaAccount(AccountCommand accountCommand) {
        AccountDTO accountDTO = new AccountDTO();
        if(utenteRepository.existsById(accountCommand.getId())) {
            accountDTO.setUtenteDTO(new UtenteDTO(utenteRepository.findById(accountCommand.getId()).get()));
            accountDTO.setCredenzialiDTO(new CredenzialiDTO(credenzialiRepository.findById(accountCommand.getId()).get()));
            utenteRepository.deleteById(accountCommand.getId());
        }
        return accountDTO;
    }


}
