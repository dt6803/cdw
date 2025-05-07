package com.cdw_ticket.movie_service.mapper;

import com.cdw_ticket.movie_service.dto.request.MovieRequest;
import com.cdw_ticket.movie_service.dto.response.MovieResponse;
import com.cdw_ticket.movie_service.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toMovie(MovieRequest request);
    MovieResponse toMovieResponse(Movie movie);
    void update(@MappingTarget Movie movie, MovieRequest request);
}
