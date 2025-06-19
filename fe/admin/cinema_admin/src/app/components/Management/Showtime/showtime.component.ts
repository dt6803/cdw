import { Component, OnInit } from "@angular/core";
import { Cinema } from "src/app/models/cinema.model";
import { Showtime } from "src/app/models/showtime.model";
import { CinemaService } from "src/app/services/cinema.service";
import { ShowtimeService } from "src/app/services/showtime.service";
import { DropdownModule } from 'primeng/dropdown';
import {  Router } from "@angular/router";
import { MessageService } from "primeng/api";
@Component({
    templateUrl: './showtime.component.html',
    styleUrls: ['./showtime.component.css'],
  })
export class ShowtimeComponent implements OnInit {
    constructor(
      private showTimeService: ShowtimeService,
      private cinemaService: CinemaService,
      private router: Router,
      private messageService: MessageService,
    ){}
    showtimes: Showtime[];
    filteredShowtimes: Showtime[];
    cinemas: Cinema[];
    cinemaId: string;
    selectedCinema: Cinema; // Cinema được chọn
    sortAscending: boolean = true;
    ngOnInit(): void {
      this.showTimeService.findAll().then(
        res => {
          this.showtimes = res.data as Showtime[];
          this.filteredShowtimes = this.showtimes;
        }
      );
      this.cinemaService.findAll().then(
        res => {
          this.cinemas = res as Cinema[];
          // Thêm tùy chọn "Xem tất cả" vào danh sách cinemas
        this.cinemas = [{ id: "", name: "Chọn Rạp",city: "No", address: "", description: "", imageUrl: ""}, ...this.cinemas];
        }
      );
    }
    confirmUpdate(showTimeId: string): void {
      if (confirm('Bạn có chắc muốn sửa?')) {
       this.update(showTimeId);
      }
    }
    update(showTimeId: string){
      this.router.navigate(['/admin/edit-showtime', showTimeId]);
    }

    onCinemaChange(cinema: Cinema) {
      if (cinema) {

        this.cinemaId = cinema.id;
        if(this.cinemaId == "") {
          this.filteredShowtimes = this.showtimes;
        }else {
          this.showTimeService.findAllByCinema(this.cinemaId).then(
            res => {
              this.filteredShowtimes = res as Showtime[];
            }
          );
        }
      } else {
        this.filteredShowtimes = this.showtimes; // Nếu không chọn cinema nào thì hiển thị tất cả
      }
    }
    confirmDelete(showTimeId: string): void {
      if (confirm('Bạn có chắc muốn xóa?')) {
        this.delete(showTimeId);
      }
    }

    delete(id: string){
        this.showTimeService.delete(id).then(
          res => {
            console.log(res);
            this.messageService.add({
              severity: "success",
              summary: "Thành công",
              detail: "Xoá Suất Chiếu thành công"
            });
            this.showTimeService.findAll().then(
                resp => {
                  console.log(resp);
                  this.filteredShowtimes = resp as Showtime[];
                }
            );
          }
          ,
          err => {
            this.messageService.add({
              severity: "error",
              summary: "Lỗi",
              detail: "Xóa Suất Chiếu thất bại"
            });
          }
        );
    }
    sort() {
      // this.filteredShowtimes.sort((a, b) => {
      //   if (this.sortAscending) {
      //     return a.id - b.id; // Sắp xếp tăng dần
      //   } else {
      //     return b.id - a.id; // Sắp xếp giảm dần
      //   }
      // });
      // this.sortAscending = !this.sortAscending;
    }
}
