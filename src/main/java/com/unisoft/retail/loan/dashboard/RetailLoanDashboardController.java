package com.unisoft.retail.loan.dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionSummary;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.customerloanprofile.followup.*;
import com.unisoft.customerloanprofile.loanpayment.DealerWisePayment;
import com.unisoft.customerloanprofile.loanpayment.LoanPaymentSummaryModel;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.loan.dashboard.esau.LoanPerformanceAndEsauTrendDataModel;
import com.unisoft.retail.loan.dashboard.esau.LoanPerformanceAndEsauTrendService;
import com.unisoft.retail.loan.dashboard.kpi.LoanKpiTargetVsAchievement;
import com.unisoft.retail.loan.dashboard.kpi.LoanKpiTargetVsAchievementSevrice;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import com.unisoft.retail.loan.dataEntry.ptp.DealerWisePtpSummary;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtpService;
import com.unisoft.retail.loan.dataEntry.ptp.PtpStatusSummary;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import com.unisoft.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created at 01 April, 2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/retail/loan/dashboard/")
public class RetailLoanDashboardController {

    private final RetailLoanDashboardService dashboardService;
    private final FollowUpService followUpService;
    private final LoanPtpService loanPtpService;
    private final RetailLoanUcbApiService retailLoanUcbApiService;
    private final LoanAccountDistributionService loanAccountDistributionService;
    private final LoanKpiTargetVsAchievementSevrice kpiTargetVsAchievementSevrice;
    private final LoanPerformanceAndEsauTrendService performanceAndEsauTrendService;
    private final DateUtils dateUtils;
    @Autowired
    private AccountInformationService accountInformationService;
    @Autowired
    private  EmployeeService employeeService;
    @Autowired
    private PeopleAllocationLogicService peopleAllocationLogicService;

    @Autowired
    private FollowUpRepository followUpRepository;

    @ResponseBody
    @GetMapping(value = "account-details")
    public List<LoanAccountDistributionSummary> getLoanAccountDetails(@RequestParam("dealerPin") String dealerPin) {
        List<LoanAccountDistributionSummary> loanAccountDetails=loanAccountDistributionService.getCurrentMonthLoanDistributionSummaryForDealer(dealerPin);
//        return loanAccountDistributionService.getCurrentMonthLoanDistributionSummaryForDealer(dealerPin);
        return loanAccountDetails;
    }

