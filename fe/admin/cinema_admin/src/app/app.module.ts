
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChatComponent } from './components/Chat/chat.component';
import { BaseUrlService } from './services/baseUrl.service';
import { ChatService } from './services/chatService.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Chat } from './models/chat.model';
import { DatePipe } from '@angular/common';
import { LoginComponent } from './components/Login/login.component';
import { DashboardComponent } from './components/Dashboard/dashboard.component';
import { LayoutComponent } from './components/Layout/layout.component';
import { CheckLoginService } from './services/checkLogin.service';
import { AccountService } from './services/account.service';
import { AccessDenied } from './components/Access Denied/access_denied.component';
import { CinemaComponent } from './components/Management/Cinema/cinema.component';
import { CinemaService } from './services/cinema.service';
import { RoomService } from './services/room.service';
import { RoomComponent } from './components/Management/Room/room.component';
import { AddRoomComponent } from './components/Management/Room/add_room.component';
import { ToastModule } from "primeng/toast";
import { ConfirmationService, MessageService } from "primeng/api";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { EditRoomComponent } from './components/Management/Room/edit_room.component';
import { AccountComponent } from './components/Management/Account/account.component';
import { MovieComponent } from './components/Management/Movie/movie.component';
import { MovieService } from './services/movie.service';
import { AddMovieComponent } from './components/Management/Movie/add_movie.component';
import { EditMovieComponent } from './components/Management/Movie/edit_movie.component';
import { PaymentComponent } from './components/Management/Payment/payment.component';
import { PaymentService } from './services/payment.service';
import { SeatComponent } from './components/Management/Seat/seat.component';
import { AddSeatComponent } from './components/Management/Seat/add_seat.component';
import { SubComponent } from './components/Management/Sub/sub.component';
import { AddSubComponent } from './components/Management/Sub/add_sub.component';
import { SubService } from './services/sub.service';
import { SeatService } from './services/seat.service';
import { ComboService } from './services/combo.service';
import { ComboComponent } from './components/Management/Combo/combo.component';
import { AddComboComponent } from './components/Management/Combo/add_combo.component';
import { ShowtimeComponent } from './components/Management/Showtime/showtime.component';
import { AddShowtimeComponent } from './components/Management/Showtime/add_showtime.component';
import { EditShowtimeComponent } from './components/Management/Showtime/edit_showtime.component';
import { ShowtimeService } from './services/showtime.service';
import { DropdownModule } from 'primeng/dropdown';
import { RatingComponent } from './components/Management/Rating/rating.component';
import { RatingService } from './services/rating.service';
import { RatingModule } from 'primeng/rating';
import { CalendarModule } from 'primeng/calendar';
import { BookingComponent } from './components/Management/Booking/booking.component';
import { BookingService } from './services/booking.service';
import { DialogModule } from 'primeng/dialog';
import { FileUploadModule } from 'primeng/fileupload';

@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
    LoginComponent,
    DashboardComponent,
    LayoutComponent,
    AccessDenied,
    CinemaComponent,
    RoomComponent,
    AddRoomComponent,
    EditRoomComponent,
    AccountComponent, 
    MovieComponent,
    AddMovieComponent,
    EditMovieComponent,
    PaymentComponent,
    SeatComponent,
    AddSeatComponent,
    SubComponent,
    AddSubComponent,
    ComboComponent,
    AddComboComponent,
    ShowtimeComponent,
    AddShowtimeComponent,
    EditShowtimeComponent,
    RatingComponent,
    BookingComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ToastModule,
    BrowserAnimationsModule,
    DropdownModule,
    RatingModule,
    CalendarModule,
    DialogModule,
    FileUploadModule,
  ],
  providers: [
    BaseUrlService,
    ChatService,
    DatePipe,
    CheckLoginService,
    AccountService,
    CinemaService,
    RoomService,
    MessageService,
    ConfirmationService,
    MovieService,
    PaymentService,
    SubService,
    SeatService,
    ComboService,
    ShowtimeService,
    RatingService,
    BookingService
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
