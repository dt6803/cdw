
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
  rooms: Room[];
  cinemas: Cinema[];
  roomId: number;
  room: Room;
  ngOnInit(): void {

  }

  edit(){

  }
}
