package com.unisoft.retail.card.dataEntry.ptp;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CardPtpRepository extends JpaRepository<CardPtp,Long> {

    List<CardPtp> findByEnabledIs(boolean a);

    List<CardPtp> findByEnabledIsAndCustomerBasicInfoOrderByCreatedDateDesc(boolean a, CustomerBasicInfoEntity customerBasicInfoEntity);
    
    @Query(nativeQuery = true)
    List<PtpStatusSummary> getPtpSummaryByDealer(@Param("dealerPin") String dealerPin);
    
    @Query(nativeQuery = true)
    List<PtpStatusSummary> getPtpSummaryBySupervisor(@Param("supervisorPin") String supervisorPin);
    
    @Query(nativeQuery = true)
    List<PtpStatusSummary> getPtpSummaryByTeamLeader(@Param("teamLeaderPin") String teamLeaderPin);
    
    @Query(nativeQuery = true)
    List<PtpStatusSummary> getPtpSummaryByManager(@Param("managerPin") String managerPin);

    @Query(value = "SELECT LP.PIN                                  DEALER_PIN, " +
            "       MAX(U.FIRST_NAME || ' ' || U.LAST_NAME) DEALER_NAME, " +
            "       SUM(LP.KEPT_COUNT)                      KEPT_COUNT, " +
            "       COALESCE(SUM(LP.KEPT_SUM), 0)           KEPT_SUM, " +
            "       SUM(LP.BROKEN_COUNT)                    BROKEN_COUNT, " +
            "       COALESCE(SUM(LP.BROKEN_SUM), 0)         BROKEN_SUM, " +
            "       SUM(LP.CURED_COUNT)                     CURED_COUNT, " +
            "       COALESCE(SUM(LP.CURED_SUM), 0)          CURED_SUM " +
            "FROM (SELECT CUSTOMER_ID, CARD_AMOUNT, LOWER(CARD_PTP_STATUS) LOAN_PTP_STATUS, PIN " +
            "      FROM CARD_PTP " +
            "      WHERE TRUNC(CARD_PTP_DATE, 'DD') >= TRUNC(SYSDATE, 'MM') " +
            "        AND PIN IN (:dealerPins)) " +
            "         PIVOT ( " +
            "           COUNT(DISTINCT CUSTOMER_ID) COUNT, SUM(CARD_AMOUNT) SUM " +
            "         FOR LOAN_PTP_STATUS IN ('kept' KEPT, 'broken' BROKEN, 'cured' CURED) " +
            "         ) LP " +
            "       JOIN LOS_TB_M_USERS U ON U.USERNAME = LP.PIN " +
            "GROUP BY LP.PIN", nativeQuery = true)
    List<Tuple> getDealerWisePtpSummary(@Param("dealerPins") List<String> dealerPins);

    @Query(value = "SELECT * FROM CARD_PTP CP WHERE CP.CUSTOMER_ID=?1 AND CP.CARD_PTP_DATE BETWEEN cast(?2 as date) AND cast(?3 as date) ", nativeQuery = true)
    List<CardPtp> getCardPtpByCustomerIdAndDateRange(String customerId, String startDate, String endDate);

}
