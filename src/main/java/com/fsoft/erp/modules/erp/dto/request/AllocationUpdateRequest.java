package com.fsoft.erp.modules.erp.dto.request;

import com.fsoft.erp.modules.erp.constants.AllocationConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class AllocationUpdateRequest {

    String employeeId;

    String projectId;

    @Min(AllocationConstants.MIN_ALLOCATION_PERCENT)
    @Max(AllocationConstants.MAX_ALLOCATION_PERCENT)
    Integer allocationPercent;

    @Size(max = AllocationConstants.MAX_ROLE_IN_PROJECT)
    String roleInProject;

    LocalDate startDate;

    LocalDate endDate;
}
