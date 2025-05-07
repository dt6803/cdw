package com.cdw_ticket.movie_service.repository;

import com.cdw_ticket.movie_service.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
}
