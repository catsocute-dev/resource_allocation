package com.fsoft.erp.modules.erp.entity;

import com.fsoft.erp.common.entity.BaseEntity;
import com.fsoft.erp.modules.erp.constants.EmployeeConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = EmployeeConstants.TABLE_EMPLOYEE)
public class Employee extends BaseEntity {

    @Column(name = EmployeeConstants.COL_EMPLOYEE_CODE,
            nullable = false,
            unique = true,
            length = EmployeeConstants.MAX_EMPLOYEE_CODE)
    @Size(max = EmployeeConstants.MAX_EMPLOYEE_CODE)
    String employeeCode;

    @Column(name = EmployeeConstants.COL_FULL_NAME,
            nullable = false,
            length = EmployeeConstants.MAX_FULL_NAME)
    @Size(max = EmployeeConstants.MAX_FULL_NAME)
    String fullName;

    @Column(name = EmployeeConstants.COL_EMAIL,
            nullable = false,
            unique = true,
            length = EmployeeConstants.MAX_EMAIL)
    @Email
    String email;

    @Column(name = EmployeeConstants.COL_ROLE,
            length = EmployeeConstants.MAX_ROLE)
    @Size(max = EmployeeConstants.MAX_ROLE)
    String role;

    @Column(name = EmployeeConstants.COL_DEPARTMENT,
            length = EmployeeConstants.MAX_DEPARTMENT)
    @Size(max = EmployeeConstants.MAX_DEPARTMENT)
    String department;
}
