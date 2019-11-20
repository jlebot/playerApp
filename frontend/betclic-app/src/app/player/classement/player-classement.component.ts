import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {Player} from '../player';
import {PlayerService} from '../player.service';
import {PlayerDataSource} from '../player-data-source';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-player-classement',
  templateUrl: './player-classement.component.html',
  styleUrls: ['./player-classement.component.css']
})
export class PlayerClassementComponent implements AfterViewInit, OnInit {

  playersCount: number;
  dataSource: PlayerDataSource;
  displayedColumns: string[] = ['pseudo', 'points', 'rank'];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private playerService: PlayerService) {}

  ngOnInit() {
      this.playersCount = 10;
      this.dataSource = new PlayerDataSource(this.playerService);
      this.dataSource.loadPlayers('', 0, 5);
  }

  ngAfterViewInit() {
    this.paginator.page.pipe(tap(() => this.loadPlayersPage())).subscribe();
  }

  loadPlayersPage() {
    this.dataSource.loadPlayers('', this.paginator.pageIndex, this.paginator.pageSize);
  }

  onRowClicked(player: Player) {
    console.log('Selected player: ', player);
  }

}

