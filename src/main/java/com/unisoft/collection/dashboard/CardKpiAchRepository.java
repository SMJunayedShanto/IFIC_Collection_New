package com.unisoft.collection.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CardKpiAchRepository extends JpaRepository<CardKpiAchEntity, Long> {
}
