package com.cdw_ticket.showtime_service.service.impl;

import com.cdw_ticket.showtime_service.dto.request.ShowtimeRequest;
import com.cdw_ticket.showtime_service.dto.response.ShowtimeResponse;
import com.cdw_ticket.showtime_service.exception.AppException;
import com.cdw_ticket.showtime_service.exception.ErrorCode;
import com.cdw_ticket.showtime_service.mapper.ShowtimeMapper;
import com.cdw_ticket.showtime_service.repository.ShowtimeRepository;
import com.cdw_ticket.showtime_service.service.ShowtimeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowtimeServiceImpl implements ShowtimeService {
    ShowtimeRepository showtimeRepository;
    ShowtimeMapper showtimeMapper;

    @Override
    public ShowtimeResponse create(ShowtimeRequest request) {
        var showtime = showtimeMapper.toShowtime(request);
        showtimeRepository.save(showtime);
        return showtimeMapper.toShowtimeResponse(showtime);
    }

    @Override
    public List<ShowtimeResponse> getAll() {
        return showtimeRepository.findAll()
                .stream()
                .map(showtimeMapper::toShowtimeResponse)
                .toList();
    }

    @Override
    public ShowtimeResponse getById(String id) {
        var showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SHOWTIME_NOT_FOUND));
        return showtimeMapper.toShowtimeResponse(showtime);
    }

    @Override
    public ShowtimeResponse updateById(String id, ShowtimeRequest request) {
        var showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SHOWTIME_NOT_FOUND));
        showtimeMapper.update(showtime, request);
        showtimeRepository.save(showtime);
        return showtimeMapper.toShowtimeResponse(showtime);
    }

    @Override
    public void delete(String id) {
        showtimeRepository.deleteById(id);
    }
}
