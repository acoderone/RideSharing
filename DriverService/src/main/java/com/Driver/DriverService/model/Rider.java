package com.Driver.DriverService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private String name;
    private String password;
    private String license;
    @Enumerated(EnumType.STRING)
    private Status status=Status.AVAILABLE;
    private Double latitude;
    private Double longitude;
    @OneToMany(mappedBy = "rider",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips;
}
