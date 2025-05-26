package com.cdw_ticket.cinema_service.entity;

import com.cdw_ticket.cinema_service.enums.CinemaStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "address", nullable = false)
    String address;
    @Column(name = "city")
    String city;
    @ManyToOne
    CinemaBrand brand;
    @Column(name = "description")
    String description;
    @Column(name = "image_url")
    String imageUrl;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    List<CinemaRoom> rooms = new ArrayList<>();
}
