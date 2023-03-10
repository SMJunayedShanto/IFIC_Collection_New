package com.unisoft.customerloanprofile.hotnote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.unisoft.common.CommonEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class HotNoteEntity extends CommonEntity {

    @Expose
    private String hotNote;
    @Expose
    private String vipStatus;
    @Expose
    private boolean statusFlag;
    @Expose
    private String creatorPin;
    @Expose
    private String username;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;

}
