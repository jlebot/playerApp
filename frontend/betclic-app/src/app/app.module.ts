import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PlayerConsultationComponent } from 'src/app/player/consultation/player-consultation.component';
import { PlayerSaisieComponent } from 'src/app/player/saisie/player-saisie.component';
import { PlayerClassementComponent } from 'src/app/player/classement/player-classement.component';
import {PlayerService} from 'src/app/player/player.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SettingsRequestInterceptor} from './http-communication/settings-request-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    PlayerConsultationComponent,
    PlayerSaisieComponent,
    PlayerClassementComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    PlayerService,
    [
      { provide: HTTP_INTERCEPTORS, useClass: SettingsRequestInterceptor, multi: true }
    ]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
