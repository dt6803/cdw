import { MovieService } from 'src/app/services/movie.service';
import { Component, OnInit } from '@angular/core';
import { Movie } from 'src/app/models/movie.model';

@Component({
  templateUrl: './news.component.html',
})
export class NewsComponent implements OnInit{

  constructor(private movieService: MovieService) {}

  movies: Movie[]

  ngOnInit(): void {

    this.movieService.findAllByStatus().then(
      (res) => {
          this.movies = res.data as Movie[];
          console.log(this.movies[0], 'test1')
      },
      (err) => {
          console.error('Error fetching movies:', err);
      }
    )

  }

}
