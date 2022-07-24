package com.unisoft.collection.samd.setup.temporaryjobdelegation;

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
public class TemporaryJobDelegation extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String remarks;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<EmployeeInfoEntity> toUser = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REFRESH)
    private EmployeeInfoEntity fromUser;

    @Transient
    private List<Long> toUserIds;

    private String temporaryJobDelegationStatus;

    public void setToUserIds(List<Long> toUserIds) {
        this.toUser = toUserIds != null ? toUserIds.stream().map(EmployeeInfoEntity::new).collect(Collectors.toList()) : null;
    }


    public EmployeeInfoEntity getFromUser() {
        return fromUser= (fromUser != null) ? fromUser: new EmployeeInfoEntity();
    }



}
