package com.unisoft.collection.KPI.Loan.TargetByAccount;
/*
  Created by Md.   Islam on 8/28/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.sectorGroup.SectorGroupEntity;
import com.unisoft.collection.settings.sectorGroup.SectorGroupService;
import com.unisoft.collection.settings.zone.ZoneEntity;
import com.unisoft.collection.settings.zone.ZoneService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/kpi/loan/byAccount/")
public class LoanKPITargetByAccountController {


    private LoanKPITargetByAccountService loanKPITargetByAccountService;
    private ProductTypeService productTypeService;
    private SectorGroupService sectorGroupService;
    private LocationService locationService;
    private ZoneService zoneService;
    private DPDBucketService dpdBucketService;
    private UserService userService;
    private ProductTypeRepository productTypeRepository;
    private AuditTrailService auditTrailService;


    @GetMapping("list")

    public String viewList(Model model){

        List<LoanKPITargetByAccountEntity> loanKPITargetByAccountEntities= loanKPITargetByAccountService.getAllData();
        model.addAttribute("loanKPIByAccountList",loanKPITargetByAccountEntities);

        return "collection/kpi/loan/targetByAccount/listView";

    }


    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson = new Gson();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));

        model.addAttribute("loanKPIByAccount",new LoanKPITargetByAccountEntity());
        return "collection/kpi/loan/targetByAccount/create";
    }

    @PostMapping(value = "create")
    public String addNewPage( LoanKPITargetByAccountEntity loanKPITargetByAccountEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(loanKPITargetByAccountEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<DPDBucketEntity> dpdBucketEntities= new ArrayList<>();


            for(String s: loanKPITargetByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getDpdBucketIds()){
                DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(new Long(s));
                dpdBucketEntities.add(dpdBucketEntity);
            }

            loanKPITargetByAccountEntity.setProductType(productTypeEntities);
            loanKPITargetByAccountEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetByAccountEntity.setZone(zoneEntities);
            loanKPITargetByAccountEntity.setLocation(locationEntities);
            loanKPITargetByAccountEntity.setDpdBucket(dpdBucketEntities);

            loanKPITargetByAccountEntity.setCreatedBy(empId);
            loanKPITargetByAccountEntity.setCreatedDate(new Date());
            boolean save = loanKPITargetByAccountService.addNew(loanKPITargetByAccountEntity);
            auditTrailService.saveCreatedData("KPI Target Setup based on Account-Loan", loanKPITargetByAccountEntity);
        }else {
            LoanKPITargetByAccountEntity oldEntity = loanKPITargetByAccountService.getById(loanKPITargetByAccountEntity.getId());
            LoanKPITargetByAccountEntity previousEntity = new LoanKPITargetByAccountEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<DPDBucketEntity> dpdBucketEntities= new ArrayList<>();

            for(String s: loanKPITargetByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: loanKPITargetByAccountEntity.getDpdBucketIds()){
                DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(new Long(s));
                dpdBucketEntities.add(dpdBucketEntity);
            }

            loanKPITargetByAccountEntity.setProductType(productTypeEntities);
            loanKPITargetByAccountEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetByAccountEntity.setZone(zoneEntities);
            loanKPITargetByAccountEntity.setLocation(locationEntities);
            loanKPITargetByAccountEntity.setDpdBucket(dpdBucketEntities);

            loanKPITargetByAccountEntity.setModifiedBy(empId);
            loanKPITargetByAccountEntity.setModifiedDate(new Date());
            boolean update=loanKPITargetByAccountService.updateData(loanKPITargetByAccountEntity);
            auditTrailService.saveUpdatedData("KPI Target Setup based on Account-Loan", previousEntity, loanKPITargetByAccountEntity);
        }
        return "redirect:/collection/kpi/loan/byAccount/list";
    }



    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model)
    {
        LoanKPITargetByAccountEntity loanKPITargetByAccountEntity= loanKPITargetByAccountService.getById(findId);
        Gson gson = new Gson();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));
        model.addAttribute("selectedProductTypeList", gson.toJson(loanKPITargetByAccountEntity.getProductType()));
        model.addAttribute("selectedSectorGroupList", gson.toJson(loanKPITargetByAccountEntity.getSectorGroup()));
        model.addAttribute("selectedZoneList", gson.toJson(loanKPITargetByAccountEntity.getZone()));
        model.addAttribute("selectedLocationList", gson.toJson(loanKPITargetByAccountEntity.getLocation()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(loanKPITargetByAccountEntity.getDpdBucket()));

        model.addAttribute("loanKPIByAccount", loanKPITargetByAccountEntity);
        return "collection/kpi/loan/targetByAccount/create";
    }


    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model)
    {
        model.addAttribute("loanKPIByAccount",loanKPITargetByAccountService.getById(findId));
        return "collection/kpi/loan/targetByAccount/view";
    }

}
