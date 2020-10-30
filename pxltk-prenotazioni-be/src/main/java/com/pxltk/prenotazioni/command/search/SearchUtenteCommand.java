package com.pxltk.prenotazioni.command.search;

import java.util.Date;

import com.pxltk.prenotazioni.command.ListCommand;
import com.pxltk.prenotazioni.command.search.SearchUtenteCommand;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchUtenteCommand extends ListCommand{
	private String nome;
	private String Cognome;
	private String email;
	private Date dataNascita;
	private Integer idRuolo;
	private Integer idPrenotazione;
	private Date dataPrenotazione;
}