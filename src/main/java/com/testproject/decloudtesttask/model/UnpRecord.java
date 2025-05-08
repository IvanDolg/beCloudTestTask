package com.testproject.decloudtesttask.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unp_records")
public class UnpRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String unp;
}
