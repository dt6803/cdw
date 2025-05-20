package com.cdw_ticket.movie_service.service.impl;

import com.cdw_ticket.movie_service.dto.request.GenreRequest;
import com.cdw_ticket.movie_service.dto.response.GenreResponse;
import com.cdw_ticket.movie_service.entity.Genre;
import com.cdw_ticket.movie_service.mapper.GenreMapper;
import com.cdw_ticket.movie_service.repository.GenreRepository;
import com.cdw_ticket.movie_service.service.GenreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreServiceImpl implements GenreService {
    GenreRepository genreRepository;
    GenreMapper genreMapper;


    @Override
    public GenreResponse create(GenreRequest request) {
        var genre = genreMapper.toGenre(request);
        genreRepository.save(genre);
        return genreMapper.toGenreResponse(genre);
    }

    @Override
    public List<GenreResponse> getAll() {
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::toGenreResponse)
                .toList();
    }

    @Override
    public void delete(String id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Set<Genre> getGenresByNames(Set<String> names) {
        return genreRepository.findByNameIn(names);
    }

    @Override
    public Set<String> getStrGenres(Set<Genre> genres) {
        if (genres == null) return Collections.emptySet();
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.toSet());
    }
}
