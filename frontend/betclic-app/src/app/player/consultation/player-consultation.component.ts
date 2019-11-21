import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {Player} from '../player';
import {PlayerService} from '../player.service';
import {PlayerDataSource} from '../player-data-source';
import {tap} from 'rxjs/operators';
import {PlayerSaisieComponent} from '../saisie/player-saisie.component';
import {MessageDialogService} from '../../technique/message-dialog.service';

@Component({
  selector: 'app-player-consultation',
  templateUrl: './player-consultation.component.html',
  styleUrls: ['./player-consultation.component.css']
})
export class PlayerConsultationComponent implements AfterViewInit, OnInit {

  playersCount: number;
  dataSource: PlayerDataSource;
  displayedColumns: string[] = ['pseudo', 'points', 'rank'];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private playerService: PlayerService, private modalService: MessageDialogService) {}

  ngOnInit() {
      this.playerService.getPlayersCount().subscribe( count => this.playersCount = count);
      this.dataSource = new PlayerDataSource(this.playerService);
      this.dataSource.loadPlayers('', 0, 5);
  }

  ngAfterViewInit() {
      this.paginator.page.pipe(tap(() => this.loadPlayersPage())).subscribe();
  }

  loadPlayersPage() {
      this.playerService.getPlayersCount().subscribe( count => this.playersCount = count);
      this.dataSource.loadPlayers('', this.paginator.pageIndex, this.paginator.pageSize);
  }

  openModalDialogue(player: Player) {
      const playerSaisieComponent = this.modalService.open(PlayerSaisieComponent);
      playerSaisieComponent.result.then(
        () => this.loadPlayersPage(),
        () => {}
      );
      if (player) {
        playerSaisieComponent.componentInstance.inputPlayer = player;
      }
  }

  onClearPlayers() {
      this.playerService.deleteAllPlayers().subscribe(() => this.loadPlayersPage());
  }

}

