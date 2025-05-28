package com.cdw_ticket.showtime_service.dto.response;

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

    String posterUrl;

    String trailerUrl;

    String language;

    String subtitle;
}
