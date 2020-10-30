import { Component, OnInit } from '@angular/core';
import { UtenteService } from './../../services/utente.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  isLogged: boolean;

  constructor(private utenteService: UtenteService) { }

  ngOnInit(): void {
    this.updateStatus();
  }

  updateStatus(): void{
    this.utenteService.isLogged.subscribe(
      data => this.isLogged = data
    );
  }

  logout(): void{
    this.utenteService.logout();
    sessionStorage.clear();
    localStorage.clear();
  }

}
