import { OnInit, Component } from '@angular/core';
import { Cinema } from 'src/app/models/cinema.model';
import { CinemaService } from 'src/app/services/cinema.service';
import {RoomService} from "../../../services/room.service";
import {Router} from "@angular/router";



// @ts-ignore
@Component({
  styleUrls: ['./cinema.component.css'],
  templateUrl: './cinema.component.html',
})
export class CinemaComponent implements OnInit {

  constructor(
    private cinemaService: CinemaService,
    private roomService: RoomService,
    private router: Router
    ) {}
  cinemas: Cinema[];
  displayEditDialog = false;
  selectedCinema: any = {};
  rooms: any[];
  ngOnInit(): void {
    this.cinemaService.findAll().then(
      res => {
        this.cinemas = res.data as Cinema[];
        console.log(res)
      }
    );

  }

  openEditDialog(cinema: any) {
    this.selectedCinema = { ...cinema }; // clone tránh sửa trực tiếp
    this.displayEditDialog = true;

    console.log('selected: ', this.selectedCinema)
    this.roomService.findRoomInfoByCinemaId(this.selectedCinema.id).then(
      (res) => {
        if (res.status === 'Success') {
          this.rooms = res.data
        }
      }

    )
  }

  showAddRoomPopup() {

  }

  editRoom(roomId: string) {
    console.log('edit room: ' , roomId)
    this.router.navigate(['admin/edit-room', roomId]);
  }


  saveCinema() {
    if (this.selectedCinema.id) {
      console.log('update')
      const payload = {
        name: this.selectedCinema.name,
        address: this.selectedCinema.address,
        city: this.selectedCinema.city,
        brandId: this.selectedCinema.brand.id,
        description: this.selectedCinema.description,
        imageUrl: this.selectedCinema.imageUrl
      };

      console.log('update room payliad: ', payload)
      this.cinemaService.updateCinema(this.selectedCinema.id, payload).then(
        (res) => {
          if (res.status === 'Success') {
            this.displayEditDialog = false;
            this.refreshCinemaList();
          } else {
            console.log('updated failed')
          }
        }
      );
    } else {
      const payload = {
        name: this.selectedCinema.name,
        address: this.selectedCinema.address,
        city: this.selectedCinema.city,
        brandId: '056a7571-dec0-4007-8b61-63f5f18bb12f',
        description: 'Rạp chiếu phim CGV nằm trong trung tâm thương mại Aeon Mall, có phòng chiếu IMAX và 4DX hiện đại.',
        imageUrl: 'https://eexample.com/'
      };
      this.cinemaService.createCinema(payload).then(
        (res) => {
          if (res.status === 'Success') {
            this.displayEditDialog = false;
            this.refreshCinemaList();
          } else {
            console.log('create failed')
          }
        }
      )
    }
  }

  refreshCinemaList() {
    this.cinemaService.findAll().then(res => {
      this.cinemas = res.data as Cinema[];
    }).catch(err => {
      console.error('Không thể làm mới danh sách rạp:', err);
    });
  }

  openCreateCinemaDialog() {
    this.selectedCinema = {
      name: '',
      city: '',
      address: '',
      brandId: '',
      description: '',
      imageUrl: ''
    };
    this.rooms = []; // Mặc định chưa có phòng
    this.displayEditDialog = true;
  }

}
