package com.unisoft.collection.samd.dataEntry.justification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificationRepository extends JpaRepository<Justification, Long> {
    List<Justification> findAllByAccountNumber(String accountNumber);

}
