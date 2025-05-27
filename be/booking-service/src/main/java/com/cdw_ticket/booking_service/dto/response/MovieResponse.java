package com.cdw_ticket.booking_service.dto.response;

import com.cdw_ticket.booking_service.enums.MovieStatus;
import com.cdw_ticket.booking_service.enums.Rating;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieResponse {
    String id;
    String title;

    String description;

    int duration;

    Date releaseDate;

    Set<String> genres;

    String director;

    List<String> casts;

    Rating rating;

    String posterUrl;

    String trailerUrl;

    String language;

    MovieStatus status;

    String subtitle;
}
