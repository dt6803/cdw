import { Showtime } from "./showtime.model";
import { Sub } from "./sub.model";

export class Movie{
  showtimes: Showtime[]
    // id: number;
    // title: string;
    // description: string;
    // duration: string;
    // genre: string;
    // trailer: string;
    // actor: string;
    // publisher: string;
    // releaseDate: string;
    // photo: string;
    // status: boolean;
    // age: number;
    // director: string;
    // listSubId: number[];
    // listSubName: string[];

  id: string;
  title: string;
  description: string;
  duration: number;
  releaseDate: string; // hoặc Date nếu bạn sẽ parse thành đối tượng Date
  genres: string[];
  director: string;
  casts: string[];
  rating: string;
  posterUrl: string;
  trailerUrl: string;
  language: string;
  status: 'NOW_SHOWING' | 'COMING_SOON' | string; // có thể tùy chỉnh thêm
  subtitle: string;
}
