
import {map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {classToPlain, plainToClass} from 'class-transformer';
import {Player} from './player';
import {Routes} from '../routes';

@Injectable()
export class PlayerService {

      constructor(private http: HttpClient) { }


      public save(player: Player): Observable<Player> {
          return this.http.post<Player>(Routes.PLAYER, classToPlain(player)).pipe(
              map(response => plainToClass(Player, response)));
      }

      public create(pseudo: string): Observable<Player> {
          return this.http.put<string>(Routes.PLAYER, classToPlain(pseudo)).pipe(
              map(response => plainToClass(Player, response)));
      }


      public get(pseudo: string): Observable<Player> {
          return this.http.get<Player>(`${Routes.PLAYER}/${pseudo}`).pipe(
              map(response =>  plainToClass(Player, response)));
      }

    public getPlayersSortByPoints(): Observable<Player[]> {
      return this.http.get<Player[]>(`${Routes.PLAYER}`).pipe(
        map(response =>  plainToClass(Player, response)));
    }

    public getPlayersWithPagination(filter = '', sortOrder = 'asc', pageNumber = 0, pageSize = 5): Observable<Player[]> {
      return this.http.get<Player[]>(Routes.PLAYER, {
              params: new HttpParams()
                .set('filter', filter)
                .set('sortOrder', sortOrder)
                .set('pageNumber', pageNumber.toString())
                .set('pageSize', pageSize.toString())
            }).pipe(map(response =>  plainToClass(Player, response)));
    }

}