    @GetMapping(value = "followupbydate")
    public Map getDateWiseFollowUpSummary(@RequestParam(value = "startDate") String startDate,
                                          @RequestParam(value = "endDate") String endDate,
                                          @RequestParam(value = "userId") String userId,
                                          HttpSession session) {

        String sDate = dateUtils.changeStringDatePattern(startDate,"yyyy-MM-dd","dd-MMM-yyyy");
        String eDate = dateUtils.changeStringDatePattern(endDate,"yyyy-MM-dd","dd-MMM-yyyy");
        Map map = new HashMap();

//        List<FollowUpEntity> followups = new ArrayList<>();
//        List<Tuple> tuples = followUpRepository.findByPinAndFollowUpDateIsBetween(userId,sDate,eDate);
//        for(Tuple t : tuples){
//            followups.add(new FollowUpEntity(t));
//        }
//
//        map.put("loanFollowup", followUpRepository.getLoanFollowUpByCusBasicInfoDateWise());

        List<FollowUpEntity> followUpList = new ArrayList<>();
        List<LoanAccountDistributionInfo> distributionInfos =
                (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        if (distributionInfos == null) {
            map.put("loanFollowup", followUpList);
            return map;
        }

        for (LoanAccountDistributionInfo distributionInfo : distributionInfos) {
            Long customerId = distributionInfo.getLoanAccountBasicInfo().getCustomer().getId();
            List<FollowUpEntity> followups = dashboardService.getLoanFollowUpByCusBasicInfoDateWise(customerId, userId,startDate,endDate);
            for (FollowUpEntity follow : followups) {
                follow.setOutstanding(distributionInfo.getOutStanding());
                follow.setAccNo(distributionInfo.getAccountNo());
                followUpList.add(follow);
//                if(follow.getFollowUpDate().after(dateUtils.getFormattedDate(startDate,"yyyy-MM-dd"))
//                && !dateUtils.getFormattedDate(endDate,"yyyy-MM-dd").after(follow.getFollowUpDate())){
//
//                }
            }
        }
        map.put("loanFollowup", followUpList);

        return map;
    }

    @GetMapping(value = "date-wise-follow-up")
    public Map getDateWiseFollowUp(String userId, HttpSession session) {
        Map map = new HashMap();
        List<FollowUpEntity> followUpList = new ArrayList<>();
        List<LoanAccountDistributionInfo> distributionInfos =
                (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        if (distributionInfos == null) {
            map.put("loanFollowup", followUpList);
            return map;
        }

        for (LoanAccountDistributionInfo distributionInfo : distributionInfos) {
            Long customerId = distributionInfo.getLoanAccountBasicInfo().getCustomer().getId();
            List<FollowUpEntity> followups = dashboardService.getLoanFollowUpByCusBasicInfo(customerId, userId);
            for (FollowUpEntity follow : followups) {
                if(dateUtils.getFormattedDate(new Date(),"yyyy-MM-dd").compareTo(follow.getFollowUpDate()) == 0){
                    follow.setOutstanding(distributionInfo.getOutStanding());
                    follow.setAccNo(distributionInfo.getAccountNo());
                    followUpList.add(follow);
                }
            }
        }
        map.put("loanFollowup", followUpList);
        return map;
    }

    @GetMapping(value = "follow-up")
    public List<FollowUpSummaryModel> getMonthlyFollowUpSummary(String designation, String userPin) {
        return followUpService.getCurrentMonthFollowUpSummmary(userPin, designation);
    }

    @GetMapping(value = "ptp-status-summary")
    public List<PtpStatusSummary> getPtpStatusWiseSummary(String designation, String userPin) {
        List<PtpStatusSummary> ptpStatusSummaries =  loanPtpService.getCurrentMonthFollowUpSummmary(designation, userPin);
        return ptpStatusSummaries;
    }

    @GetMapping(value = "advance-search")
    public List<AccountInformationEntity> getAdvenceSearchData(@RequestParam("payload") String payloadString) throws Exception {

        payloadString = payloadString.replace("&quot;", "\"");

        AdvanceSearchPayload payload = new ObjectMapper().readValue(payloadString, AdvanceSearchPayload.class);
        List<AccountInformationEntity> accountInformationEntity = accountInformationService.advancedSearch(payload);
        return accountInformationEntity;
//        return retailLoanUcbApiService.getAdvanceSearchData(payload);
    }

    @GetMapping(value = "dealer-wise-payment-summary")
    public List<DealerWisePayment> getDealerWisePaymentSummary(@RequestParam("dealerPins") String[] dealerPins) {
        return dashboardService.getDealerWisePaymentSummary(Arrays.asList(dealerPins));
    }

    @GetMapping(value = "dealer-wise-ptp-summary")
    public List<DealerWisePtpSummary> getDealerWisePtpSummary(@RequestParam("dealerPins") String[] dealerPins) {
        return loanPtpService.getDealerWisePtpSummary(Arrays.asList(dealerPins));
    }

    @GetMapping(value = "dealer-wise-follow-up-summary")
    public List<DealerWiseFollowUpSummary> getDealerWiseFollowUpSummary(@RequestParam("dealerPins") String[] dealerPins) {
        return followUpService.getDealerWiseFollowUpSummary(Arrays.asList(dealerPins));
    }

    @ResponseBody
    @GetMapping(value = "payment-summary")
    public List<LoanPaymentSummaryModel> getDealerPaymentSummary(@RequestParam("userPin") String userPin) {
        List<String> pinList = peopleAllocationLogicService.findDealerPinByAnyPin(userPin);
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return dashboardService.getCategorizedPaymentSummary(pinList, startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "kpi-target-vs-achievement-account-wise")
    public List<LoanKpiTargetVsAchievement> getKpiAccountWiseSummaryForCurrentMonth(@RequestParam("userPin") String userPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAccountWiseSummary(Arrays.asList(userPin), startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "kpi-target-vs-achievement-amount-wise")
    public List<LoanKpiTargetVsAchievement> getKpiAmountWiseSummaryForCurrentMonth(@RequestParam("userPin") String userPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAmountWiseSummary(Arrays.asList(userPin), startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "performance-and-esau-trend")
    public LoanPerformanceAndEsauTrendDataModel getPerformanceAndEsauTrendSummary(@RequestParam("userPin") String userPin,
                                                                                  @RequestParam(name = "month", required = false) String month) {
        Date beginingMonth = StringUtils.hasText(month) ? dateUtils.getFormattedDate(month, "yyyy-MM") : new Date();
        List<LoanPerformanceAndEsauTrendDataModel> summary = performanceAndEsauTrendService.getPerformanceAndEsauTrendSummary(Arrays.asList(userPin), beginingMonth);
        return summary.isEmpty() ? new LoanPerformanceAndEsauTrendDataModel() : summary.get(0);
    }



    @ResponseBody
    @GetMapping(value = "team-lead-kpi-target-account-wise")
    public List<LoanKpiTargetVsAchievement> getTeamLeadKpiAccountWiseSummaryForCurrentMonth() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findDealerByTeamLeaderId(employeeInfoEntity.getId());
        List<String>userPins=new ArrayList<>();
        for(PeopleAllocationLogicInfo peopleAllocationLogicInfo:peopleAllocationLogicInfoList){
            EmployeeInfoEntity emp = employeeService.getById(peopleAllocationLogicInfo.getDealer().getId());
            userPins.add(emp.getPin());
        }
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAccountWiseSummary(userPins, startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "team-lead-kpi-target-amount-wise")
    public List<LoanKpiTargetVsAchievement> getTeamLeadKpiAmountWiseSummaryForCurrentMonth() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findDealerByTeamLeaderId(employeeInfoEntity.getId());
        List<String>userPins=new ArrayList<>();
        for(PeopleAllocationLogicInfo peopleAllocationLogicInfo:peopleAllocationLogicInfoList){
            EmployeeInfoEntity emp = employeeService.getById(peopleAllocationLogicInfo.getDealer().getId());
            userPins.add(emp.getPin());
        }
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAmountWiseSummary(userPins, startDate, endDate);
    }

}
