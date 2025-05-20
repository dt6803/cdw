package com.cdw_ticket.movie_service.mapper;

import com.cdw_ticket.movie_service.dto.request.MovieRequest;
import com.cdw_ticket.movie_service.dto.response.MovieResponse;
import com.cdw_ticket.movie_service.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "genres", ignore = true)
    Movie toMovie(MovieRequest request);
    @Mapping(target = "genres", ignore = true)
    MovieResponse toMovieResponse(Movie movie);
    @Mapping(target = "genres", ignore = true)
    void update(@MappingTarget Movie movie, MovieRequest request);
}
