import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable()
export class AuthService {

    public jwtHelper: JwtHelperService = new JwtHelperService();

    constructor() { }
    public isAuthenticated(): boolean {
        const token = sessionStorage.getItem('token');
        if (token !== null) {
            try {
                return !this.jwtHelper.isTokenExpired(token);
            } catch (e) {
                console.log(e);
                return false;
            }
        }
        return false;
    }
}
