import { Combo } from "./combo.model";
import { Seat } from "./seat.model";

export class Booking{
    id: number;
    email: string;
    phone: string;
    name: string;
    countTicket: number;
    countCombo: number;
    created: string;
    movie: string;
}

export class BookingDetails{
    showTime: string;
    cinema: string;
    movie: string;
    sub: string;
    room: string;
    bookingDetails: Seat1[];
    comboDetails: Combo1[];
}

export class Seat1{
    seat: string;
}
export class Combo1{
    combo: string;
    quantity: number;
}