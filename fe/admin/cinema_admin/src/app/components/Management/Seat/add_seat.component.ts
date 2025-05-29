import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Cinema } from 'src/app/models/cinema.model';
import { Room } from 'src/app/models/room.model';
import { Seat } from 'src/app/models/seat.model';

import { RoomService } from 'src/app/services/room.service';
import { SeatService } from 'src/app/services/seat.service';




@Component({
  templateUrl: './add_seat.component.html',
})
export class AddSeatComponent implements OnInit {
  constructor(
    private roomService: RoomService,
    private seatService: SeatService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router
    ) {}
  rooms: Room[];
  
  seats:Seat[];
  addSeatForm:FormGroup;
  ngOnInit(): void {
    this.addSeatForm = this.formBuilder.group({
      name: ['', Validators.required],
      roomId: [1, Validators.required],
      seatType: [1, Validators.required],
      price: ['', Validators.required],
    });
    this.seatService.findAll().then(
      res => {
        this.seats = res as Seat[];
      }
    );
    this.roomService.findAll().then(
      res => {
        this.rooms = res as Room[];
      }
    );
    
  }

  add(){
    if(this.addSeatForm.valid){
      console.log(this.addSeatForm.value);
      var seat = this.addSeatForm.value as Seat;
      this.seatService.create(seat).then(
        res => {
      
          console.log(res);
            this.messageService.add({
              severity: "success",
              summary: "Thành công",
              detail: "Thêm ghế thành công"
            });
            setTimeout(() => {
              this.router.navigate(['/admin/seat']);
            }, 2000);
        },
        err => {
          this.messageService.add({
            severity: "error",
            summary: "Lỗi",
            detail: "Thêm ghế thất bại"
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
}
