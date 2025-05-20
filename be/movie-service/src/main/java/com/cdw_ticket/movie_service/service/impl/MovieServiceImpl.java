package com.cdw_ticket.movie_service.service.impl;

import com.cdw_ticket.movie_service.dto.request.MovieRequest;
import com.cdw_ticket.movie_service.dto.response.MovieResponse;
import com.cdw_ticket.movie_service.entity.Genre;
import com.cdw_ticket.movie_service.entity.Movie;
import com.cdw_ticket.movie_service.exception.AppException;
import com.cdw_ticket.movie_service.exception.ErrorCode;
import com.cdw_ticket.movie_service.mapper.MovieMapper;
import com.cdw_ticket.movie_service.repository.MovieRepository;
import com.cdw_ticket.movie_service.service.GenreService;
import com.cdw_ticket.movie_service.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieServiceImpl implements MovieService {
    MovieRepository movieRepository;
    MovieMapper movieMapper;
    GenreService genreService;

    @Override
    public List<MovieResponse> getAll() {
        return movieRepository.findAll().stream()
                .map(movie -> {
                    MovieResponse response = movieMapper.toMovieResponse(movie);
                    response.setGenres(genreService.getStrGenres(movie.getGenres()));
                    return response;
                })
                .toList();
    }

    @Override
    public MovieResponse getById(String id) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));
        var response = movieMapper.toMovieResponse(movie);
        Set<String> genreNames = genreService.getStrGenres(movie.getGenres());
        response.setGenres(genreNames);
        return response;
    }

    @Override
    public MovieResponse create(MovieRequest request) {
        Movie movie = movieMapper.toMovie(request);

        Set<Genre> genres = genreService.getGenresByNames(request.getGenres());
        movie.setGenres(genres);

        movie = movieRepository.save(movie);

        var response = movieMapper.toMovieResponse(movie);
        Set<String> genreNames = genreService.getStrGenres(movie.getGenres());
        response.setGenres(genreNames);

        return response;
    }

    @Override
    public MovieResponse updateById(String id, MovieRequest request) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));

        movieMapper.update(movie, request);
        Set<Genre> genres = genreService.getGenresByNames(request.getGenres());
        movie.setGenres(genres);
        movieRepository.save(movie);

        var response = movieMapper.toMovieResponse(movie);
        Set<String> genreNames = genreService.getStrGenres(movie.getGenres());
        response.setGenres(genreNames);

        return response;
    }

    @Override
    public void deleteById(String id) {
        movieRepository.deleteById(id);
    }
}
