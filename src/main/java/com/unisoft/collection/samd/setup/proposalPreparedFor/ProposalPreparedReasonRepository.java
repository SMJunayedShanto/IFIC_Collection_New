package com.unisoft.collection.samd.setup.proposalPreparedFor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalPreparedReasonRepository extends JpaRepository<ProposalPreparedReason, Long> {
}
