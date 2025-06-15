export enum ShowtimeStatus {
  AVAILABLE = 'AVAILABLE',
  UNAVAILABLE = 'UNAVAILABLE',
  // Thêm các trạng thái khác nếu có
}

export class Showtime {
  id: string;
  movieId: string;
  movieTitle: string;
  cinemaId: string;
  roomId: string;
  cinemaName: string;
  startTime: string; // hoặc: Date nếu bạn muốn parse về Date object
  endTime: string;
  price: number;
  status: ShowtimeStatus;
}
