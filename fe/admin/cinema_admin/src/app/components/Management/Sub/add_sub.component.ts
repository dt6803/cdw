import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Sub } from 'src/app/models/sub.model';
import { SubService } from 'src/app/services/sub.service';



@Component({
  templateUrl: './add_sub.component.html',
})
export class AddSubComponent implements OnInit {
  constructor(
    private subService: SubService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router
    ) {}
  subs:Sub[];
  addSubForm:FormGroup;
  ngOnInit(): void {
    this.addSubForm = this.formBuilder.group({
      name: ['', Validators.required],
      created: ["", Validators.required],
    });
    this.subService.findAll().then(
      res => {
        this.subs = res as Sub[];
      }
    );
    this.subService.findAll().then(
      res => {
        this.subs = res as Sub[];
      }
    );
    
  }

  add(){
    if(this.addSubForm.valid){
      console.log(this.addSubForm.value);
      var sub = this.addSubForm.value as Sub;
      this.subService.create(sub).then(
        res => {
      
          console.log(res);
            this.messageService.add({
              severity: "success",
              summary: "Thành công",
              detail: "Thêm ghế thành công"
            });
            setTimeout(() => {
              this.router.navigate(['/admin/sub']);
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
