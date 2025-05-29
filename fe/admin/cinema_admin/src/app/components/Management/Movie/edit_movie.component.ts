
import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Cinema } from 'src/app/models/cinema.model';
import { Movie } from 'src/app/models/movie.model';
import { Room } from 'src/app/models/room.model';
import { CinemaService } from 'src/app/services/cinema.service';
import { MovieService } from 'src/app/services/movie.service';
import { RoomService } from 'src/app/services/room.service';



@Component({
  templateUrl: './edit_movie.component.html',
})
export class EditMovieComponent implements OnInit {
  editMovieForm: FormGroup;
  constructor(
    private movieService: MovieService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute
  ) { }
  movieId: number;
  movie: Movie;
  movies: Movie[];
  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const movieId = params["movieId"];
      this.movieId = movieId;
      console.log(this.movieId);
    });
    this.movieService.findById(this.movieId).then(
      res => {
        this.movie = res as Movie;
        console.log(this.movie);
        this.editMovieForm = this.formBuilder.group({
          title: [this.movie.title, Validators.required],
          photo: [this.movie.photo, Validators.required],
          description: [this.movie.description, Validators.required],
          duration: [this.movie.duration, Validators.required],
          genre: [this.movie.genre, Validators.required],
          releaseDate: [this.movie.releaseDate, Validators.required],
          age: [this.movie.age, [Validators.required, Validators.min(1)]],
          trailer: [this.movie.trailer, Validators.required],
          director: [this.movie.director, Validators.required],
          actor: [this.movie.actor, Validators.required],
          publisher: [this.movie.publisher, Validators.required],

        });

      }
    );
    this.editMovieForm = this.formBuilder.group({
      title: ['', Validators.required],
      photo: ['', Validators.required],
      description: ['', Validators.required],
      duration: ['', Validators.required],
      genre: ['', Validators.required],
      releaseDate: [null, Validators.required],
      age: [0,[Validators.required, Validators.min(1)]],
      trailer: ['', Validators.required],
      director: ['', Validators.required],
      actor: ['', Validators.required],
      publisher: ['', Validators.required],

    });
    this.movieService.findAll().then(
      res => {
        this.movies = res as Movie[];
      }
    );
  }

  edit() {
    if (this.editMovieForm.valid) {
      var movie = this.editMovieForm.value as Movie;
      movie.id = this.movie.id;
      this.movieService.edit(movie).then(
        res => {

          console.log(res);
          this.messageService.add({
            severity: "success",
            summary: "Thành công",
            detail: "Sửa phim thành công"
          });
          setTimeout(() => {
            this.router.navigate(['/admin/movie']);
          }, 2000);
        },
        err => {
          this.messageService.add({
            severity: "error",
            summary: "Lỗi",
            detail: "Sửa phim thất bại"
          });
        }

      );


    } else {
      this.messageService.add({
        severity: "error",
        summary: "Lỗi",
        detail: "Vui lòng nhập đủ thông tin"
      });
    }
  }
}
