package com.cdw_ticket.movie_service.repository;

import com.cdw_ticket.movie_service.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
}
