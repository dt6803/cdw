import { OnInit, Component } from '@angular/core';

import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Combo } from 'src/app/models/combo.model';
import { ComboService } from 'src/app/services/combo.service';

@Component({
  templateUrl: './combo.component.html',
})
export class ComboComponent implements OnInit {
  constructor(
    private comboService: ComboService,
    private messageService: MessageService,
    private router: Router
  ) {}
  combos: Combo[];
  ngOnInit(): void {
    this.comboService.findAll().then((res) => {
      this.combos = res as Combo[];
    });
  }
  confirmDelete(comboId: number): void {
    if (confirm('Bạn có chắc muốn xóa?')) {
      this.delete(comboId);
    }
  }
  confirmUpdate(comboId: number): void {
    if (confirm('Bạn có chắc muốn sửa?')) {
      this.update(comboId);
    }
  }
  update(comboId: number) {
    this.router.navigate(['/admin/edit-combo', comboId]);
  }
  delete(id: number) {
    console.log(id);
    this.comboService.delete(id).then(
      (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Thành công',
          detail: 'Xoá phòng thành công',
        });
        this.comboService.findAll().then((res) => {
          this.combos = res as Combo[];
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
