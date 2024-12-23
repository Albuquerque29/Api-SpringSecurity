package com.example.Projeto.Back.End.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table (name = "product_tb")
@Data

public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private double value;

}