package com.unisoft.collection.settings.loanreferenceinfo;

import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanCustomerReferenceInfoSettingsRepository extends JpaRepository<ReferenceInfoEntity,Long> {

    List<ReferenceInfoEntity> findByLoanAccountNo(String loanAccountNo);
    ReferenceInfoEntity findByAccountNoAndLoanAccountNo(String accountNo,String loanAccountNo);
    List<ReferenceInfoEntity>findByStatus(ReferenceInfoStatus status);
}
