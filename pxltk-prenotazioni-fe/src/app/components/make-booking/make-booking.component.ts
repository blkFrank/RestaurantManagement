import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { CustomDate } from 'src/app/models/custom-date.model';
import { PrenotazioneService } from 'src/app/services/prenotazione.service';
import { UtenteService } from 'src/app/services/utente.service';
import { Subscription } from 'rxjs';
import { MyDate } from 'src/app/models/my-date.model';

@Component({
  selector: 'app-make-booking',
  templateUrl: './make-booking.component.html',
  styleUrls: ['./make-booking.component.css']
})
export class MakeBookingComponent implements OnInit, OnDestroy {
  validBook: boolean;
  settedDate: CustomDate;
  listDates: CustomDate[];
  subs: Subscription[] = [];

  constructor(private prenotazioneService: PrenotazioneService, private utenteService: UtenteService) {
    this.validBook = null;
    this.listDates = [];
    const sub = this.prenotazioneService.getDates().subscribe(         // get della lista dei giorni prenotabili
      data =>  {
        this.listDates = data;
        console.log(this.listDates);
    }
    );
    this.subs.push(sub);

  }
  ngOnDestroy(): void {
    this.subs.forEach(subscription => {subscription.unsubscribe(), console.log('unsubscribe fatta:' + subscription.closed); });
  }

  ngOnInit(): void {
  }

  addBook(): void {
    console.log(this.settedDate);
    const sub = this.prenotazioneService.newBook(this.settedDate.data, sessionStorage.getItem('email')).subscribe(   // to do
      data => {
        this.settedDate.valida = false;
        this.validBook = true;
      },
      error => {
        this.validBook = false;
      }
    );
    this.subs.push(sub);
  }
  
  DateToString(date: Date): string{
    const DATEOPTIONS = {weekday: 'long', day: 'numeric', month: 'numeric', year: 'numeric'};
    const dateToString = new Date(date).toLocaleDateString('it-IT', DATEOPTIONS);
    return dateToString;

  }
}
