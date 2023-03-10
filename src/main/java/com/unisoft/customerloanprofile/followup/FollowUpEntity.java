package com.unisoft.customerloanprofile.followup;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.unisoft.base.BaseInfo;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Data
@Entity
public class FollowUpEntity extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date followUpDate;

    private String followUpTime;
    private String followUpRemarks;
    @Expose
    @ElementCollection
    private Set<String> followUpReason;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;

    @Transient
    private String outstanding;

    @Transient
    private String followUpDates;

    @Transient
    private String accNo;

    @Expose
    private String pin;
    @Expose
    private String username;

    @Transient
    private String crDate;

    public FollowUpEntity() {
    }

//    public FollowUpEntity(Tuple t) {
//        //this.followUpDate = Objects.toString(t.get(""),"-");
//        this.followUpTime = Objects.toString(t.get(""),"-");
//        this.crDate = Objects.toString(t.get("CREATED_DATE"),"-");
//        this.accNo = Objects.toString(t.get("ACCOUNT_NO"),"-");
//        this.outstanding = Objects.toString(t.get("OUTSTANDING"),"-");
//        this.followUpReason = Objects.toString(t.get("OUTSTANDING"),"-");
//    }
}
