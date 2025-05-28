package com.cdw_ticket.showtime_service.service.impl;

import com.cdw_ticket.showtime_service.client.CinemaClient;
import com.cdw_ticket.showtime_service.client.MovieClient;
import com.cdw_ticket.showtime_service.dto.request.ShowtimeRequest;
import com.cdw_ticket.showtime_service.dto.response.CinemaSimpleResponse;
import com.cdw_ticket.showtime_service.dto.response.MovieResponse;
import com.cdw_ticket.showtime_service.dto.response.ShowtimeResponse;
import com.cdw_ticket.showtime_service.entity.Showtime;
import com.cdw_ticket.showtime_service.exception.AppException;
import com.cdw_ticket.showtime_service.exception.ErrorCode;
import com.cdw_ticket.showtime_service.mapper.ShowtimeMapper;
import com.cdw_ticket.showtime_service.repository.ShowtimeRepository;
import com.cdw_ticket.showtime_service.service.ShowtimeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShowtimeServiceImpl implements ShowtimeService {
    ShowtimeRepository showtimeRepository;
    ShowtimeMapper showtimeMapper;
    MovieClient movieClient;
    CinemaClient cinemaClient;

    @Override
    public ShowtimeResponse create(ShowtimeRequest request) {
        var showtime = showtimeMapper.toShowtime(request);
        showtimeRepository.save(showtime);
        var response = buildFullShowtimeResponse(showtime);
        return response;
    }

    @Override
    public List<ShowtimeResponse> getAll() {
        return showtimeRepository.findAll().stream()
                .map(this::buildFullShowtimeResponse)
                .toList();
    }

    @Override
    public ShowtimeResponse getById(String id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SHOWTIME_NOT_FOUND));

        var response = buildFullShowtimeResponse(showtime);
        return response;
    }


    @Override
    public ShowtimeResponse updateById(String id, ShowtimeRequest request) {
        var showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SHOWTIME_NOT_FOUND));
        showtimeMapper.update(showtime, request);
        showtimeRepository.save(showtime);
        var response = buildFullShowtimeResponse(showtime);
        return response;
    }

    @Override
    public void delete(String id) {
        showtimeRepository.deleteById(id);
    }

    private ShowtimeResponse buildFullShowtimeResponse(Showtime showtime) {
        ShowtimeResponse response = showtimeMapper.toShowtimeResponse(showtime);

        MovieResponse movie = movieClient.getMovieById(showtime.getMovieId()).getData();
        response.setMovieTitle(movie.getTitle());

        CinemaSimpleResponse cinema = cinemaClient.getSimpleInfo(showtime.getCinemaId()).getData();
        response.setCinemaName(cinema.getName());
        return response;
    }

}
