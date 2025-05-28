export class Payment {
  id: number;
  bookingID: number;
  paymentType: number;
  transactionNo: string;
  ticketNumber: number;
  QR: string;
  created: string;
  description: string;
  price: number;
  status:boolean;
}