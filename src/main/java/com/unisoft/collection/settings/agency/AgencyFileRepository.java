package com.unisoft.collection.settings.agency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyFileRepository extends JpaRepository<AgencyFile, Long> {
}
