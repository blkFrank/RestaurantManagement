import { CustomDate } from './../models/custom-date.model';
import { Observable } from 'rxjs';
import { Prenotazione } from './../models/prenotazione.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Constants } from '../shared/constants';

@Injectable({
  providedIn: 'root'
})
export class PrenotazioneService {
  private serverUrl: string;

  constructor(private http: HttpClient) {
    this.serverUrl = Constants.URL_BACK_END;
  }

  newBook(date: number, userMail: string): any{
    const newUrl = Constants.NUOVA_PRENOTAZIONE;
    const prenotazione = new Prenotazione();
    prenotazione.email = userMail;
    prenotazione.data = date;
    return this.http.post<any>(newUrl, prenotazione);
  }

  listBookingUser(email: string): Observable<Prenotazione[]> {
    const newUrl = Constants.GET_LIST_PRENOTAZIONI + email;
    return this.http.get<Prenotazione[]>(newUrl);
  }

  deleteBook(id: number): any {
    const newUrl = Constants.ELIMINA_PRENOTAZIONE + id;
    return this.http.get<any>(newUrl);
  }

  getDates(): Observable<CustomDate[]> {
    const newURL = Constants.GET_LIST_VALID_DATES;
    return this.http.get<CustomDate[]>(newURL);
  }

  modifyBook(idPrenotazione: number, dataUpdate: number): any{
    const newUrl = Constants.AGGIORNA_PRENOTAZIONE + idPrenotazione + '/' + dataUpdate;
    return this.http.post<any>(newUrl, null);
  }
}
