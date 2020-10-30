package com.pxltk.prenotazioni.command;

import lombok.Data;

import java.util.Date;

@Data
public class PrenotazioneCommand {
    private String email;
    private long data;
}
