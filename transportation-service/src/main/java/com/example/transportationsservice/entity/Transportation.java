package com.example.transportationsservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transportations")
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "type")
    private String type;

    @Column(name = "cost")
    private double cost;

    @Column(name = "description")
    private String description;

    // Constructors
    public Transportation() {}

    public Transportation(String name, String city, String type, double cost, String description) {
        this.name = name;
        this.city = city;
        this.type = type;
        this.cost = cost;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}