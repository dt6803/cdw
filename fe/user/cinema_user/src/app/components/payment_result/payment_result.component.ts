import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {AppComponent} from "../../app.component";
import {BookingService} from "../../services/booking.service";

@Component({
  selector: 'app-payment-result',
  templateUrl: './payment-result.component.html',
  styleUrls: ['./payment-result.component.css']
})
export class PaymentResultComponent implements OnInit {
  isSuccess: boolean = true;
  orderCode: string = '';
  bankCode: string = '';
  amount: number = 0;
  paymentDate: string = '';
  responseCode: string = '';
  currentUser: string = '';

  constructor(
    private route: ActivatedRoute,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.responseCode = params['vnp_ResponseCode'];
      this.currentUser = localStorage.getItem("accessToken");
      this.orderCode = params['vnp_TxnRef'] || '';
      this.bankCode = params['vnp_BankCode'] || '';
      this.amount = Number(params['vnp_Amount']) / 100 || 0;
      this.paymentDate = this.formatPaymentDate(params['vnp_PayDate']);
      this.bookingService.getById(this.orderCode).then(
        (res) => {
          if (res.data.status === 'COMPLETED' && this.responseCode === '00') {
            this.isSuccess = true;
          }
        }
      );
    });
  }

  private formatPaymentDate(dateString: string): string {
    if (!dateString || dateString.length !== 14) return '';
    const year = dateString.substring(0, 4);
    const month = dateString.substring(4, 6);
    const day = dateString.substring(6, 8);
    const hour = dateString.substring(8, 10);
    const minute = dateString.substring(10, 12);
    const second = dateString.substring(12, 14);
    return `${day}/${month}/${year} ${hour}:${minute}:${second}`;
  }
}
