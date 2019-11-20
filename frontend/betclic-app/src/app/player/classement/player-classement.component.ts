import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {Player} from '../player';

@Component({
  selector: 'app-player-classement',
  templateUrl: './player-classement.component.html',
  styleUrls: ['./player-classement.component.css']
})
export class PlayerClassementComponent implements OnInit {

  displayedColumns: string[] = ['pseudo', 'points', 'rank'];
  dataSource = new MatTableDataSource<Player>(ELEMENT_DATA);

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
  }
}

const ELEMENT_DATA: Player[] = [
  {pseudo: 'test', points: 1000, rank: 1}
];
