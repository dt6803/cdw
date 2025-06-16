export class BookingRequest {
  userId!: string;
  showtimeId!: string;
  seatIds!: string[];
  paymentMethod!: 'VNPAY' | 'MOMO';  // Ràng buộc chỉ cho phép 2 loại
}
