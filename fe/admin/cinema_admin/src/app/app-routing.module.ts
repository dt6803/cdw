import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Chat } from './models/chat.model';
import { ChatComponent } from './components/Chat/chat.component';
import { LoginComponent } from './components/Login/login.component';
import { DashboardComponent } from './components/Dashboard/dashboard.component';
import { LayoutComponent } from './components/Layout/layout.component';
import { CheckLoginService } from './services/checkLogin.service';
import { AccessDenied } from './components/Access Denied/access_denied.component';
import { CinemaComponent } from './components/Management/Cinema/cinema.component';
import { RoomComponent } from './components/Management/Room/room.component';
import { AddRoomComponent } from './components/Management/Room/add_room.component';
import { EditRoomComponent } from './components/Management/Room/edit_room.component';
import { AccountComponent } from './components/Management/Account/account.component';
import { MovieComponent } from './components/Management/Movie/movie.component';
import { AddMovieComponent } from './components/Management/Movie/add_movie.component';
import { EditMovieComponent } from './components/Management/Movie/edit_movie.component';
import { PaymentComponent } from './components/Management/Payment/payment.component';
import { SubComponent } from './components/Management/Sub/sub.component';
import { AddSubComponent } from './components/Management/Sub/add_sub.component';
import { SeatComponent } from './components/Management/Seat/seat.component';
import { ComboComponent } from './components/Management/Combo/combo.component';
import { AddComboComponent } from './components/Management/Combo/add_combo.component';
import { ShowtimeComponent } from './components/Management/Showtime/showtime.component';
import { AddShowtimeComponent } from './components/Management/Showtime/add_showtime.component';
import { EditShowtimeComponent } from './components/Management/Showtime/edit_showtime.component';
import { RatingComponent } from './components/Management/Rating/rating.component';
import { AddSeatComponent } from './components/Management/Seat/add_seat.component';
import { BookingComponent } from './components/Management/Booking/booking.component';

const routes: Routes = [
  {
    path: "",
    component: LoginComponent,
  },
  {
    path: "login",
    component: LoginComponent,
  },
  {
    path: "admin",
    component: LayoutComponent,
    canActivate: [CheckLoginService],

    children: [
      {
        path: "chat",
        component: ChatComponent,
      },
     
      {
        path: "dashboard",
        component: DashboardComponent,
      },
      {
        path: "cinema",
        component: CinemaComponent,
      },
      {
        path: "room",
        component: RoomComponent,
      },
      {
        path: "account",
        component: AccountComponent,
      },
      {
        path: "add-room",
        component: AddRoomComponent,
      },
      {
        path: "edit-room/:roomId",
        component: EditRoomComponent,
      },
      {
        path: "movie",
        component: MovieComponent,
      },
      {
        path: "add-movie",
        component: AddMovieComponent,
      },
      {
        path: "sub",
        component: SubComponent,
      },
      {
        path: "add-sub",
        component: AddSubComponent,
      },
      {
        path: "seat",
        component: SeatComponent,
      },
      {
        path: "add-seat",
        component: AddSeatComponent,
      },
      {
        path: "combo",
        component: ComboComponent,
      },
      {
        path: "add-combo",
        component: AddComboComponent,
      },
      {
        path: "edit-movie/:movieId",
        component: EditMovieComponent,
      },
      {
        path: "payment",
        component: PaymentComponent,
      },
      {
        path: "showtime",
        component: ShowtimeComponent,
      } ,
      {
        path: "add-showtime",
        component: AddShowtimeComponent,
      } ,
      {
        path: "edit-showtime/:showTimeId",
        component: EditShowtimeComponent,
      },
      {
        path: "rating",
        component: RatingComponent,
      } ,
      {
        path: "booking",
        component: BookingComponent,
      } ,

    ]
  },
  {
    path: "access-denied",
    component: AccessDenied,
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
