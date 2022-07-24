package com.unisoft.customerloanprofile.AdditionalInfo;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AdditionalInfoDao {

    @Autowired
    private EntityManager entityManager;

    public List<AdditionalInfo> getList(){
        List<AdditionalInfo> additionalInfoList = new ArrayList<>();

        try{
            Session session = entityManager.unwrap(Session.class);
            additionalInfoList = session.createCriteria(AdditionalInfo.class).list();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return additionalInfoList;
    }

    public List<AdditionalInfo> findByCustomer(CustomerBasicInfoEntity customerBasicInfoEntity){
        List<AdditionalInfo> additionalInfoList = new ArrayList<>();

        try{
            Session session = entityManager.unwrap(Session.class);
            additionalInfoList = session.createCriteria(AdditionalInfo.class)
                    .add(Restrictions.eq("customerBasicInfo", customerBasicInfoEntity))
                    .list();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return additionalInfoList;
    }

    public boolean save(AdditionalInfo additionalInfo){
        boolean save = false;

        try{
            Session session = entityManager.unwrap(Session.class);
            session.saveOrUpdate(additionalInfo);
            session.flush();
            save = true;
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return save;
    }


    public AdditionalInfo findById(Long id){
        AdditionalInfo additionalInfo = new AdditionalInfo();
        try {
            Session session = entityManager.unwrap(Session.class);
            additionalInfo = session.byId(AdditionalInfo.class).getReference(id);
            session.flush();
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return additionalInfo;
    }


}
