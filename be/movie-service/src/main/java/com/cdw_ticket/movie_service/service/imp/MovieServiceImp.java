package com.cdw_ticket.movie_service.service.imp;

import com.cdw_ticket.movie_service.dto.request.MovieRequest;
import com.cdw_ticket.movie_service.dto.response.MovieResponse;
import com.cdw_ticket.movie_service.exception.AppException;
import com.cdw_ticket.movie_service.exception.ErrorCode;
import com.cdw_ticket.movie_service.mapper.MovieMapper;
import com.cdw_ticket.movie_service.repository.MovieRepository;
import com.cdw_ticket.movie_service.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieServiceImp implements MovieService {
    MovieRepository movieRepository;
    MovieMapper movieMapper;


    @Override
    public List<MovieResponse> getAll() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse getById(String id) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public MovieResponse create(MovieRequest request) {
        var movie = movieMapper.toMovie(request);
        movie = movieRepository.save(movie);
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public MovieResponse updateById(String id, MovieRequest request) {
        var movie = movieRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));
        movieMapper.update(movie, request);
        movieRepository.save(movie);
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public void deleteById(String id) {
        movieRepository.deleteById(id);
    }
}
