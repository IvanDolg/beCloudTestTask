package com.testproject.decloudtesttask.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "individuals")
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;
}
