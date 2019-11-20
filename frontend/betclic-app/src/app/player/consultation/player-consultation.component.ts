import { Component, OnInit } from '@angular/core';
import {PlayerService} from '../player.service';
import {Player} from '../player';
import {plainToClass} from 'class-transformer';

@Component({
  selector: 'app-player-consultation',
  templateUrl: './player-consultation.component.html',
  styleUrls: ['./player-consultation.component.css']
})
export class PlayerConsultationComponent implements OnInit {

  public player: Player;

  constructor(private playerService: PlayerService) { }

  ngOnInit() {
    this.playerService.get('M3rzhin').subscribe(response => this.player = plainToClass(Player, response));
  }

}
