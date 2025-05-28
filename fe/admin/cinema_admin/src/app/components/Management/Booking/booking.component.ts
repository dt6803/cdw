import { BookingService } from './../../../services/booking.service';
import { OnInit, Component } from '@angular/core';
import { Booking, BookingDetails } from 'src/app/models/booking.model';
import { Cinema } from 'src/app/models/cinema.model';
import { CinemaService } from 'src/app/services/cinema.service';



@Component({
  templateUrl: './booking.component.html',
})
export class BookingComponent implements OnInit {
  visible: boolean;
  bookingDetails: BookingDetails;

  constructor(
    private bookingService: BookingService
    ) {}
  bookings: Booking[];
  ngOnInit(): void {
   
    this.bookingService.findAll().then(
      res => {
        console.log(res);
        this.bookings = res as Booking[];
        this.bookingService.findById(this.bookings[0].id).then(
          res => {
            this.bookingDetails = res as BookingDetails;
            console.log(this.bookingDetails);
          }
        );
      }
    );
   
  }
  showDialog(bookingId: number) {
    
    this.visible = true;
    this.bookingService.findById(bookingId).then(
      res => {
        this.bookingDetails = res as BookingDetails;
        console.log(this.bookingDetails);
      }
    );
    console.log(bookingId);
}
}
