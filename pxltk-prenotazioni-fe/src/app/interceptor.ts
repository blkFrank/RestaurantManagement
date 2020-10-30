import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError, timeout } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Constants } from './shared/constants';

@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const BASE_URL = Constants.URL_BACK_END;
    console.log('intercepted request ... ');
    let newReq;
    if (req.url.indexOf('user') === -1 && req.url.indexOf('noauth') === -1 ) {
      return next.handle(req);
    } else if (req.url.indexOf('user') === 0) {
      const newUrl = BASE_URL + req.url;
      console.log(newUrl);
      newReq = req.clone({
        headers: new HttpHeaders({
          'Authorization': sessionStorage.getItem('token'),
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
        }),
        url: newUrl
      });
    }  else if (req.url.indexOf('noauth') === 0) {
      const newUrl = BASE_URL + req.url;
      console.log(newUrl);
      newReq = req.clone({
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
        }),
        url: newUrl
      });
    }

    console.log('Sending request with new header now ...');
    return next.handle(newReq)
      .pipe(timeout(300000))
      .pipe(catchError((error, caught) => {
        console.log(error);
        this.handleAuthError(error);
        return of(error);
      }) as any);
  }

  private handleAuthError(err: HttpErrorResponse): Observable<any> {
    if (err.status === 0) {
      console.log('handled error ' + err.status);
      return of(err.message);
    }
    if (err.status === 404) {
      console.log('errore 404 ' + err.status);
      return of(err.message);
    }
    if (err.status === 500) {
      console.log('errore 500 ' + err.message);
      return of(err.message);
    }
    throw err;
  }
}
