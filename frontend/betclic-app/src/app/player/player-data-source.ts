import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {Player} from './player';
import {PlayerService} from './player.service';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {catchError, finalize} from 'rxjs/operators';

export class PlayerDataSource implements DataSource<Player> {

    private playerSubject = new BehaviorSubject<Player[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private playerService: PlayerService) {}

    connect(collectionViewer: CollectionViewer): Observable<Player[]> {
        return this.playerSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.playerSubject.complete();
        this.loadingSubject.complete();
    }

    loadPlayers(filter = '', pageIndex = 0, pageSize = 5) {
        this.loadingSubject.next(true);
        this.playerService.getPlayersWithPagination(filter, pageIndex, pageSize)
              .pipe(catchError(() => of([])), finalize(() => this.loadingSubject.next(false)))
              .subscribe(players => this.playerSubject.next(players));
    }
}
