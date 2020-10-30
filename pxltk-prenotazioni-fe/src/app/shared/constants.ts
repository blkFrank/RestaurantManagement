import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class Constants {

    public static URL_BACK_END = 'http://localhost:8080/';
  
    public static MODIFICA_UTENTE = 'user/modifica';
    public static ELIMINA_UTENTE = 'user/elimina';
    public static GET_UTENTE = 'user/get';

    public static GET_LIST_PRENOTAZIONI = 'user/booking/getByEmail/' ;
    public static NUOVA_PRENOTAZIONE = 'user/booking/new';
    public static ELIMINA_PRENOTAZIONE = 'user/booking/delete/';
    public static AGGIORNA_PRENOTAZIONE = 'user/booking/update/';
    public static GET_LIST_VALID_DATES = 'user/booking/getValidDates';
    

    public static REGISTRA_UTENTE = 'noauth/createAccount';
    public static LOGIN_URL = 'noauth/login';
}