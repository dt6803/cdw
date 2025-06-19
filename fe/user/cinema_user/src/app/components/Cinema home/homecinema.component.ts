import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import * as moment from "moment";
import { MovieService } from "src/app/services/movie.service";
import { Movie } from "src/app/models/movie.model";
import { DatePipe } from "@angular/common";
import { ShowTimeService } from "src/app/services/showTime.service";
import { ActivatedRoute, Router } from "@angular/router";
import { CinemaService } from "src/app/services/cinema.service";
import { Cinema } from "src/app/models/cinema.model";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import {MovieWithShowtimes} from "../../models/movieShowtimes.model";

@Component({
  templateUrl: "./homecinema.component.html",
  styleUrls: ['./homecinema.component.css']
})
export class HomeCinemaComponent implements OnInit {

  todayDate1: Date = new Date();
  todayDate2: Date = new Date();
  todayDate3: Date = new Date();
  todayDate4: Date = new Date();
  todayDate5: Date = new Date();
  todayDate6: Date = new Date();
  todayDate7: Date = new Date();
  myParam: string | undefined;
  date1: number;
  date2: number;
  date3: number;
  date4: number;
  date5: number;
  date6: number;
  date7: number;


  constructor(private movieService: MovieService, private datePipe: DatePipe, private showTimeService: ShowTimeService, private router: Router, private route: ActivatedRoute,
    private cinemaService: CinemaService
    ) {}
  movies: Movie[];
  dateSelected: Date;
  cinemas: Cinema[];
  cinemaId: string = '';
  dateSelectedString: string;
  hoursString: string;
  moviesWithShowtimes: MovieWithShowtimes[] = [];

  ngOnInit() {

    this.route.queryParams.subscribe(params => {
      this.myParam = params['status'];
      if(this.myParam == 'false'){
        alert('Hiện không có suất chiếu phù hợp');
      } // In ra giá trị của myParam
    });
    this.todayDate1.setDate(this.todayDate1.getDate()  - 4)
    this.date1 = this.todayDate1.setDate(this.todayDate1.getDate() + 0);
    this.date2 = this.todayDate2.setDate(this.todayDate2.getDate() + 1);
    this.date3 = this.todayDate3.setDate(this.todayDate3.getDate() + 2);
    this.date4 = this.todayDate4.setDate(this.todayDate4.getDate() + 3);
    this.date5 = this.todayDate5.setDate(this.todayDate5.getDate() + 4);
    this.date6 = this.todayDate6.setDate(this.todayDate6.getDate() + 5);
    this.date7 = this.todayDate7.setDate(this.todayDate7.getDate() + 6);


    this.dateSelected = this.todayDate1;
    this.dateSelectedString = this.datePipe.transform(this.dateSelected, 'yyyy-MM-dd');
    this.cinemaService.findAll().then(
      (res) => {
        this.cinemas = res.data as Cinema[];
        this.cinemaId = this.cinemas[0].id;

        this.movieService.findAll(this.dateSelectedString, this.cinemaId).then(
          async (res) => {
            const showtimes = res.data;

            // Lấy danh sách movieId duy nhất
            const movieIds = [...new Set(showtimes.map((s: any) => s.movieId))] as string[];

            // Gọi API lấy thông tin từng phim
            const movieResponses = await Promise.all(
              movieIds.map((id) =>
                this.movieService.findMovieById(id).then((res) => res.data)
              )
            );

            // Map movieId -> movie
            const movieMap: { [key: string]: any } = {};
            movieIds.forEach((id, index) => {
              movieMap[id] = movieResponses[index];
            });

            // Gộp showtimes vào từng phim
            this.moviesWithShowtimes = movieIds.map((id) => ({
              movie: movieMap[id],
              showtimes: showtimes.filter((s: any) => s.movieId === id),
            }));


            console.log('movie showtime:', this.moviesWithShowtimes);
            console.log(this.moviesWithShowtimes[0])
          },
          (err) => console.error(err)
        );
      },
      (err) => console.error(err)
    );




  }
  selectCinema(evt: any){
    this.cinemaId = evt.target.value;
    console.log('selected cinema: ', this.cinemaId);
    this.movieService.findAll(this.dateSelectedString, this.cinemaId).then(
      async (res) => {
        const showtimes = res.data;

        // Lấy danh sách movieId duy nhất
        const movieIds = [...new Set(showtimes.map((s: any) => s.movieId))] as string[];

        // Gọi API lấy thông tin từng phim
        const movieResponses = await Promise.all(
          movieIds.map((id) =>
            this.movieService.findMovieById(id).then((res) => res.data)
          )
        );

        // Map movieId -> movie
        const movieMap: { [key: string]: any } = {};
        movieIds.forEach((id, index) => {
          movieMap[id] = movieResponses[index];
        });

        // Gộp showtimes vào từng phim
        this.moviesWithShowtimes = movieIds.map((id) => ({
          movie: movieMap[id],
          showtimes: showtimes.filter((s: any) => s.movieId === id),
        }));


        console.log('movie showtime:', this.moviesWithShowtimes);

      },
      (err) => console.error(err)
    );
  }
  check(evt: any) {
    var value: any = evt.target.value;

    console.log("selected date: ", value);
    this.dateSelectedString = value;
    this.movieService.findAll(this.dateSelectedString, this.cinemaId).then(
      async (res) => {
        const showtimes = res.data;

        // Lấy danh sách movieId duy nhất
        const movieIds = [...new Set(showtimes.map((s: any) => s.movieId))] as string[];

        // Gọi API lấy thông tin từng phim
        const movieResponses = await Promise.all(
          movieIds.map((id) =>
            this.movieService.findMovieById(id).then((res) => res.data)
          )
        );

        // Map movieId -> movie
        const movieMap: { [key: string]: any } = {};
        movieIds.forEach((id, index) => {
          movieMap[id] = movieResponses[index];
        });

        // Gộp showtimes vào từng phim
        this.moviesWithShowtimes = movieIds.map((id) => ({
          movie: movieMap[id],
          showtimes: showtimes.filter((s: any) => s.movieId === id),
        }));


        console.log('movie showtime:', this.moviesWithShowtimes);
        console.log(this.moviesWithShowtimes[0])
      },
      (err) => console.error(err)
    );
  }
  convertDateToString(date: Date) {
    const formattedDate = moment(date).format("DD-MM-YYYY");
    console.log("AAAAAAAAAAAAAAAAAAAAa", formattedDate);
    return formattedDate;
  }
  checkHours(hours: string): boolean{
    this.hoursString = this.datePipe.transform(this.todayDate1, 'dd/MM/yyyy HH:mm');
    return this.hoursString > hours;
  }

  // Phương thức để cắt chuỗi showDate
  getFormattedDate(isoString: string): string {
    const date = new Date(isoString);
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
  }

}
