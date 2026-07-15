package com.fsoft.erp.modules.erp.dto.response;

import com.fsoft.erp.modules.erp.enums.ProjectStatus;
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
public class ProjectResponse {
    String id;
    String projectCode;
    String projectName;
    String customer;
    LocalDate startDate;
    LocalDate endDate;
    ProjectStatus status;
}
