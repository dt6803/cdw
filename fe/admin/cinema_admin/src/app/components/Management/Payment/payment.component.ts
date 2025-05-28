import { MessageService } from 'primeng/api';
import { OnInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { Payment } from 'src/app/models/payment.model';
import { PaymentService } from 'src/app/services/payment.service';

@Component({
  templateUrl: './payment.component.html',
})
export class PaymentComponent implements OnInit {

  payments: Payment[] = [];
  sortedPayments: Payment[] = [];
  currentSortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(
    private paymentService: PaymentService,
    private messageService: MessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.paymentService.findAll().then(
      res => {
        this.payments = res as Payment[];
        this.sortedPayments = [...this.payments]; // Sao chép dữ liệu ban đầu để sử dụng cho sắp xếp
      }
    );
  }

  sortTable(column: string): void {
    if (this.currentSortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.currentSortColumn = column;
      this.sortDirection = 'asc';
    }

    this.sortedPayments.sort((a, b) => {
      let comparison = 0;

      if (a[column] > b[column]) {
        comparison = 1;
      } else if (a[column] < b[column]) {
        comparison = -1;
      }

      return this.sortDirection === 'asc' ? comparison : -comparison;
    });
  }

  getSortIcon(column: string): string {
    if (this.currentSortColumn === column) {
      return this.sortDirection === 'asc' ? 'bx bx-up-arrow-alt' : 'bx bx-down-arrow-alt';
    }
    return 'bx bx-sort';
  }
}

