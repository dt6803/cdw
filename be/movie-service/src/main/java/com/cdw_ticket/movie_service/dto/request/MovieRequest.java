package com.cdw_ticket.movie_service.dto.request;

import com.cdw_ticket.movie_service.entity.Genre;
import com.cdw_ticket.movie_service.entity.Person;
import com.cdw_ticket.movie_service.enums.MovieStatus;
import com.cdw_ticket.movie_service.enums.Rating;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieRequest {
    @NotNull(message = "Title must not be null")
    String title;

    String description;
    @NotNull(message = "Duration must not be null")
    int duration;

    Date releaseDate;

    Set<Genre> genres;

    Person director;

    Set<Person> casts;

    Rating rating;

    String posterUrl;

    String trailerUrl;

    String language;

    MovieStatus status;

    String subtitle;
}
