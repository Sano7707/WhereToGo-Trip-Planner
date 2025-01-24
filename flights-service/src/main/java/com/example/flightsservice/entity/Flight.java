package com.example.flightsservice.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure") // Use double quotes for case-sensitive column names
    private String departure;

    @Column(name = "destination") // Use double quotes for case-sensitive column names
    private String destination;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "cost")
    private double cost;


    public Flight(String departure, String destination, LocalDate date, double cost) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.cost = cost;
    }


    public Flight() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}