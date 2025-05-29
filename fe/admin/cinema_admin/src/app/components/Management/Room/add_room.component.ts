import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Cinema } from 'src/app/models/cinema.model';
import { Room } from 'src/app/models/room.model';
import { CinemaService } from 'src/app/services/cinema.service';
import { RoomService } from 'src/app/services/room.service';



@Component({
  templateUrl: './add_room.component.html',
})
export class AddRoomComponent implements OnInit {
  addRoomForm: FormGroup;
  constructor(
    private roomService: RoomService,
    private cinemaService: CinemaService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router
    ) {}
  rooms: Room[];
  cinemas: Cinema[];

  ngOnInit(): void {
    this.addRoomForm = this.formBuilder.group({
      name: ['', Validators.required],
      cinemaId: [1, Validators.required],
      quantity: [0,[Validators.required, Validators.min(1)]],
     
    });
    this.roomService.findAll().then(
      res => {
        this.rooms = res as Room[];
      }
    );
    this.cinemaService.findAll().then(
      res => {
        this.cinemas = res as Cinema[];
      }
    );
   
  
  }

  add(){
    if(this.addRoomForm.valid){
      var room = this.addRoomForm.value as Room;
      this.roomService.create(room).then(
        res => {
      
          console.log(res);
            this.messageService.add({
              severity: "success",
              summary: "Thành công",
              detail: "Thêm phòng thành công"
            });
            setTimeout(() => {
              this.router.navigate(['/admin/room']);
            }, 2000);
        },
        err => {
          this.messageService.add({
            severity: "error",
            summary: "Lỗi",
            detail: "Thêm phòng thất bại"
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
