package com.cdw_ticket.cinema_service.service.impl;

import com.cdw_ticket.cinema_service.dto.request.CinemaBrandRequest;
import com.cdw_ticket.cinema_service.dto.response.CinemaBrandResponse;
import com.cdw_ticket.cinema_service.exception.AppException;
import com.cdw_ticket.cinema_service.exception.ErrorCode;
import com.cdw_ticket.cinema_service.mapper.CinemaBrandMapper;
import com.cdw_ticket.cinema_service.repository.CinemaBrandRepository;
import com.cdw_ticket.cinema_service.service.CinemaBrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaBrandServiceImpl implements CinemaBrandService {
    CinemaBrandRepository cinemaBrandRepository;
    CinemaBrandMapper cinemaBrandMapper;

    @Override
    public CinemaBrandResponse create(CinemaBrandRequest request) {
        var brand = cinemaBrandMapper.toCinemaBrand(request);
        cinemaBrandRepository.save(brand);
        return cinemaBrandMapper.toCinemaBrandResponse(brand);
    }

    @Override
    public List<CinemaBrandResponse> getAll() {
        return cinemaBrandRepository.findAll()
                .stream()
                .map(cinemaBrandMapper::toCinemaBrandResponse)
                .toList();
    }

    @Override
    public CinemaBrandResponse updateById(String id, CinemaBrandRequest request) {
        var brand = cinemaBrandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        cinemaBrandMapper.update(brand, request);
        cinemaBrandRepository.save(brand);
        return cinemaBrandMapper.toCinemaBrandResponse(brand);
    }

    @Override
    public void delete(String id) {
        cinemaBrandRepository.deleteById(id);
    }
}
