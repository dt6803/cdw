import { OnInit, Component } from '@angular/core';
import { MovieRatings } from 'src/app/models/movieRatings.model';
import { CinemaService } from 'src/app/services/cinema.service';
import { RatingService } from 'src/app/services/rating.service';



@Component({
  templateUrl: './rating.component.html',
})
export class RatingComponent implements OnInit {

  constructor(
    private ratingService: RatingService
    ) {}
  ratings: MovieRatings[];
  movieID: number;
  ngOnInit(): void {
    this.ratingService.findAll(this.movieID).then(
      res => {
        this.ratings = res.status as MovieRatings[];
        console.log(res.status);
      }
    );
   
  }
}
