package com.cdw_ticket.cinema_service.service.impl;

import com.cdw_ticket.cinema_service.dto.request.ComboRequest;
import com.cdw_ticket.cinema_service.dto.response.ComboResponse;
import com.cdw_ticket.cinema_service.exception.AppException;
import com.cdw_ticket.cinema_service.exception.ErrorCode;
import com.cdw_ticket.cinema_service.mapper.ComboMapper;
import com.cdw_ticket.cinema_service.repository.ComboRepository;
import com.cdw_ticket.cinema_service.service.ComboService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComboServiceImpl implements ComboService {
    ComboRepository comboRepository;
    ComboMapper comboMapper;


    @Override
    public ComboResponse create(ComboRequest request) {
        var combo = comboMapper.toCombo(request);
        return comboMapper.toComoResponse(comboRepository.save(combo));
    }

    @Override
    public List<ComboResponse> getAll() {
        return comboRepository.findAll().stream()
                .map(comboMapper::toComoResponse)
                .toList();
    }

    @Override
    public ComboResponse getById(String id) {
        var combo = comboRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMBO_NOT_EXISTED));
        return comboMapper.toComoResponse(comboRepository.save(combo));
    }

    @Override
    public void delete(String id) {
        comboRepository.deleteById(id);
    }
}
