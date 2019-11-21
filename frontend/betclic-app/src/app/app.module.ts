import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PlayerSaisieComponent } from 'src/app/player/saisie/player-saisie.component';
import { PlayerConsultationComponent } from 'src/app/player/consultation/player-consultation.component';
import {PlayerService} from 'src/app/player/player.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {SettingsRequestInterceptor} from './http-communication/settings-request-interceptor.service';
import {MatInputModule, MatPaginatorModule, MatProgressSpinnerModule, MatSortModule, MatTableModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MessageDialogService} from './technique/message-dialog.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    PlayerSaisieComponent,
    PlayerConsultationComponent
  ],
  entryComponents: [
    PlayerSaisieComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule
  ],
  providers: [
    PlayerService,
    MessageDialogService,
    [
      { provide: HTTP_INTERCEPTORS, useClass: SettingsRequestInterceptor, multi: true }
    ]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
