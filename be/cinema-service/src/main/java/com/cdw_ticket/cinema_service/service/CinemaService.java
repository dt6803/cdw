package com.cdw_ticket.cinema_service.service;

import com.cdw_ticket.cinema_service.dto.request.CinemaRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaDetailResponse;
import com.cdw_ticket.cinema_service.dto.response.CinemaSimpleResponse;
import com.cdw_ticket.cinema_service.entity.Cinema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaService {
    CinemaSimpleResponse create(CinemaRequest request);
    List<CinemaSimpleResponse> getAll();
    CinemaSimpleResponse getSimpleInfo(String id);
    CinemaDetailResponse getDetailInfo(String id);
    CinemaSimpleResponse updateInfo(String id, CinemaRequest request);
    void delete(String id);
    Cinema getCinemaById(String id);
}
