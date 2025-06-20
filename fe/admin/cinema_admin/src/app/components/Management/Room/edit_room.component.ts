
import { OnInit, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Cinema } from 'src/app/models/cinema.model';
import { Room } from 'src/app/models/room.model';
import { CinemaService } from 'src/app/services/cinema.service';
import { RoomService } from 'src/app/services/room.service';



@Component({
  templateUrl: './edit_room.component.html',
  styleUrls: ['./edit_room.component.css'],
})
export class EditRoomComponent implements OnInit {
  editRoomForm: FormGroup;
  constructor(
    private roomService: RoomService,
    private cinemaService: CinemaService,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute
    ) {}

  roomId: string;
  room: any = {
    name: '',
    type: '',
    capacity: 0
  };

  seatLayout = [];
  seatGrid: any[][] = [];
  ngOnInit(): void {
    this.roomId = this.route.snapshot.paramMap.get('roomId')!;
    this.roomService.findById(this.roomId).then(
      (res) => {
        if (res.status === 'Success')
        {
          this.room = res.data;
        }
      }
    )
    this.roomService.getSeatLayout(this.roomId).then(
      (res) => {
        console.log('seat layout: ', res)
        this.seatLayout = res.data.seats;
        this.buildSeatGrid();
      }
    );
  }

  buildSeatGrid() {
    const maxRow = Math.max(...this.seatLayout.map(seat => seat.rowNumber));
    const maxCol = Math.max(...this.seatLayout.map(seat => seat.colNumber));
    // const maxRow = 20
    // const maxCol = 15
    // Khởi tạo ma trận trống
    const grid = Array.from({ length: maxRow }, () =>
      Array.from({ length: maxCol }, () => null)
    );

    for (const seat of this.seatLayout) {
      grid[seat.rowNumber - 1][seat.colNumber - 1] = seat;
    }

    this.seatGrid = grid;
    console.log("seat grid: ", this.seatGrid)
  }

  edit(){

  }

  saveRoom(id: string) {
    const payload = {
      name: this.room.name,
      type: this.room.type,
      capacity: this.room.capacity,
      cinemaId: this.room.cinemaId
    };

    console.log('Saving room with payload:', payload);

    this.roomService.updateRoom(id, payload).then(
      (res) => {
        console.log('Lưu phòng thành công:', res);

      },
      (err) => {
        console.error('Lỗi khi lưu phòng:', err);
      }
    );
  }


  resetRoom() {
    const seatTypes = ['NORMAL', 'VIP', 'COUPLE'];
    const seatPrices: { [key: string]: number } = {
      NORMAL: 50000,
      VIP: 80000,
      COUPLE: 120000
    };
    const seatPerRow = 15

    const rows = Math.ceil(this.room.capacity / seatPerRow); // Giả sử mỗi hàng có 10 ghế
    const seats = [];
    let seatIndex = 0;

    for (let row = 0; row < rows; row++) {
      for (let col = 0; col < seatPerRow; col++) {
        if (seatIndex >= this.room.capacity) break;

        const seatType = seatTypes[seatIndex % seatTypes.length];
        const seatPrice = seatPrices[seatType];
        const seatCode = String.fromCharCode(65 + row) + (col + 1); // A1, A2, B1...

        seats.push({
          seatCode: seatCode,
          rowNumber: row + 1,
          colNumber: col + 1,
          type: seatType,
          price: seatPrice,
          status: 'AVAILABLE'
        });

        seatIndex++;
      }
    }

    const payload = {
      roomId: this.room.id,
      seats: seats
    };

    console.log('Reset payload:', payload);

    this.roomService.createSeatLayout(this.roomId, payload).then(
      (res) => {
        if (res.status === 'Success')
          console.log('cresate seat alout')
      }

    )
  }

}
