package com.HE180030.repository;

import com.HE180030.model.QuantitiesSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantitiesSoldRepository extends JpaRepository<QuantitiesSold, Integer> {
}
