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
   ratings: MovieRatings[];
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
    this.movieService.findAll().then(res => {
      this.movies = res as Movie[];
      this.updatePagination();
      this.cdr.detectChanges(); // Đảm bảo UI được cập nhật
    });
  }

  // updatePagination(): void {
  //   this.totalPages = this.getTotalPages();
  //   if (this.totalPages < this.currentPage) {
  //     this.currentPage = 1;  // Reset lại trang nếu trang hiện tại lớn hơn tổng số trang
  //   }
  //   this.cdr.detectChanges();  // Đảm bảo UI được cập nhật sau khi tính toán pagination
  // }
  updatePagination(): void {
    this.totalPages = this.getTotalPages();
    this.cdr.detectChanges();  // Đảm bảo UI được cập nhật sau khi tính toán pagination
  }

  get paginatedMovies(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.movies.slice(startIndex, startIndex + this.itemsPerPage);
  }

  // changePage(page: number): void {
  //   if (page >= 1 && page <= this.totalPages) {
  //     this.currentPage = page;
  //     this.cdr.detectChanges();
  //   }
  // }
  // changePage(page: number): void {
  //   if (page >= 1 && page <= this.totalPages) {
  //     this.currentPage = page; // Cập nhật giá trị currentPage
  //     this.updatePagination(); // Cập nhật lại pagination sau khi đổi trang
  //     this.cdr.detectChanges();  // Cập nhật UI
  //   }
  // }
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

  delete(id: number): void {
    this.movieService.delete(id).then(
      res => {
        this.messageService.add({
          severity: "success",
          summary: "Thành công",
          detail: "Xoá phim thành công"
        });
        this.loadMovies();
      },
      err => {
        this.messageService.add({
          severity: "error",
          summary: "Lỗi",
          detail: "Xóa phim thất bại"
        });
      }
    );
  }

  setVideo(trailer: string): void {
    this.videoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`${trailer}`);
  }

  confirmDelete(movieId: number): void {
    if (confirm('Bạn có chắc muốn xóa?')) {
      this.delete(movieId);
    }
  }

  confirmUpdate(movieId: number): void {
    if (confirm('Bạn có chắc muốn sửa?')) {
      this.update(movieId);
    }
  }
  loadRating(movieId: number): void {
    this.ratingService.findAll(movieId).then(
      res => {
        this.ratings = res.status as MovieRatings[];
        console.log(res.status);
      }
    );
  }
}
