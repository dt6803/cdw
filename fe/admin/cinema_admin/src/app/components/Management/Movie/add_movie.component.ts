import { DatePipe } from '@angular/common';
import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Cinema } from 'src/app/models/cinema.model';
import { Movie } from 'src/app/models/movie.model';
import { Room } from 'src/app/models/room.model';
import { CinemaService } from 'src/app/services/cinema.service';
import { MovieService } from 'src/app/services/movie.service';
import { RoomService } from 'src/app/services/room.service';



@Component({
  templateUrl: './add_movie.component.html',
})
export class AddMovieComponent implements OnInit {
  addMovieForm: FormGroup;
  constructor(
    private movieService: MovieService,
    private roomService: RoomService,
    private cinemaService: CinemaService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router,
    private datePipe: DatePipe,
    ) {}
  rooms: Room[];
  cinemas: Cinema[];
  movies: Movie[];
  photo: File;
  ngOnInit(): void {
    this.addMovieForm = this.formBuilder.group({
      title: ['', Validators.required],
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
    this.roomService.findAll().then(
      res => {
        this.rooms = res as Room[];
      }
    );
    this.cinemaService.findAll().then(
      res => {
        this.cinemas = res as Cinema[];
      }
    );
    this.movieService.findAll().then(
      res => {
        this.movies = res as Movie[];
      }
    );
   
  
  }

  add(){
    if(this.addMovieForm.valid){
      var movie = this.addMovieForm.value as Movie;
      var releaseDate = movie.releaseDate.split("-");
      
      movie.releaseDate = releaseDate[2] +  "/" + releaseDate[1] + "/" + releaseDate[0];
      
      var formData = new FormData();
      formData.append('photo', this.photo);
      formData.append('movie', JSON.stringify(movie));
      this.movieService.create(formData).then(
        res => {
      
          console.log(res);
            this.messageService.add({
              severity: "success",
              summary: "Thành công",
              detail: "Thêm phim thành công"
            });
            setTimeout(() => {
              this.router.navigate(['/admin/movie']);
            }, 2000);
        },
        err => {
          this.messageService.add({
            severity: "error",
            summary: "Lỗi",
            detail: "Thêm phim thất bại"
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
  onUpload(evt: any){
    this.photo = evt.target.files[0];
    console.log(this.photo.name);
  }
}