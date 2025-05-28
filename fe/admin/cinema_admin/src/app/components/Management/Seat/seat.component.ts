import { OnInit, Component } from '@angular/core';

import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Seat } from 'src/app/models/seat.model';
import { SeatService } from 'src/app/services/seat.service';

@Component({
  templateUrl: './seat.component.html',
})
export class SeatComponent implements OnInit {
  constructor(
    private seatService: SeatService,
    private messageService: MessageService,
    private router: Router
  ) {}
  seats: Seat[];
  ngOnInit(): void {
    this.seatService.findAll().then((res) => {
      console.log(res);
      this.seats = res as Seat[];
    });
  }
  confirmDelete(seatId: number): void {
    if (confirm('Bạn có chắc muốn xóa?')) {
      this.delete(seatId);
    }
  }
  confirmUpdate(seatId: number): void {
    if (confirm('Bạn có chắc muốn sửa?')) {
      this.update(seatId);
    }
  }
  update(seatId: number) {
    this.router.navigate(['/admin/edit-seat', seatId]);
  }
  delete(id: number) {
    console.log(id);
    this.seatService.delete(id).then(
      
      (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Thành công',
          detail: 'Xoá phòng thành công',
        });
        this.seatService.findAll().then((res) => {
          this.seats = res as Seat[];
        });
      },
      (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Lỗi',
          detail: 'Xóa phòng thất bại',
        });
      }
    );
  }
}
