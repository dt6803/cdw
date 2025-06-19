import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ShowAPIService} from "src/app/services/showAPI.service";
import {TicketAPIService} from "src/app/services/ticketAPI.service";
import {MessageService} from "primeng/api";
import {ShowTimeService} from "src/app/services/showTime.service";
import {ComboService} from "src/app/services/combo.service";
import {Combo} from "src/app/models/combo.model";
import {BookingService} from "src/app/services/booking.service";
import {IPayPalConfig} from "ngx-paypal";
import {PaymentService} from "src/app/services/payment.service";
import {PaymentRequest} from "src/app/models/payment.model";
import {DatePipe} from "@angular/common";
import {Seat} from "../../models/seat.model";
import {Showtime} from "../../models/showtime.model";
import {MovieService} from "../../services/movie.service";
import {Movie} from "../../models/movie.model";
import {AccountService} from "../../services/account.service";
import {jwtDecode} from "jwt-decode";

@Component({
  templateUrl: "./buy_ticket.component.html",
  styleUrls: ['./ticket-booking.component.css']
})
export class BuyTicketComponent {
  maxRow = 20;
  maxCol = 20;
  rows: string[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];
  seats: number[] = Array.from({length: this.maxCol}, (_, i) => i + 1);
  doubleSeats: number[][] = [[1, 2], [3, 4], [5, 6], [7, 8], [9, 10], [11, 12], [13, 14]];

  // Lưu trạng thái của các ghế, khởi tạo tất cả là chưa được đặt (false)
  seatStatus: { [key: string]: boolean } = {};
  seatBooked: { [key: string]: boolean } = {};
  selectedSeatStr: string = '';
  orderId = Math.floor(100000 + Math.random() * 900000); // ID đơn hàng
  amount: number; // Số tiền cần thanh toán
  maxSelectedSeats: number = 10;
  todayDate: Date = new Date();
  showDetail: Showtime;
  showId: string;
  selectedSeats: Seat[] = [];
  combos: Combo[];
  selectedCombos: Combo[];
  quantities: { [comboId: string]: number } = {};
  ticketForm: FormGroup;
  comboId: number = -1;
  payPalConfig: IPayPalConfig;
  lastElementsArray = [];
  visible: boolean = false;
  total: number = 0;
  listSeatBooked = [];
  seatGrid: (Seat | null)[][] = [];
  roomId: string = '';
  movie: Movie;
  userId: '';

