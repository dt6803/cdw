package com.cdw_ticket.cinema_service.controller;

import com.cdw_ticket.cinema_service.dto.request.ComboRequest;
import com.cdw_ticket.cinema_service.dto.response.BaseResponse;
import com.cdw_ticket.cinema_service.dto.response.ComboResponse;
import com.cdw_ticket.cinema_service.service.ComboService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/combos")
public class ComboController {
    ComboService comboService;

    @PostMapping()
    public BaseResponse<ComboResponse> create(@RequestBody ComboRequest request) {
        return BaseResponse.<ComboResponse>builder()
                .data(comboService.create(request))
                .build();
    }

    @GetMapping
    public BaseResponse<List<ComboResponse>> getAll() {
        return BaseResponse.<List<ComboResponse>>builder()
                .data(comboService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<ComboResponse> getById(@PathVariable String id) {
        return BaseResponse.<ComboResponse>builder()
                .data(comboService.getById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(@PathVariable String id) {
        comboService.delete(id);
        return BaseResponse.<Void>builder()
                .message("Delete successfully!")
                .build();
    }
}
