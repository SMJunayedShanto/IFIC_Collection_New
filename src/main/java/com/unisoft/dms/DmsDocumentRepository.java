package com.unisoft.dms;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DmsDocumentRepository extends JpaRepository<DmsDocumentUpload, Long> {

    public List<DmsDocumentUpload> findByCustomerBasicInfoEntity(CustomerBasicInfoEntity customerBasicInfoEntity);
}
