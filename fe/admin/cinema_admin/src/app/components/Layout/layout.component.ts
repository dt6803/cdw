import { OnInit, Component } from '@angular/core';
import { Router } from '@angular/router';



@Component({
  templateUrl: './layout.component.html',
})
export class LayoutComponent implements OnInit {
  constructor(
    private router: Router
  ){}
  open: boolean;
 username: string = localStorage.getItem("username");
  ngOnInit(): void {
    this.open = false
   
  }
  menu(){
    this.open = !this.open;
  }
  logout(){
    localStorage.removeItem("username");
    this.router.navigate(['/login']);
  }
}
