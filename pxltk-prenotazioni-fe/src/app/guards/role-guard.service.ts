import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import decode from 'jwt-decode';
import { AuthService } from './auth.service';
@Injectable()
export class RoleGuardService implements CanActivate {
    constructor(public auth: AuthService, public router: Router) { }
    canActivate(route: ActivatedRouteSnapshot): boolean {
        try {
            const expectedRole = route.data.exprectedPermission;
            const token = sessionStorage.getItem('token');
            const tokenPayload = decode(token);
            if ( !this.auth.isAuthenticated() || !expectedRole.includes(tokenPayload.ROLE)) {
                this.router.navigate(['login']);
                return false;
            }
            return true;
        } catch (e) {
            console.log(e);
            this.router.navigate(['login']);
            return false;
        }
    }
}