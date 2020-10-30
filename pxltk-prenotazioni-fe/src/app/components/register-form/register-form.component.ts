import { UtenteService } from './../../services/utente.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import {NgbDateStruct, NgbCalendar} from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit, OnDestroy {
  email: string;
  password: string;
  nome: string;
  cognome: string;
  dataNascita: number;
  dataModel: NgbDateStruct;
  idRuolo: number;
  validRegistration: boolean;
  subs: Subscription[] = [];
  
  constructor(private utenteService: UtenteService, private calendar: NgbCalendar, private router: Router) { }

  ngOnInit(): void {
  }
  
  ngOnDestroy(): void{
    this.subs.forEach(subscription => { subscription.unsubscribe(), console.log('unsubscribe fatta:' + subscription.closed); });
  }

  register(): any {
    this.validRegistration = null;
    const sub = this.utenteService.register(this.email, this.password, this.nome, this.cognome, this.dataModel, this.idRuolo).subscribe(
      data => {
                console.log('registrazione effettuata');
                this.validRegistration = true;
                this.utenteService.updateIsLogged(true);
                sessionStorage.setItem('token', data.token);
                sessionStorage.setItem('email', data.email);
                console.log(data);
                this.router.navigate(['make-booking']);
              },
      error => {this.validRegistration = false; }
    );
    this.subs.push(sub);
  }
}
