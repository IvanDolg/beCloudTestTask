package com.testproject.decloudtesttask.repository;

import com.testproject.decloudtesttask.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
}
