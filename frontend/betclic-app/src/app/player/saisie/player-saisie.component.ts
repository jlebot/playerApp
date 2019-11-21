import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {PlayerService} from '../player.service';
import {Player} from '../player';

@Component({
  selector: 'app-player-saisie',
  templateUrl: './player-saisie.component.html',
  styleUrls: ['./player-saisie.component.css']
})
export class PlayerSaisieComponent implements OnInit {

    public player: Player;
    public modeCreation: boolean;

    constructor(public activeModal: NgbActiveModal, public playerService: PlayerService) {
        this.modeCreation = true;
        this.player = new Player();
    }

    ngOnInit() {
    }

    @Input()
    set inputPlayer(playerInput: Player) {
        if (playerInput) {
            this.modeCreation = false;
            this.player.pseudo = playerInput.pseudo;
            this.player.points = playerInput.points;
        }
    }

    public sauvegarder(): void {
        this.playerService.save(this.player).subscribe(() => {
            this.activeModal.close();
        });
    }

    public titrePage(): string {
        if (this.modeCreation) {
            return 'Adding a new player';
        } else {
            return 'Updating ' + this.player.pseudo + ' points';
        }
    }

}
