package com.cdw_ticket.movie_service.service;

import com.cdw_ticket.movie_service.dto.request.GenreRequest;
import com.cdw_ticket.movie_service.dto.response.GenreResponse;
import com.cdw_ticket.movie_service.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface GenreService {
    GenreResponse create(GenreRequest request);
    List<GenreResponse> getAll();
    void delete(String id);
    Set<Genre> getGenresByNames(Set<String> names);
    Set<String> getStrGenres(Set<Genre> genres);
}
