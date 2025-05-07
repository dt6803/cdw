package com.cdw_ticket.movie_service.service;

import com.cdw_ticket.movie_service.dto.request.MovieRequest;
import com.cdw_ticket.movie_service.dto.response.MovieResponse;
import com.cdw_ticket.movie_service.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    List<MovieResponse> getAll();
    MovieResponse getById(String id);
    MovieResponse create(MovieRequest request);
    MovieResponse updateById(String id, MovieRequest request);
    void deleteById(String id);
}
