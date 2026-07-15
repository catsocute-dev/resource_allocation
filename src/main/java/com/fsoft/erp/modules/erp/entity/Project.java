package com.fsoft.erp.modules.erp.entity;

import com.fsoft.erp.common.entity.BaseEntity;
import com.fsoft.erp.modules.erp.constants.ProjectConstants;
import com.fsoft.erp.modules.erp.enums.ProjectStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = ProjectConstants.TABLE_PROJECT)
public class Project extends BaseEntity {

    @Column(name = ProjectConstants.COL_PROJECT_CODE,
            nullable = false,
            unique = true,
            length = ProjectConstants.MAX_PROJECT_CODE)
    @Size(max = ProjectConstants.MAX_PROJECT_CODE)
    String projectCode;

    @Column(name = ProjectConstants.COL_PROJECT_NAME,
            nullable = false,
            length = ProjectConstants.MAX_PROJECT_NAME)
    @Size(max = ProjectConstants.MAX_PROJECT_NAME)
    String projectName;

    @Column(name = ProjectConstants.COL_CUSTOMER,
            length = ProjectConstants.MAX_CUSTOMER)
    @Size(max = ProjectConstants.MAX_CUSTOMER)
    String customer;

    @Column(name = ProjectConstants.COL_START_DATE)
    LocalDate startDate;

    @Column(name = ProjectConstants.COL_END_DATE)
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = ProjectConstants.COL_STATUS, nullable = false)
    ProjectStatus status;
}
