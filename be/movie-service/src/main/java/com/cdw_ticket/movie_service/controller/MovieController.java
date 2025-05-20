package com.cdw_ticket.movie_service.controller;

import com.cdw_ticket.movie_service.dto.request.MovieRequest;
import com.cdw_ticket.movie_service.dto.response.BaseResponse;
import com.cdw_ticket.movie_service.dto.response.MovieResponse;
import com.cdw_ticket.movie_service.service.MovieService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/movies")
@Slf4j
public class MovieController {
    MovieService movieService;

    @GetMapping
    public BaseResponse<List<MovieResponse>> getAll() {
        return BaseResponse.<List<MovieResponse>>builder()
                .data(movieService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public BaseResponse<MovieResponse> getById(@PathVariable String id) {
        return BaseResponse.<MovieResponse>builder()
                .data(movieService.getById(id))
                .build();
    }

    @PostMapping
    public BaseResponse<MovieResponse> create(@Valid @RequestBody MovieRequest request) {
        log.info(request.getDirector());
        return BaseResponse.<MovieResponse>builder()
                .data(movieService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public BaseResponse<MovieResponse> updateById(
            @PathVariable String id,
            @Valid @RequestBody MovieRequest request
    ) {
        return BaseResponse.<MovieResponse>builder()
                .data(movieService.updateById(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(@PathVariable String id) {
        movieService.deleteById(id);
        return BaseResponse.<Void>builder()
                .message("Delete successfully!")
                .build();
    }
}
