package com.cdw_ticket.showtime_service.service;

import com.cdw_ticket.showtime_service.dto.request.ShowtimeRequest;
import com.cdw_ticket.showtime_service.dto.response.ShowtimeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShowtimeService {
    ShowtimeResponse create(ShowtimeRequest request);
    List<ShowtimeResponse> getAll();
    ShowtimeResponse getById(String id);
    ShowtimeResponse updateById(String id, ShowtimeRequest request);
    void delete(String id);
}
