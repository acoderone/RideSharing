package com.Driver.DriverService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String start;
    private String end;
    private Double price;
    private String origin;
    private String destination;
    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;
}
