package com.cdw_ticket.payment_service.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Recipient {
    String name;
    String email;
    @Override
    public String toString() {
        return "Recipient{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
