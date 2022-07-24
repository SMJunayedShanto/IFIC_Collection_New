package com.unisoft.retail.loan.setup.nplReleaseLoan;
/*
 * Created by    on 25 April, 2021
 */

import com.unisoft.common.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NplReleaseLoanRepository extends CommonRepository<NplReleaseLoan> {

    NplReleaseLoan findNplReleaseLoanById(Long id);

}
