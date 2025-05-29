import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Combo } from 'src/app/models/combo.model';
import { ComboService } from 'src/app/services/combo.service';

@Component({
  templateUrl: './add_combo.component.html',
})
export class AddComboComponent implements OnInit {
  constructor(
    private comboService: ComboService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router
  ) {}
  combos: Combo[];
  addComboForm: FormGroup;
  ngOnInit(): void {
    this.addComboForm = this.formBuilder.group({
      name: ['', Validators.required],
      price: ['', Validators.required],
    });
    this.comboService.findAll().then((res) => {
      this.combos = res as Combo[];
    });
  }

  add() {
    if (this.addComboForm.valid) {
      console.log(this.addComboForm.value);
      var combo = this.addComboForm.value as Combo;
      this.comboService.create(combo).then(
        (res) => {
          console.log(res);
          this.messageService.add({
            severity: 'success',
            summary: 'Thành công',
            detail: 'Thêm combo thành công',
          });
          setTimeout(() => {
            this.router.navigate(['/admin/combo']);
          }, 2000);
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Lỗi',
            detail: 'Thêm combo thất bại',
          });
        }
      );
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Lỗi',
        detail: 'Vui lòng nhập đủ thông tin',
      });
    }
  }
}
