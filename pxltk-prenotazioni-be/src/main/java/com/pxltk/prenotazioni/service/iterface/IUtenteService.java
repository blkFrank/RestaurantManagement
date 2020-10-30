package com.pxltk.prenotazioni.service.iterface;

import com.pxltk.prenotazioni.command.AccountCommand;
import com.pxltk.prenotazioni.dto.AccountDTO;
import com.pxltk.prenotazioni.dto.CredenzialiDTO;
import com.pxltk.prenotazioni.dto.UtenteDTO;
import com.pxltk.prenotazioni.exception.MngException;

import java.text.ParseException;

/**
 * @author Gregorio Graziano
 * @email gregoriograziano@gmail.com
 */
public interface IUtenteService {
    AccountDTO login(AccountCommand accountCommand) throws MngException;

    UtenteDTO leggiUtenteByMail(String email);

    CredenzialiDTO leggiCredenziali(AccountCommand accountCommand);

    CredenzialiDTO modificaCredenziali(AccountCommand accountCommand);

    UtenteDTO leggiDati(AccountCommand accountCommand);

    UtenteDTO modificaDati(AccountCommand accountCommand);

    AccountDTO leggiAccount(AccountCommand accountCommand);

    AccountDTO creaAccount(AccountCommand accountCommand) throws ParseException;

    AccountDTO modificaAccount(AccountCommand accountCommand) throws ParseException;

    AccountDTO eliminaAccount(AccountCommand accountCommand);

}
