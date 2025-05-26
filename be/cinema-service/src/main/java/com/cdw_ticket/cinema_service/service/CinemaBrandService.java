package com.cdw_ticket.cinema_service.service;

import com.cdw_ticket.cinema_service.dto.request.CinemaBrandRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaBrandResponse;
import com.cdw_ticket.cinema_service.entity.CinemaBrand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaBrandService {
    CinemaBrandResponse create(CinemaBrandRequest request);
    List<CinemaBrandResponse> getAll();
    CinemaBrandResponse updateById(String id, CinemaBrandRequest request);
    void delete(String id);
    CinemaBrand findById(String id);
}
