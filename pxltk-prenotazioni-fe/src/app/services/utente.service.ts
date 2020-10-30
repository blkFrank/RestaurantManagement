import { Account } from './../models/account.model';
import { Credenziali } from './../models/credenziali.model';
import { Utente } from './../models/utente.model';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import { Constants } from '../shared/constants';
@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  private userLogged: Account;
  private serverUrl: string;
  isLogged: Subject<boolean>;

  constructor(private http: HttpClient) {
    this.userLogged = new Account();
    this.isLogged = new Subject<boolean>();
    this.isLogged.next(false);
  }

  logout(): void{
    this.isLogged.next(false);
  }

  setUserLogged(user: Account): void{
    this.userLogged.nome = user.nome;
    this.userLogged.cognome = user.cognome;
    this.userLogged.dataNascita = user.dataNascita;
    this.userLogged.email = user.email;
    this.userLogged.id = user.id;
    this.userLogged.password = user.password;
  }

  getuserLogged(): Account{
    return this.userLogged;
  }

  login(email: string, password: string): Observable<Utente> {
    const newUrl = Constants.LOGIN_URL;
    const credenziali = new Credenziali();
    credenziali.email = email;
    credenziali.password = password;
    return this.http.post<Utente>(newUrl, credenziali);
  }

  // tslint:disable-next-line: max-line-length
  register(email: string, password: string, nome: string, cognome: string, dataNascita: NgbDateStruct, idRuolo: number): Observable<Account>{
    const newUrl = Constants.REGISTRA_UTENTE;
    const account = new Account();
    account.email = email;
    account.password = password;
    account.nome = nome;
    account.cognome = cognome;
    account.idRuolo = idRuolo;
    console.log(dataNascita);
    const dateB = new Date(dataNascita.year, dataNascita.month, dataNascita.day);
    account.dataNascita = dateB.getTime();
    console.log(account.dataNascita);
    return this.http.post<Account>(newUrl, account);
  }

  updateIsLogged(state: boolean): void{
    this.isLogged.next(state);
  }
}
