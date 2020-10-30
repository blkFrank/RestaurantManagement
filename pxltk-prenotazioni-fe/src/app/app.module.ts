import { RoleGuardService } from './guards/role-guard.service';
import { AuthService } from './guards/auth.service';
import { AuthGuardService } from './guards/auth-guard.service';
import { Interceptor } from './interceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { FormsModule } from '@angular/forms';
import { NgbDatepicker, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { BookingComponent } from './components/booking/booking.component';
import { MakeBookingComponent } from './components/make-booking/make-booking.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    RegisterFormComponent,
    NavBarComponent,
    BookingComponent,
    MakeBookingComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true},
    AuthGuardService,
    AuthService,
    RoleGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
