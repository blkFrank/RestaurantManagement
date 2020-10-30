package com.pxltk.prenotazioni.command;

import com.pxltk.prenotazioni.entity.Credenziali;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Gregorio Graziano
 * @email gregoriograziano@gmail.com
 */
@Data
public class AccountCommand {
    private int id;
    private String nome;
    private String cognome;
    private long dataNascita;
    private Integer idRuolo;
    private String email;
    private String password;
}
