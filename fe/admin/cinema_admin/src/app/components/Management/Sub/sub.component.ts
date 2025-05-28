import { OnInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Sub } from 'src/app/models/sub.model';
import { SubService } from 'src/app/services/sub.service';

@Component({
  templateUrl: './sub.component.html',
})
export class SubComponent implements OnInit {
  constructor(
    private subService: SubService,
    private messageService: MessageService,
    private router: Router
  ) {}
  subs: Sub[];
  ngOnInit(): void {
    this.subService.findAll().then((res) => {

      this.subs = res as Sub[];
    });
  }
  confirmDelete(subId: number): void {
    if (confirm('Bạn có chắc muốn xóa?')) {
      this.delete(subId);
    }
  }
  confirmUpdate(subId: number): void {
    if (confirm('Bạn có chắc muốn sửa?')) {
      this.update(subId);
    }
  }
  update(subId: number) {
    this.router.navigate(['/admin/edit-sub', subId]);
  }
  delete(id: number) {
    this.subService.delete(id).then(
      (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Thành công',
          detail: 'Xoá phòng thành công',
        });
        this.subService.findAll().then((res) => {
          this.subs = res as Sub[];
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
