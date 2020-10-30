package com.pxltk.prenotazioni.command;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ListCommand {
	private Integer skip;
	private Integer offset;
	private Integer take;
	private String orderByName;
	private String orderByType;
	private Date orderByDate;
	private Integer idSocieta;
}