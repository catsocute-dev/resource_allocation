package com.fsoft.erp.modules.erp.dto.request;

import com.fsoft.erp.modules.erp.constants.EmployeeConstants;
import com.fsoft.erp.modules.erp.constants.EmployeeErrorCodeConstants;
import jakarta.validation.constraints.Email;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeCreationRequest {

    @NotNull
    @NotBlank
    @Size(max = EmployeeConstants.MAX_EMPLOYEE_CODE)
    String employeeCode;

    @NotNull
    @NotBlank
    @Size(max = EmployeeConstants.MAX_FULL_NAME)
    String fullName;

    @NotNull
    @NotBlank
    @Email(message = EmployeeErrorCodeConstants.EMPLOYEE_EMAIL_INVALID)
    String email;

    @Size(max = EmployeeConstants.MAX_ROLE)
    String role;

    @Size(max = EmployeeConstants.MAX_DEPARTMENT)
    String department;
}
