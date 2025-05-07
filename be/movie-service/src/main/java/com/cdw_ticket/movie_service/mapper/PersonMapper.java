package com.cdw_ticket.movie_service.mapper;

import com.cdw_ticket.movie_service.dto.request.PersonRequest;
import com.cdw_ticket.movie_service.dto.response.PersonResponse;
import com.cdw_ticket.movie_service.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toPerson(PersonRequest request);
    PersonResponse toPersonResponse(Person person);
}
