package com.unisoft.retail.card.reporttextfileupload;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveryViewService {

    @Autowired
    private RecoveryViewDAO recoveryViewDAO;

//    public RecoveryViewDTO getRecoveryView(String contractNo, String date){
//        return recoveryViewDAO.getRecoveryView(contractNo, date);
//    }
}
