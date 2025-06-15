package com.cdw_ticket.showtime_service.repository;

import com.cdw_ticket.showtime_service.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, String> {
    @Query(
            value = "SELECT * FROM show_times WHERE DATE(start_time) = :date AND cinema_id = :cinemaId",
            nativeQuery = true
    )
    List<Showtime> findByStartDateAndCinemaId(@Param("date") LocalDate date, @Param("cinemaId") String cinemaId);

    @Query(
            value = "SELECT * " +
                    "FROM show_times " +
                    "WHERE DATE(start_time) = :date AND cinema_id = :cinemaId AND movie_id = :movieId",
            nativeQuery = true
    )
    List<Showtime> findByMovieIdAndDateAndCinemaId(
            @Param("date") LocalDate date,
            @Param("cinemaId") String cinemaId,
            @Param("movieId") String movieId);
}
