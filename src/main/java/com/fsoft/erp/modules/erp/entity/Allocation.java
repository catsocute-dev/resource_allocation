package com.fsoft.erp.modules.erp.entity;

import com.fsoft.erp.common.entity.BaseEntity;
import com.fsoft.erp.modules.erp.constants.AllocationConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = AllocationConstants.TABLE_ALLOCATION)
public class Allocation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = AllocationConstants.COL_EMPLOYEE_ID, nullable = false)
    Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = AllocationConstants.COL_PROJECT_ID, nullable = false)
    Project project;

    @Column(name = AllocationConstants.COL_ALLOCATION_PERCENT, nullable = false)
    @Min(AllocationConstants.MIN_ALLOCATION_PERCENT)
    @Max(AllocationConstants.MAX_ALLOCATION_PERCENT)
    Integer allocationPercent;

    @Column(name = AllocationConstants.COL_ROLE_IN_PROJECT,
            length = AllocationConstants.MAX_ROLE_IN_PROJECT)
    @Size(max = AllocationConstants.MAX_ROLE_IN_PROJECT)
    String roleInProject;

    @Column(name = AllocationConstants.COL_START_DATE)
    LocalDate startDate;

    @Column(name = AllocationConstants.COL_END_DATE)
    LocalDate endDate;
}
