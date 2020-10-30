import { Account } from './../../models/account.model';
import { UtenteService } from './../../services/utente.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit, OnDestroy {

  validEmail: boolean;
  validPassword: boolean;
  logged: boolean;
  email: string;
  password: string;
  loginFail: boolean;
  subs: Subscription[] = [];
  private data: any;

  constructor(private router: Router, private utenteService: UtenteService) {
    this.validEmail = null;
    this.validPassword = null;
    this.logged = false;
    this.loginFail = false;
   }

  ngOnInit(): void {
    if (this.logged === true) {
      this.router.navigate(['booking']);
    }
  }

  ngOnDestroy(): void{
    this.subs.forEach(subscription => { subscription.unsubscribe(), console.log('unsubscribe fatta:' + subscription.closed); });
  }

  login(): void{
    const regexp = new RegExp('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@'
      + '[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$');     // regexp temporanea per verificare la email
    if (!regexp.test(this.email)) {
      this.validEmail = false;
      console.log('valid mail: ' + this.validEmail);
    } else {
      this.validEmail = true;
    }

    if (this.password == null) {
      this.validPassword = false;
    } else {
      this.validPassword = true;
    }

    if (this.validEmail && this.validPassword) {
      this.logged = true;
      const sub = this.utenteService.login(this.email, this.password).subscribe(
        (data) => {
          const account = new Account();
          account.nome = data.nome;
          account.cognome = data.cognome;
          account.dataNascita = data.dataNascita;
          account.email = this.email;
          account.password = this.password;
          account.id = data.id;
          this.utenteService.updateIsLogged(true);
          this.data = data;
          sessionStorage.setItem('token', this.data.token);
          sessionStorage.setItem('email', this.data.email);
          console.log(data);
          this.router.navigate(['booking']);
        },
        error => {
          this.loginFail = true;
          console.log('Errore login');
          const text = 'Credenziali non valide!';
        }
      );
      this.subs.push(sub);
    }
  }

}
