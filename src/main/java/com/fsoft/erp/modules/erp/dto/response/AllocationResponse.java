package com.fsoft.erp.modules.erp.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllocationResponse {
    String id;
    String employeeId;
    String employeeName;
    String projectId;
    String projectName;
    Integer allocationPercent;
    String roleInProject;
    LocalDate startDate;
    LocalDate endDate;
}
