package com.unisoft.retail.card.dataEntry.comments;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
     List<Comment> findByCustomerBasicInfo(CustomerBasicInfoEntity customer);
}
