import {Movie} from "./movie.model";
import {Showtime} from "./showtime.model";

export class MovieWithShowtimes {
  movie: Movie;
  showtimes: Showtime[];
}
