import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ShowAPIService} from "src/app/services/showAPI.service";
import * as moment from "moment";
import {TicketAPIService} from "src/app/services/ticketAPI.service";
import {MessageService} from "primeng/api";
import {ShowTimeService} from "src/app/services/showTime.service";
import {ComboService} from "src/app/services/combo.service";
import {Combo, ComboDetails} from "src/app/models/combo.model";
import {BookingService} from "src/app/services/booking.service";
import {IPayPalConfig} from "ngx-paypal";
import {PaymentService} from "src/app/services/payment.service";
import {PaymentRequest} from "src/app/models/payment.model";
import {DatePipe} from "@angular/common";
import {Seat} from "../../models/seat.model";
import {Showtime} from "../../models/showtime.model";
import {MovieService} from "../../services/movie.service";
import {Movie} from "../../models/movie.model";

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
  selectedSeats1: string = '';
  orderId = Math.floor(100000 + Math.random() * 900000); // ID đơn hàng
  amount: number; // Số tiền cần thanh toán
  maxSelectedSeats: number = 10;
  todayDate: Date = new Date();
  showDetail: Showtime;
  showId: string;
  selectedSeat: { row: number, col: number } | null = null;
  selectedSeats: Set<string> = new Set();
  combos: Combo[];
  selectedCombo: Combo;
  comboNumber: number;
  ticketForm: FormGroup;
  comboId: number = -1;
  comboDetails: ComboDetails[];
  payPalConfig: IPayPalConfig;
  lastElementsArray = [];
  visible: boolean = false;
  total: number = 0;
  listSeatBooked = [];
  seatGrid: (Seat | null)[][] = [];
  roomId: string = '';
  movie: Movie;

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
    private movieService: MovieService
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




    this.checkShow();



    // this.comboService.findAll().then(
    //   (res) => {
    //     this.combos = res as Combo[];
    //
    //
    //   },
    //   (err) => {
    //     console.log(err);
    //   }
    // );
    this.ticketForm = this.formBuilder.group({
      name: ['', Validators.required], // Thêm Validators.required
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10,11}$')]], // Thêm Validators.required và pattern
      email: ['', [Validators.required, Validators.email]], // Thêm Validators.required và email
      showTimeId: this.showId
    });
    this.comboDetails = [];


  }

  // isSeatSelected(seat: string): boolean {
  //   return this.selectedSeats.has(seat);
  // }
  // Hàm xử lý khi nhấp vào ghế
  selectSeat(row: number, col: number): void {
    const rowLabels = ['A', 'B', 'C', 'D', 'E'];
    this.selectedSeat = {row, col};
    console.log(`Selected Seat: Row ${rowLabels[col]}, Column  ${row + 1}`);
  }

  // Get the CSS class based on seat status

  convertDateToString(date: Date) {
    const formattedDate = moment(date).format("DD/MM/YYYY");

    return formattedDate;
  }

  reloadCurrentRoute(): void {
    const currentUrl = this.router.url;
    this.router.navigateByUrl("/", {skipLocationChange: true}).then(() => {
      this.router.navigate([currentUrl]);
    });

  }

  toggleSeat(seatId: string): void {
    this.seatStatus[seatId] = !this.seatStatus[seatId];
    console.log('select seat: ', seatId);
    if (seatId.startsWith('H')) {
      const seatNumber = parseInt(seatId.substring(1), 10);
      const pairSeatNumber = this.doubleSeats.find(pair => pair.includes(seatNumber))?.find(seat => seat !== seatNumber);
      if (pairSeatNumber !== undefined) {
        this.seatStatus[`H${pairSeatNumber}`] = this.seatStatus[seatId];
      }
    }

    this.updateSelectedSeats();
  }

  isSeatSelected(seatId: string): boolean {
    // Trả về trạng thái hiện tại của ghế (đã đặt hoặc chưa)
    return this.seatStatus[seatId];
  }

  isBooked(seatBooked: string): boolean {
    return this.seatBooked[seatBooked];
  }

  getSelectedSeats(): string[] {
    const selectedSeats: string[] = [];

    Object.keys(this.seatStatus).forEach(seatId => {
      if (this.seatStatus[seatId]) {
        if (seatId.startsWith('H')) {
          const seatNumber = parseInt(seatId.substring(1), 10);
          const pairSeatNumber = this.doubleSeats.find(pair => pair.includes(seatNumber))?.find(seat => seat !== seatNumber);
          const pairSeatId = `H${pairSeatNumber}`;

          // Kiểm tra nếu cặp ghế đôi này chưa được thêm vào danh sách
          if (!selectedSeats.includes(`${seatId}-${pairSeatId}`) && !selectedSeats.includes(`${pairSeatId}-${seatId}`)) {
            selectedSeats.push(`${seatId}-${pairSeatId}`);
          }
        } else {
          selectedSeats.push(seatId);
        }
      }
    });

    return selectedSeats;
  }

  updateSelectedSeats(): void {
    this.selectedSeats1 = this.getSelectedSeats().join(', ');
  }

  selectCombo(evt: any) {
    console.log(evt.target.value);
    this.comboId = evt.target.value;
  }

  buyTicket() {

    if (this.ticketForm.valid && this.selectedSeats1 !== '') {
      this.visible = true;

      // Split the seats into an array and sort by row letters and seat numbers.
      let seats = this.selectedSeats1.split(', ');
      console.log("Các ghế đã đặt: ", seats);

      let seatsArray = seats.map(seat => {
        return {
          row: seat.charAt(0),
          number: parseInt(seat.slice(1), 10)
        };
      });

      seatsArray.sort((a, b) => {
        if (a.row === b.row) {
          return a.number - b.number;
        } else {
          return a.row.localeCompare(b.row);
        }
      });

      console.log("Sorted Seats Array: ", seatsArray);

      for (let i = 0; i < seatsArray.length - 1; i++) {
        if (seatsArray[i].row === seatsArray[i + 1].row) {
          let difference = seatsArray[i + 1].number - seatsArray[i].number;
          console.log("Difference: ", difference);
          if (difference >= 2) {
            this.visible = false;
            this.messageService.add({
              severity: "error",
              summary: "Đặt vé thất bại",
              detail: "Bạn vui lòng chọn ghế không cách quá 1 ghế. Vui lòng bạn chọn lại ghế"
            });
            return;
          }
        } else {
          this.visible = false;
          this.messageService.add({
            severity: "error",
            summary: "Đặt vé thất bại",
            detail: "Bạn vui lòng chọn ghế không cách quá 1 ghế. Vui lòng bạn chọn lại ghế"
          });
          return;
        }
      }

      if (seatsArray.length > this.maxSelectedSeats) {
        this.visible = false;
        this.messageService.add({
          severity: "error",
          summary: "Đặt vé thất bại",
          detail: "Chỉ cho phép đặt tối đa 10 ghế trong 1 lần đặt vé."
        });
        return;
      }

      seatsArray.forEach((seat, index) => {
        this.bookingService.findSeatByName(seat.row + seat.number).then(
          (res) => {
            console.log(res.price);
            this.total += res.price;
            console.log(this.total);
          },
          (err) => {
          }
        );
      });

      if (this.lastElementsArray.length > 0) {
        this.lastElementsArray.forEach((value) => {
          this.total += (value.quantity * value.price);
        });
      }

    } else {
      this.messageService.add({
        severity: "error",
        summary: "Đặt vé thất bại",
        detail: "Vui lòng nhập đủ thông tin"
      });
    }


  }

  onValueChange(evt: any, id: number, price: number) {
    var quantity = evt.target.value;

    if (quantity >= 0) {
      this.comboDetails.push({comboId: id, bookingId: null, quantity: quantity, price: price});
      console.log(this.comboDetails);

      const lastElementsMap = new Map<number, any>();
      this.comboDetails.forEach(element => {
        // Cập nhật hoặc thêm phần tử vào map với key là comboId
        lastElementsMap.set(element.comboId, element);
      });

      this.lastElementsArray = Array.from(lastElementsMap.values());

      // Loại bỏ các phần tử có quantity = 0
      this.lastElementsArray = this.lastElementsArray.filter(element => element.quantity > 0);

      console.log(this.lastElementsArray);
    }

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
