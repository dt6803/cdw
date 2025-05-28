import { MessageService } from 'primeng/api';
import { OnInit, Component } from '@angular/core';
import { Cinema } from 'src/app/models/cinema.model';
import { Room } from 'src/app/models/room.model';
import { CinemaService } from 'src/app/services/cinema.service';
import { RoomService } from 'src/app/services/room.service';
import { Router } from '@angular/router';



@Component({
  templateUrl: './room.component.html',
})
export class RoomComponent implements OnInit {

  constructor(
    private roomService: RoomService,
    private messageService: MessageService,
    private router: Router
    ) {}
  rooms: Room[];
  ngOnInit(): void {
    this.roomService.findAll().then(
      res => {
        this.rooms = res as Room[];
      }
    );
   
  }
  confirmDelete(roomId: number): void {
    if (confirm('Bạn có chắc muốn xóa?')) {
      this.delete(roomId);
    }
  }
  confirmUpdate(roomId: number): void {
    if (confirm('Bạn có chắc muốn sửa?')) {
     this.update(roomId);
    }
  }
  update(roomId: number){
    this.router.navigate(['/admin/edit-room', roomId]);
  }
  delete(id: number){
    this.roomService.delete(id).then(
      res => {
        this.messageService.add({
          severity: "success",
          summary: "Thành công",
          detail: "Xoá phòng thành công"
        });
        this.roomService.findAll().then(
          res => {
            this.rooms = res as Room[];
          }
        );
      }
      ,
      err => {
        this.messageService.add({
          severity: "error",
          summary: "Lỗi",
          detail: "Xóa phòng thất bại"
        });
      }
    );
  }
}