  constructor(
    private route: ActivatedRoute,
    private showAPIService: ShowAPIService,
    private formBuilder: FormBuilder,
    private ticketAPIService: TicketAPIService,
    private messageService: MessageService,
    private router: Router,
    private showTimeService: ShowTimeService,
    private comboService: ComboService,
    private bookingService: BookingService,
    private paymentService: PaymentService,
    private datePipe: DatePipe,
    private movieService: MovieService,
    private accountService: AccountService,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      const showIdParam = params["showId"];
      this.showId = showIdParam;
    });

    this.showTimeService.findById(this.showId).then(
      (res) => {
        this.showDetail = res.data as Showtime
        console.log("Show Detail: ", this.showDetail);
        this.roomId = this.showDetail.roomId;
        console.log("Room ID: ", this.roomId);
        this.movieService.findMovieById(this.showDetail.movieId).then(
          (res) => {
            this.movie = res.data as Movie;
            console.log("Movie Detail: ", this.movie.genres);
          }
        )

        this.showTimeService.getSeatLayout(this.roomId).then(
          (res) => {
            console.log("Seat Layout: ", res.data)
            const rawSeats: Seat[] = this.removeDuplicates(res.data.seats);
            // this.maxRow = Math.max(...rawSeats.map(s => s.rowNumber));
            // this.maxCol = Math.max(...rawSeats.map(s => s.colNumber));
            this.maxRow = 10; // Số hàng cố định
            this.maxCol = 20; // Số cột cố định
            // Khởi tạo grid
            this.seatGrid = Array.from({length: this.maxRow}, () =>
              Array.from({length: this.maxCol}, () => null)
            );

            rawSeats.forEach(seat => {
              this.seatGrid[seat.rowNumber - 1][seat.colNumber - 1] = seat;
            });
            console.log("Seat Grid: ", this.seatGrid);
          },
          (err) => {
            console.log(err);
          }
        );
      },
      (err) => {
        console.log(err);
      }
    );

    this.comboService.findAll().then(
      (res) => {
        this.combos = res.data as Combo[];
        console.log('fetch combo: ', this.combos);
      },
      (err) => {
        console.log(err);
      }
    )


    this.checkShow();

    this.ticketForm = this.formBuilder.group({
      name: ['', Validators.required], // Thêm Validators.required
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10,11}$')]], // Thêm Validators.required và pattern
      email: ['', [Validators.required, Validators.email]], // Thêm Validators.required và email
      paymentMethod: ['VNPAY', Validators.required]
    });
    // this.comboDetails = [];


  }

  reloadCurrentRoute(): void {
    const currentUrl = this.router.url;
    this.router.navigateByUrl("/", {skipLocationChange: true}).then(() => {
      this.router.navigate([currentUrl]);
    });

  }

  toggleSeat(aSeat: Seat): void {
    const index = this.selectedSeats.findIndex(seat => seat.id === aSeat.id);

    if (index > -1) {

      this.selectedSeats.splice(index, 1);
    } else {

      this.selectedSeats.push(aSeat);
    }

    console.log('Toggled seat:', aSeat.id + ' - ' + aSeat.seatCode);
    this.updateSelectedSeatStr();
  }

  isSeatSelected(seatCode: string): boolean {
    return this.selectedSeats.some(seat => seat.seatCode === seatCode);
  }

  isBooked(seatBooked: string): boolean {
    return this.seatBooked[seatBooked];
  }


  updateSelectedSeatStr(): void {
    this.selectedSeatStr = this.selectedSeats.map(seat => seat.seatCode).join(',');
  }

  selectCombo(evt: any) {
    console.log(evt.target.value);
    this.comboId = evt.target.value;
  }

  buyTicket() {
    //Kiểm tra user
    const token = localStorage.getItem("accessToken");


    if (!token) {
      this.ticketForm.markAllAsTouched();
      this.messageService.add({
        severity: 'warn',
        summary: 'Chưa đăng nhập',
        detail: 'Vui lòng đăng nhập.'
      });
      this.router.navigate(['/login']);
      return;
    }
    const decoded: any = jwtDecode(token);
    this.userId = decoded.sub;

    // Kiểm tra form
    if (this.ticketForm.invalid) {
      this.ticketForm.markAllAsTouched();
      this.messageService.add({
        severity: 'warn',
        summary: 'Lỗi xác thực',
        detail: 'Vui lòng nhập đầy đủ thông tin trước khi thanh toán.'
      });
      return;
    }

    // Lấy danh sách ID ghế
    const seatIds = this.selectedSeats.map(seat => seat.id);

    if (seatIds.length === 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Chưa chọn ghế',
        detail: 'Vui lòng chọn ít nhất một ghế để tiếp tục.'
      });
      return;
    }

    console.log('form value: ', this.ticketForm.value);
    // Tạo payload gửi đi
    const payload = {
      ...this.ticketForm.value,
      seatIds: seatIds,
      showtimeId: this.showId,
      userId: this.userId,
      total: this.getTotalPrice()
    };

    console.log("Sending ticket data:", payload)

    this.bookingService.create(payload).then((res) => {
      console.log('Booking response:', res);
      let email = '';
      let fullname = '';
      if (res.status === 'Success' && res.data.urlPayment) {

        this.accountService.getProfileByUserId(res.data.userId).then(
          (res) => {
            fullname = res.data.fullName;
            email = res.data.email;
          }
        );

        this.bookingService.sendMailConfirm({
          "to": {
            "name": fullname,
            "email": email

          },
          "subject": "T Cinema Booking Confirmation",
          "htmlContent": "booking-confirmation",
          "data": {
            "movieTitle": res.data.movieTitle,
            "showtime": res.data.showtime,
            "createdAt": res.data.createdAt,
            "totalPrice": res.data.totalPrice,
            "qrCodeBase64": res.data.qrCodeBase64
          }
        }).then(r => {
          console.log('send mail')
        });

        // Chuyển trang đến link thanh toán
        //window.location.href = res.data.urlPayment;
      } else {
        // Thông báo lỗi nếu không có urlPayment hoặc status không success
        this.messageService.add({
          severity: 'error',
          summary: 'Thanh toán thất bại',
          detail: res.data.message || 'Không thể xử lý thanh toán. Vui lòng thử lại.',
        });
      }
    }).catch((err) => {
      // Bắt lỗi nếu request thất bại
      this.messageService.add({
        severity: 'error',
        summary: 'Lỗi hệ thống',
        detail: 'Không thể kết nối máy chủ. Vui lòng thử lại sau.',
      });
    });


  }


  getTotalPrice(): number {
    let comboTotal = 0;

    for (const combo of this.combos) {
      const quantity = this.quantities[combo.id] || 0;
      comboTotal += quantity * combo.price;
    }

    const seatTotal = this.selectedSeats.reduce((sum, seat) => sum + seat.price, 0);

    return comboTotal + seatTotal;
  }

  adjustQuantity(comboId: string, delta: number) {
    const currentQty = this.quantities[comboId] || 0;
    const newQty = Math.max(currentQty + delta, 0);
    this.quantities[comboId] = newQty;
    console.log('quantity: ', this.quantities[comboId]);
  }


  checkShow() {
    this.showTimeService.findById(this.showId).then(
      (res) => {
        if (res.showDate < this.datePipe.transform(this.todayDate, 'dd/MM/yyyy')) {
          this.router.navigate(['/cinema'], {queryParams: {status: false}});
        } else {

        }

      },
      (err) => {
        console.error(err);
      }
    );
  }


  onPay() {
    const amount = this.total;
    const orderInfo = this.orderId;
    const returnUrl = 'http://localhost:4200/home';

    const paymentRequest: PaymentRequest = {
      amount: Number(amount),
      orderInfo: orderInfo.toString(),
      returnUrl: returnUrl
    };

    this.paymentService.vnpay(paymentRequest).then(response => {
      console.log('Response:', response);

      if (response && response.PaymentUrl) {
        console.log('Redirecting to:', response.PaymentUrl);
        window.location.assign(response.PaymentUrl); // Chuyển hướng đến VNPay
      } else {
        console.error('Payment URL is undefined or invalid');
      }
    }).catch(error => {
      console.error('Error while initiating payment:', error);
    });
  }

  removeDuplicates(seats: Seat[]): Seat[] {
    const map = new Map<string, Seat>();
    seats.forEach(seat => {
      const key = `${seat.rowNumber}-${seat.colNumber}`;
      if (!map.has(key)) {
        map.set(key, seat);
      }
    });
    return Array.from(map.values());
  }

  getRowLabel(index: number): string {
    return String.fromCharCode(65 + index); // A, B, C,...
  }
}
