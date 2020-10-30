package com.pxltk.prenotazioni.dto;

import com.pxltk.prenotazioni.entity.Credenziali;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CredenzialiDTO {

    public CredenzialiDTO(Credenziali credenziali) {
        this.email = credenziali.getEmail();
        this.password = credenziali.getPassword();
        this.created_at = credenziali.getCreated_at();
        this.edited_at = credenziali.getCreated_at();
    }

    public CredenzialiDTO() {
    }

    private String email;
    private String password;
    private Timestamp created_at;
    private Timestamp edited_at;
}
