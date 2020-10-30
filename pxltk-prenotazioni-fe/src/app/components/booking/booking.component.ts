import { CustomDate } from './../../models/custom-date.model';
import { Prenotazione } from './../../models/prenotazione.model';
import { UtenteService } from './../../services/utente.service';
import { PrenotazioneService } from './../../services/prenotazione.service';
import { Account } from './../../models/account.model';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { MyDate } from 'src/app/models/my-date.model';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit, OnDestroy {
  validDelete: boolean;
  validModify: boolean;
  isModifing: boolean;

  account: Account;
  listBooking: Prenotazione[];
  settedBooking: Prenotazione;

  listDates: CustomDate[];
  settedDate: CustomDate;
  dateListString: string[];


  subs: Subscription[] = [];

  constructor(private prenotazioneService: PrenotazioneService, private utenteService: UtenteService) {
    this.validDelete = null;
    this.validModify = null;
    this.dateListString = null;
    this.listBooking = null;
    this.settedDate = new CustomDate();
   }

  ngOnInit(): void {
    this.listPrenotazioni();
  }

  ngOnDestroy(): void{
    this.subs.forEach(subscription => { subscription.unsubscribe(), console.log('unsubscribe fatta:' + subscription.closed); });
  }

  listPrenotazioni(): void {
    const sub = this.prenotazioneService.listBookingUser(sessionStorage.getItem('email')).subscribe(
      data => { this.listBooking = data; console.log(this.listBooking); }
    );
    this.subs.push(sub);
  }

  deletePrenotazione(prenotazione: Prenotazione): void {
    this.validDelete = false;
    const sub = this.prenotazioneService.deleteBook(prenotazione.id).subscribe(
      (data) => {
        this.listBooking.splice(this.listBooking.indexOf(prenotazione), 1);
        this.validDelete = true;
      },
      (error) => console.log('Errore in eliminazione', error)
    );
    this.subs.push(sub);
  }

  modificaPrenotazione(): void{
    this.validModify = null;
    console.log(this.settedBooking);
    const sub = this.prenotazioneService.modifyBook(this.settedBooking.id, this.settedDate.data).subscribe(
      (Data) => {
        this.settedBooking.data = this.settedDate.data;
        this.settedDate.valida = false;
        this.validModify = true;
        this.isModifing = false;
        console.log('modificato');
      },
      (Error) => {
        this.validModify = false;
        console.log('impossibile modificare la prenotazione');
      }
    );
    this.subs.push(sub);
  }

  apriModificaPrenotazione(prenotazione: Prenotazione): void {
    const sub = this.prenotazioneService.getDates().subscribe(
      data => {
        this.listDates = data;
        this.isModifing = true;
        this.settedBooking = prenotazione;
        console.log(this.listDates);
      }
    );
    this.subs.push(sub);
  }

  DateMillsToString(date: number): string {
    const DATEOPTIONS = {
      weekday: 'long',
      day: 'numeric',
      month: 'numeric',
      year: 'numeric',
    };
    const dateToString = new Date(date).toLocaleDateString(
      'it-IT',
      DATEOPTIONS
    );
    return dateToString;
  }

}
