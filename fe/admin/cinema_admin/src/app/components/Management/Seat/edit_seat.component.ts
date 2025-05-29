// import { OnInit, Component } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { ActivatedRoute, Router } from '@angular/router';
// import { MessageService } from 'primeng/api';
// import { Cinema } from 'src/app/models/cinema.model';
// import { Room } from 'src/app/models/room.model';
// import { Seat } from 'src/app/models/seat.model';
// import { CinemaService } from 'src/app/services/cinema.service';
// import { RoomService } from 'src/app/services/room.service';
// import { SeatService } from 'src/app/services/seat.service';

// @Component({
//   templateUrl: './edit_seat.component.html',
// })
// export class EditSeatComponent implements OnInit {
//   editSeatForm: FormGroup;
//   constructor(
//     private roomService: RoomService,
//     private cinemaService: CinemaService,
//     private formBuilder: FormBuilder,
//     private messageService: MessageService,
//     private router: Router,
//     private route: ActivatedRoute,
//     private seatService: SeatService,
//   ) {}
//   rooms: Room[];
//   cinemas: Cinema[];
//   roomId: number;
//   room: Room;
//   seats: Seat[];
//   seat: Seat;
//   seatId: number;
//   ngOnInit(): void {
//     this.route.params.subscribe((params) => {
//       const seatId = params['seatId'];
//       this.seatId = seatId;
//       console.log(this.seatId);
//     });
//     this.seatService.findById(this.seatId).then((res) => {
//       this.seat = res as Seat;
//       console.log(this.seat);
//       this.editSeatForm = this.formBuilder.group({
//         name: [this.seat.name, Validators.required],
//       });
//     });
//     this.editSeatForm = this.formBuilder.group({
//       name: ['', Validators.required],
//     });
//     this.seatService.findAll().then((res) => {
//       this.seats = res as Seat[];
//     });
//     this.cinemaService.findAll().then((res) => {
//       this.cinemas = res as Cinema[];
//     });
//   }

//   edit() {
//     if (this.editSeatForm.valid) {
//       var seat = this.editSeatForm.value as Seat;
//       seat.id = this.seat.id;
//       this.seatService.edit(seat).then(
//         (res) => {
//           console.log(res);
//           this.messageService.add({
//             severity: 'success',
//             summary: 'Thành công',
//             detail: 'Sửa phòng thành công',
//           });
//           setTimeout(() => {
//             this.router.navigate(['/admin/seat']);
//           }, 2000);
//         },
//         (err) => {
//           this.messageService.add({
//             severity: 'error',
//             summary: 'Lỗi',
//             detail: 'Sửa phòng thất bại',
//           });
//         }
//       );
//     } else {
//       this.messageService.add({
//         severity: 'error',
//         summary: 'Lỗi',
//         detail: 'Vui lòng nhập đủ thông tin',
//       });
//     }
//   }
// }
