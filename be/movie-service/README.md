### 🎬 Movie API Endpoints

| Method | Endpoint                      | Mô tả                         |
|--------|-------------------------------|-------------------------------|
| GET    | `/api/movies`                 | Lấy danh sách phim (có phân trang, lọc) |
| GET    | `/api/movies/{id}`            | Lấy chi tiết phim             |
| POST   | `/api/movies`                 | Thêm phim mới (Admin)         |
| PUT    | `/api/movies/{id}`            | Cập nhật phim (Admin)         |
| DELETE | `/api/movies/{id}`            | Xóa phim (Admin)              |
| GET    | `/api/movies/search`          | Tìm kiếm phim                 |
| GET    | `/api/movies/now-showing`     | Phim đang chiếu               |
| GET    | `/api/movies/coming-soon`     | Phim sắp chiếu                |

### 🕒 Showtime API Endpoints

| Method | Endpoint                                | Mô tả                          |
|--------|------------------------------------------|--------------------------------|
| GET    | `/api/showtimes`                         | Lấy tất cả lịch chiếu         |
| POST   | `/api/showtimes`                         | Tạo lịch chiếu mới (Admin)    |
| GET    | `/api/showtimes/movie/{movieId}`         | Lịch chiếu theo phim          |
| GET    | `/api/showtimes/cinema/{cinemaId}`       | Lịch chiếu theo rạp           |
| GET    | `/api/showtimes/date/{date}`             | Lịch chiếu theo ngày          |
| PUT    | `/api/showtimes/{id}`                    | Cập nhật lịch chiếu (Admin)   |
| DELETE | `/api/showtimes/{id}`                    | Xóa lịch chiếu (Admin)        |

### 🎭 Genre API Endpoints
| Method | Endpoint           | Mô tả                     |
|--------|--------------------|---------------------------|
| GET    | `/api/genres`      | Lấy tất cả thể loại       |
| POST   | `/api/genres`      | Thêm thể loại mới (Admin) |
| DELETE | `/api/genres`      | Xóa thể loại (Admin)      |

### 🎭 Person API Endpoints
| Method | Endpoint       | Mô tả                   |
|--------|----------------|-------------------------|
| GET    | `/api/persons` | Lấy tất cả person       |
| POST   | `/api/persons` | Thêm thể person (Admin) |
| DELETE | `/api/persons` | Xóa person (Admin)      |
