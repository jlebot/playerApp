import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable()
export class SettingsRequestInterceptor implements HttpInterceptor {

    constructor() {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const urlBackendPrefix = environment.urlBackend;
        if (!urlBackendPrefix) {
            return next.handle(req);
        } else {
            const newRequest = req.clone({url: urlBackendPrefix + req.url});
            return next.handle(newRequest);
        }
    }
}
