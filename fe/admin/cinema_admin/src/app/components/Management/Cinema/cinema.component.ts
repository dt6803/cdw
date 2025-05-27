import { OnInit, Component } from '@angular/core';
import { Cinema } from 'src/app/models/cinema.model';
import { CinemaService } from 'src/app/services/cinema.service';



@Component({
  templateUrl: './cinema.component.html',
})
export class CinemaComponent implements OnInit {

  constructor(
    private cinemaService: CinemaService
    ) {}
  cinemas: Cinema[];
  ngOnInit(): void {
    this.cinemaService.findAll().then(
      res => {
        this.cinemas = res as Cinema[];
      }
    );
   
  }
}
