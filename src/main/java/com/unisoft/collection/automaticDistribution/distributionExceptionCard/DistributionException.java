package com.unisoft.collection.automaticDistribution.distributionExceptionCard;


import com.unisoft.base.BaseInfo;
import com.unisoft.collection.automaticDistribution.distributionExceptionCardModel.ProductGroupAgeCode;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DistributionException extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String samIgnore;

    private String writeOffIgnore;

    private String VVIP;

    private String secureCard;

    private String[] billingCycle;

    private String[] plasticCd;
    private String[] contractId;


    private String isMultiProductDistribute;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<CustomerBasicInfoEntity> customerList = new ArrayList<>();

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<EmployeeInfoEntity> employeeInfos = new ArrayList<>();

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<ProductTypeEntity> productsList = new ArrayList<>();

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Card_Exeception_id", nullable = false)
    private List<ProductGroupAgeCode> productGroupAgeCodes = new ArrayList<>();



    @Transient
    private List<String> customerIds = new ArrayList<>();

    @Transient
    private List<String> dealerIds = new ArrayList<>();

    @Transient
    private List<String> productIds = new ArrayList<>();

    @Transient
    private List<String> riskCategoryIds = new ArrayList<>();
}
