
import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Cinema } from 'src/app/models/cinema.model';
import { Room } from 'src/app/models/room.model';
import { CinemaService } from 'src/app/services/cinema.service';
import { RoomService } from 'src/app/services/room.service';



@Component({
  templateUrl: './edit_room.component.html',
})
export class EditRoomComponent implements OnInit {
  editRoomForm: FormGroup;
  constructor(
    private roomService: RoomService,
    private cinemaService: CinemaService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute
    ) {}
  rooms: Room[];
  cinemas: Cinema[];
  roomId: number;
  room: Room;
  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const roomId = params["roomId"];
      this.roomId = roomId;
      console.log(this.roomId);
    });
    this.roomService.findById(this.roomId).then(
      res => {
        this.room = res as Room;
        console.log(this.room);
        this.editRoomForm = this.formBuilder.group({
          name: [this.room.name, Validators.required],
          cinemaId: [this.room.cinemaId, Validators.required],
          quantity: [this.room.quantity,[Validators.required, Validators.min(1)]],
         
        });

      }
    );
    this.editRoomForm = this.formBuilder.group({
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

  edit(){
    if(this.editRoomForm.valid){
      var room = this.editRoomForm.value as Room;
      room.id = this.room.id;
      this.roomService.edit(room).then(
        res => {
      
          console.log(res);
            this.messageService.add({
              severity: "success",
              summary: "Thành công",
              detail: "Sửa phòng thành công"
            });
            setTimeout(() => {
              this.router.navigate(['/admin/room']);
            }, 2000);
        },
        err => {
          this.messageService.add({
            severity: "error",
            summary: "Lỗi",
            detail: "Sửa phòng thất bại"
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
