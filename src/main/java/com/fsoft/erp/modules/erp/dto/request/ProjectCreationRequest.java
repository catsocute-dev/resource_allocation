package com.fsoft.erp.modules.erp.dto.request;

import com.fsoft.erp.modules.erp.constants.ProjectConstants;
import com.fsoft.erp.modules.erp.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ProjectCreationRequest {

    @NotNull
    @NotBlank
    @Size(max = ProjectConstants.MAX_PROJECT_CODE)
    String projectCode;

    @NotNull
    @NotBlank
    @Size(max = ProjectConstants.MAX_PROJECT_NAME)
    String projectName;

    @Size(max = ProjectConstants.MAX_CUSTOMER)
    String customer;

    LocalDate startDate;

    LocalDate endDate;

    @NotNull
    ProjectStatus status;
}
