import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { Cinema } from "src/app/models/cinema.model";
import { Movie } from "src/app/models/movie.model";
import { Room } from "src/app/models/room.model";
import { ShowTime } from "src/app/models/showtime.model";

import { Sub } from "src/app/models/sub.model";
import { CinemaService } from "src/app/services/cinema.service";
import { MovieService } from "src/app/services/movie.service";
import { RoomService } from "src/app/services/room.service";
import { ShowtimeService } from "src/app/services/showtime.service";

import { SubService } from "src/app/services/sub.service";

@Component({
    templateUrl: './edit_showtime.component.html',
  })
  export class EditShowtimeComponent implements OnInit{
   editShowtimeForm : FormGroup
    constructor(
        private roomService: RoomService,
        private cinemaService: CinemaService,
        private formBuilder: FormBuilder,
        private movieService: MovieService,
        private showtimeService: ShowtimeService,
        private messageService: MessageService,
        private subService: SubService,
        private router: Router,
        private route: ActivatedRoute
        ) {}
         
    rooms: Room[];
    cinemas: Cinema[];
    movies: Movie[];
    subs: Sub[];
    showtimeId: number;
    showtime: ShowTime;
    date: Date;
        ngOnInit(): void {
            this.route.params.subscribe((params) => {
                const showTimeId = params["showTimeId"];
                this.showtimeId = showTimeId;
                console.log(this.showtimeId);
              });
              this.showtimeService.findById(this.showtimeId).then(
                res => {
                  this.showtime = res as ShowTime;
                  console.log(res);
                  console.log(this.showtime);
                  this.editShowtimeForm = this.formBuilder.group({
                    showDate: [this.showtime.showDate, Validators.required],
                    cinemaId: [this.showtime.cinemaId, Validators.required],
                    movieId: [this.showtime.movieId, Validators.required],
                    subId: [this.showtime.subId, Validators.required],
                    roomId: [this.showtime.roomId, Validators.required],
                   
                  });
                  this.updateRooms(this.showtime.cinemaId);
                }

              );
             
              this.cinemaService.findAll().then(
                res => {
                  this.cinemas = res as Cinema[];
                  this.cinemas = [{ id: -1, name: "Chọn Rạp",city: "No",district: "No",status: true }, ...this.cinemas];
                }
              );
            this.subService.findAll().then(
              res => {
                this.subs = res as Sub[]
              }
    
            )
            this.movieService.findAll().then(
                res => {
                  this.movies = res as Movie[]
                }
            )
            this.editShowtimeForm.get('cinemaId')?.valueChanges.subscribe(cinemaId => {
                console.log('Cinema ID changed:', cinemaId); // Log để kiểm tra
                if (cinemaId) {
                    this.updateRooms(cinemaId);
                } else {
                    this.rooms = [];
                }
            }); 
        }
        // Cập Nhật Lại List Rooms Khi Cinema Được Chọn
        updateRooms(cinemaId: number): void {
            this.roomService.findByCinemaId(cinemaId).then(res => {
                this.rooms = res as Room[];
        
                // Nếu danh sách phòng rỗng, reset giá trị roomId
                if (this.rooms.length === 0) {
                    this.editShowtimeForm.get('roomId')?.reset();
                }
            });
        }
        edit() {
          if(this.editShowtimeForm.valid){
            var showtime = this.editShowtimeForm.value as ShowTime;
            showtime.id = this.showtime.id;
            showtime.showDate = this.formatDate(this.editShowtimeForm.value.showDate);
            console.log(this.editShowtimeForm.value.showDate);
            this.showtimeService.edit(showtime).then(
              res => {
            
                console.log(res);
                  this.messageService.add({
                    severity: "success",
                    summary: "Thành công",
                    detail: "Sửa Suất Chiếu thành công"
                  });
                  setTimeout(() => {
                    this.router.navigate(['/admin/showtime']);
                  }, 2000);
              },
              err => {
                this.messageService.add({
                  severity: "error",
                  summary: "Lỗi",
                  detail: "Sửa Suất Chiếu thất bại"
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
        updateRoom1(evt: any){
          console.log(evt.target.value);
          this.roomService.findByCinemaId(evt.target.value).then(res => {
            this.rooms = res as Room[];
    
           
        });
        }
        formatDate(dateString: string): string {
          const date = new Date(dateString);
        
          const day = date.getDate().toString().padStart(2, '0');
          const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Tháng bắt đầu từ 0 nên cần +1
          const year = date.getFullYear();
        
          const hours = date.getHours().toString().padStart(2, '0');
          const minutes = date.getMinutes().toString().padStart(2, '0');
          const seconds = date.getSeconds().toString().padStart(2, '0');
        
          return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
        }
  }