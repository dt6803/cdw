package com.cdw_ticket.movie_service.mapper;

import com.cdw_ticket.movie_service.dto.request.GenreRequest;
import com.cdw_ticket.movie_service.dto.response.GenreResponse;
import com.cdw_ticket.movie_service.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre toGenre(GenreRequest request);
    GenreResponse toGenreResponse(Genre genre);
}
