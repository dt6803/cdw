import { OnInit, Component } from '@angular/core';
import { Account } from 'src/app/models/account.model';
import { Cinema } from 'src/app/models/cinema.model';
import { AccountService } from 'src/app/services/account.service';
import { CinemaService } from 'src/app/services/cinema.service';



@Component({
  templateUrl: './account.component.html',
})
export class AccountComponent implements OnInit {

  constructor(
    private accountService: AccountService
    ) {}
  accounts: Account[];
  ngOnInit(): void {
    this.accountService.findAll().then(
      res => {
        console.log(res);
        this.accounts = res as Account[];

      }
    );
   
  }
}
