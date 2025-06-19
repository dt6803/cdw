export class Movie{
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
