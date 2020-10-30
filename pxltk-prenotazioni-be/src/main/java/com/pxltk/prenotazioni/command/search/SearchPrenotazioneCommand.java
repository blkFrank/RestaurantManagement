package com.pxltk.prenotazioni.command.search;

import java.util.Date;

import com.pxltk.prenotazioni.command.ListCommand;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class SearchPrenotazioneCommand extends ListCommand {
	private Integer id;
	private String email;
	private Date data;
	
}
