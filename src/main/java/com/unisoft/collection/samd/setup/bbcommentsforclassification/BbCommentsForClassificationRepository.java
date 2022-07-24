package com.unisoft.collection.samd.setup.bbcommentsforclassification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BbCommentsForClassificationRepository extends JpaRepository<BbCommentsForClassification,Long> {


}
