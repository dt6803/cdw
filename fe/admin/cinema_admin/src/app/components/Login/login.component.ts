import { OnInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AccountService } from 'src/app/services/account.service';



// @ts-ignore
@Component({
  styleUrls: ['./login.component.css'],
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  constructor(
    private accountService: AccountService,
    private router: Router,
    private messageService: MessageService
    ) {}

  ngOnInit(): void {

  }

}
