
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


      public save(player: Player) {
          return this.http.post<Player>(Routes.PLAYER, classToPlain(player)).pipe(
              map(response => plainToClass(Player, response)));
      }

      public getPlayersWithFilterAndPagination(filter = '', pageNumber = 0, pageSize = 5): Observable<Player[]> {
          return this.http.get<Player[]>(Routes.PLAYER, {
                  params: new HttpParams()
                    .set('filter', filter)
                    .set('pageNumber', pageNumber.toString())
                    .set('pageSize', pageSize.toString())
                }).pipe(map(response =>  plainToClass(Player, response)));
      }

      public getPlayersCount(filter = ''): Observable<number> {
          return this.http.get<number>(Routes.PLAYERS_COUNT, {params: new HttpParams().set('filter', filter)});
      }

      public deleteAllPlayers() {
          return this.http.delete(Routes.PLAYER);
      }

}
