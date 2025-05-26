package com.cdw_ticket.cinema_service.service.impl;

import com.cdw_ticket.cinema_service.dto.request.CinemaRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaDetailResponse;
import com.cdw_ticket.cinema_service.dto.response.CinemaSimpleResponse;
import com.cdw_ticket.cinema_service.entity.Cinema;
import com.cdw_ticket.cinema_service.exception.AppException;
import com.cdw_ticket.cinema_service.exception.ErrorCode;
import com.cdw_ticket.cinema_service.mapper.CinemaMapper;
import com.cdw_ticket.cinema_service.repository.CinemaRepository;
import com.cdw_ticket.cinema_service.service.CinemaBrandService;
import com.cdw_ticket.cinema_service.service.CinemaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaServiceImpl implements CinemaService {
    CinemaRepository cinemaRepository;
    CinemaMapper cinemaMapper;
    CinemaBrandService brandService;
    @Override
    public CinemaSimpleResponse create(CinemaRequest request) {
        var cinema = cinemaMapper.toCinema(request);
        var brand = brandService.findById(request.getBrandId());
        cinema.setBrand(brand);
        cinemaRepository.save(cinema);
        return cinemaMapper.toCinemaSimpleResponse(cinema);
    }

    @Override
    public List<CinemaSimpleResponse> getAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(cinemaMapper::toCinemaSimpleResponse)
                .toList();
    }

    @Override
    public CinemaSimpleResponse getSimpleInfo(String id) {
        var cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_EXISTED));
        return cinemaMapper.toCinemaSimpleResponse(cinema);
    }

    @Override
    public CinemaDetailResponse getDetailInfo(String id) {
        var cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_EXISTED));
        return cinemaMapper.toCinemaDetailResponse(cinema);
    }

    @Override
    public CinemaSimpleResponse updateInfo(String id, CinemaRequest request) {
        var cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_EXISTED));
        var brand = brandService.findById(request.getBrandId());
        cinema.setBrand(brand);
        cinemaMapper.updateInfo(cinema, request);
        cinemaRepository.save(cinema);
        return cinemaMapper.toCinemaSimpleResponse(cinema);
    }

    @Override
    public void delete(String id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    public Cinema getCinemaById(String id) {
        return cinemaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_EXISTED));
    }
}
