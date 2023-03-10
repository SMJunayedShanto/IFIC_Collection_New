package com.unisoft.cardprofile.cardOtherAccountInfo;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardOtherAccountInfoRepository extends JpaRepository<CardOtherAccountInfo, Long> {
    List<CardOtherAccountInfo> findByCustomerBasicInfoEntity(CustomerBasicInfoEntity customerBasicInfoEntity);
}
