import { MessageService } from 'primeng/api';
import { OnInit, Component, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { MovieService } from 'src/app/services/movie.service';
import { Movie } from 'src/app/models/movie.model';
import { MovieRatings } from 'src/app/models/movieRatings.model';
import { RatingService } from 'src/app/services/rating.service';

@Component({
  templateUrl: './movie.component.html',
})
export class MovieComponent implements OnInit, AfterViewInit {
  videoUrl: SafeResourceUrl | undefined;
  movies: Movie[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 3;
  totalPages: number = 0;
  selectedMovie: any = null;
  displayMovieDialog: boolean = false;
  isLoading = false;

  constructor(
    private movieService: MovieService,
    private messageService: MessageService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private cdr: ChangeDetectorRef,
    private ratingService: RatingService
  ) {}

  ngOnInit(): void {
    this.loadMovies();
  }

  ngAfterViewInit(): void {
    this.updatePagination();  // Đảm bảo pagination được cập nhật khi view đã sẵn sàng
  }

  loadMovies(): void {
    this.isLoading = true;
    this.movieService.findAll().then(res => {
      this.movies = res.data as Movie[];
      this.isLoading = false;
      this.updatePagination();
      this.cdr.detectChanges(); // Đảm bảo UI được cập nhật
    });
  }

  openMovieDialog(movie: any) {
    this.selectedMovie = {
      ...movie,
      genresString: movie.genres?.join(', '),
      castsString: movie.casts?.join(', ')
    };
    this.displayMovieDialog = true;
  }
  updatePagination(): void {
    this.totalPages = this.getTotalPages();
    this.cdr.detectChanges();  // Đảm bảo UI được cập nhật sau khi tính toán pagination
  }

  get paginatedMovies(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.movies.slice(startIndex, startIndex + this.itemsPerPage);
  }


  changePage(page: number): void {
  if (page >= 1 && page <= this.totalPages) {
    this.currentPage = page;
    console.log('Current Page:', this.currentPage); // Kiểm tra giá trị currentPage
    this.updatePagination();
    this.cdr.detectChanges();
  }
}

  getTotalPages(): number {
    return Math.ceil(this.movies.length / this.itemsPerPage);
  }

  update(movieId: number): void {
    this.router.navigate(['/admin/edit-movie', movieId]);
  }

  saveMovie() {
    const payload = { ...this.selectedMovie };
    this.isLoading = true;
    if (this.selectedMovie.id) {
      // Đã có id => cập nhật
      this.movieService.updateMovie(this.selectedMovie.id, payload).then((res) => {
        if (res.status === 'Success') {
          alert("Chỉnh sửa thành công");
          this.displayMovieDialog = false;
          this.isLoading = false;
          this.loadMovies();

        }
      });
    } else {
      // Không có id => thêm mới
      this.movieService.createMovie(payload).then((res) => {
        if (res.status === 'Success') {
          alert("Thêm phim thành công");
          this.displayMovieDialog = false;
          this.isLoading = false;
          this.loadMovies();
        }
      });
    }
  }

  showAddMoviePopup() {
    this.selectedMovie = {
      title: '',
      description: '',
      duration: 0,
      releaseDate: '',
      genres: [],
      director: '',
      casts: [],
      rating: '',
      posterUrl: '',
      trailerUrl: '',
      language: '',
      status: 'NOW_SHOWING',
      subtitle: ''
    };
    this.displayMovieDialog = true;
  }




  setVideo(trailer: string): void {
    this.videoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.getEmbedUrl(trailer));
  }

  getEmbedUrl(url: string): string {
    const regExp = /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/watch\?v=|youtu\.be\/)([\w\-]{11})/;
    const match = url.match(regExp);
    return match ? `https://www.youtube.com/embed/${match[1]}` : '';
  }
}
