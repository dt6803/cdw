import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
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
    templateUrl: './add_showtime.component.html',
  })
export class AddShowtimeComponent implements OnInit {
    addShowtimeForm: FormGroup;
    constructor(
        private roomService: RoomService,
        private cinemaService: CinemaService,
        private formBuilder: FormBuilder,
        private movieService: MovieService,
        private showtimeService: ShowtimeService,
        private messageService: MessageService,
        private subService: SubService,
        private router: Router,
        private datePipe: DatePipe
        ) {}
      rooms: Room[];
      cinemas: Cinema[];
      movies: Movie[];
      subs: Sub[];
    ngOnInit(): void {
        this.addShowtimeForm = this.formBuilder.group({
            showDate: ['', Validators.required],
            cinemaId: [0, Validators.required],
            movieId: [0, Validators.required],
            subId: [0, Validators.required],
            roomId: [0, Validators.required],
           
          });
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
         
    this.addShowtimeForm.get('cinemaId')?.valueChanges.subscribe(cinemaId => {
        if (cinemaId) {
            if(cinemaId == -1) {
                this.rooms = [];
            }else{
                this.updateRooms(cinemaId);
            }
        } else {
          this.rooms = [];
        }
      });

      
    }

    // Cập Nhật Lại List Rooms Khi Cinema Được Chọn
  updateRooms(cinemaId: number): void {
    this.roomService.findByCinemaId(cinemaId).then(res => {
      this.rooms = res as Room[];
      // Reset roomId if no rooms available
      this.addShowtimeForm.get('roomId')?.reset();
    });
  }
    add(){
        if(this.addShowtimeForm.valid){
            var showTime = this.addShowtimeForm.value as ShowTime; 
            showTime.showDate = this.formatDate(showTime.showDate);
            console.log(showTime);
            this.showtimeService.create(showTime).then(
              res => {
                console.log(res);
                this.messageService.add({
                  severity: "success",
                  summary: "Thành công",
                  detail: "Thêm Suất Chiếu thành công"
                });
                setTimeout(() => {
                  this.router.navigate(['/admin/showtime']);
                }, 2000);
            },
              err => {
                this.messageService.add({
                  severity: "error",
                  summary: "Lỗi",
                  detail: "Thêm Suất Chiếu thất bại"
                });
                }
            )

    }
    else {
      this.messageService.add({
        severity: "error",
        summary: "Lỗi",
        detail: "Vui lòng nhập đủ thông tin"
      });
    }
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
