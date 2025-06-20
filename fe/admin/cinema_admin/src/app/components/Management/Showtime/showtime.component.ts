import {Component, OnInit} from "@angular/core";
import {Cinema} from "src/app/models/cinema.model";
import {Showtime} from "src/app/models/showtime.model";
import {CinemaService} from "src/app/services/cinema.service";
import {ShowtimeService} from "src/app/services/showtime.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {MovieService} from "../../../services/movie.service";
import {RoomService} from "../../../services/room.service";

@Component({
  templateUrl: './showtime.component.html',
  styleUrls: ['./showtime.component.css'],
})
export class ShowtimeComponent implements OnInit {
  isLoading: boolean = false;
  showtimes: Showtime[];
  filteredShowtimes: Showtime[];
  cinemaId: string;// Cinema được chọn
  sortAscending: boolean = true;
  displayShowtimeDialog = false;
  selectedShowtime: any = null;
  newShowtime: any = {
    movieId: '',
    cinemaId: '',
    roomId: '',
    startTime: '',
    endTime: '',
    price: null,
    status: ''
  };
  movies: any[] = [];
  cinemas: any[] = [];
  rooms: any[] = [];
  filteredRooms: any[] = [];
  selectedCinema: any = null;
  statuses = [
    { label: 'Đang hoạt động', value: 'AVAILABLE' },
    { label: 'Ngưng hoạt động', value: 'CANCELLED' },
    { label: 'ĐẦY', value: 'FULL' },
  ];
  displayAddShowtimeDialog = false;
  constructor(
    private showTimeService: ShowtimeService,
    private cinemaService: CinemaService,
    private router: Router,
    private messageService: MessageService,
    private movieService: MovieService,
    private roomService: RoomService,
    private showtimeService: ShowtimeService,
  ) {
  }

  ngOnInit(): void {
    this.loadData();
    this.loadShowtimes();

  }

  update(showTimeId: string) {
    this.router.navigate(['/admin/edit-showtime', showTimeId]);
  }

  openAddShowtimeDialog() {
    this.newShowtime = {
      movieId: '',
      cinemaId: '',
      roomId: '',
      startTime: '',
      endTime: '',
      price: null,
      status: ''
    };
    this.filteredRooms = [];
    this.displayAddShowtimeDialog = true;
  }

// Khi chọn rạp
  onCinemaChange(cinema: any) {
    this.selectedCinema = cinema;

    if (cinema?.id) {
      this.roomService.findRoomInfoByCinemaId(cinema.id).then(res => {
        console.log('roooom ress:', res.data);
        this.rooms = res.data;
      });
    }
  }



  saveShowtime() {
    const payload = {
      movieId: this.newShowtime.movieId?.id,
      cinemaId: this.selectedCinema?.id,
      roomId: this.newShowtime.roomId?.id,
      startTime: this.newShowtime.startTime,
      endTime: this.newShowtime.endTime,
      price: this.newShowtime.price,
      status: this.newShowtime.status || 'AVAILABLE'
    };

    console.log('create payload', payload)

    this.showtimeService.createShowtime(payload).then((res) => {
      if (res.status === 'Success') {
        alert('Tạo suất chiếu thành công!');
        this.displayShowtimeDialog = false;
        this.loadShowtimes(); // Gọi lại để cập nhật danh sách nếu cần
      }
    }).catch(err => {
      console.error('Lỗi khi tạo suất chiếu:', err);
    });
  }

// Tải dữ liệu
  loadData() {
    this.movieService.findAll().then(res => this.movies = res.data);

    this.cinemaService.findAll().then(res => {
      this.cinemas = res.data;

      if (this.cinemas.length > 0) {
        const firstCinemaId = this.cinemas[0].id;

        this.selectedCinema = this.cinemas[0]; // Optional: Gán mặc định
        console.log('selected Cinema:', this.selectedCinema)

        this.roomService.findRoomInfoByCinemaId(firstCinemaId).then(res => {
          console.log('roooom ress:', res.data)
          this.rooms = res.data;
        });
      }
    });
  }

  loadShowtimes() {
    this.isLoading = true;
    this.showtimeService.findAll()
      .then((res) => {
        this.showtimes = res.data;
        this.filteredShowtimes = this.showtimes;
      })
      .catch((err) => {
        console.error('Lỗi khi tải suất chiếu:', err);
      })
      .finally(() => {
        this.isLoading = false;
      });
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

  openShowtimeDialog(showtime: any) {
    this.selectedShowtime = {...showtime}; // tạo bản sao để tránh sửa trực tiếp
    this.displayShowtimeDialog = true;
  }
}
