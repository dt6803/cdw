import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

import { MovieService } from './services/movie.service';
import { Account } from './models/account.model';
import { Movie } from './models/movie.model';
import { AccountService } from './services/account.service';
import { MessageService } from 'primeng/api';
import { FollowService } from './services/follow.service';
import { TranslateService } from '@ngx-translate/core';

interface AutoCompleteCompleteEvent {
  originalEvent: Event;
  query: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  account: Account | null = null;
  movies: Movie[] = [];
  filteredMovies: Movie[] = [];
  movie: Movie | null = null;
  private routeSub: Subscription = new Subscription();
  isFollowed: boolean = false;
  selectedLanguage: string = 'vi';
  currentUser: string;
  constructor(
    private router: Router,
    private cdr: ChangeDetectorRef,
    private movieService: MovieService,
    private route: ActivatedRoute,
    private accountService: AccountService,
    private messageService: MessageService,
    private followService: FollowService,
    private translate: TranslateService
  ) {
    translate.setDefaultLang('vi');
  }

  ngOnInit(): void {
    console.log('App khởi động!');
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      this.getCurrentUser();
    }
  }

  async getCurrentUser() {
    try {
      const res = await this.accountService.getMyAccountInfo();
      if (res && res.data && res.data.username) {
        console.log('current user: ', res.data.username);
        this.currentUser = res.data.username;
      }
    } catch (error) {
      console.warn('Không thể lấy thông tin người dùng:', error);
      // Không gán currentUser nếu lỗi
      this.currentUser = '';
    }
  }

  async goToAdminPage() {
    console.log('ahahah')
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      // Đảm bảo accessToken có giá trị và được encode URI
      const encodedToken = encodeURIComponent(accessToken);
      window.location.href = `http://localhost:65343?token=${encodedToken}`;
    }
  }


  private async loadMovies(): Promise<void> {
    try {
      const movies = await this.movieService.findAllByStatus();
      this.movies = movies as Movie[];
      console.log('Loaded Movies:', this.movies); // Check the data
    } catch (error) {
      console.error('Error loading movies:', error);
    }
  }

  async logout(): Promise<void> {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    this.currentUser = '';
    this.cdr.detectChanges();
    window.location.href = 'http://localhost:65343/logout?sync=true';
    await this.router.navigate(['/home']);
  }

  filterMovies(event: AutoCompleteCompleteEvent): void {
    const query = event.query.toLowerCase();
    this.filteredMovies = this.movies.filter(movie =>
      movie.title.toLowerCase().includes(query)
    );
}

onMovieSelect(event: any): void {
    if (this.movie) {
      this.router.navigate(['/movie-details', this.movie.id]).then(() => {
        window.location.reload();
        this.movie = null; // Clear selection
      });
    }
}

  title = 'mall_admin';
  follow(){

    const account = JSON.parse(localStorage.getItem('account'));

    if(account == null){
      this.messageService.add({
        severity: "error",
        summary: "Vui lòng đăng nhập",
        detail: "Lỗi"
      });
    } else {
      if(this.isFollowed){
        this.followService.findById(account.id).then(
          res => {
            var follow = {
              accountId: account.id,
              status: false,
              id: res.id
            }
            this.followService.create(follow).then(
              res => {
                this.messageService.add({
                  severity: "success",
                  summary: "Bỏ theo dõi thành công",
                  detail: "Thành công"
                });
                this.isFollowed = false;
                console.log(res);
              }
            );
          }
        );
      } else {
        this.followService.findById(account.id).then(
          res => {
            if(res == null){
              var follow = {
                accountId: account.id,
                status: true
              }
              this.followService.create(follow).then(
                res => {
                  this.messageService.add({
                    severity: "success",
                    summary: "Theo dõi thành công",
                    detail: "Bạn sẽ nhận được thông báo phim hằng ngày."
                  });
                  this.isFollowed = true;
                  console.log(res);
                }
              );
            } else {
              var follow1 = {
                accountId: account.id,
                status: true,
                id: res.id
              }
              this.followService.create(follow1).then(
                res => {
                  this.messageService.add({
                    severity: "success",
                    summary: "Theo dõi thành công",
                    detail: "Bạn sẽ nhận được thông báo phim hằng ngày."
                  });
                  this.isFollowed = true;
                  console.log(res);
                }
              );
            }
          }
        );

      }

    }
    console.log(account);
  }
  switchLanguage(language: string) {
    this.translate.use(language);
  }
}
