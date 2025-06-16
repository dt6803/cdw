package com.cdw_ticket.cinema_service.service;

import com.cdw_ticket.cinema_service.dto.request.ComboRequest;
import com.cdw_ticket.cinema_service.dto.response.ComboResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComboService {
    ComboResponse create(ComboRequest request);
    List<ComboResponse> getAll();
    ComboResponse getById(String id);
    void delete(String id);
}
