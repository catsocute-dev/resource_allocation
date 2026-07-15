package com.fsoft.erp.modules.erp.mapper;

import com.fsoft.erp.modules.erp.dto.request.EmployeeCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.EmployeeResponse;
import com.fsoft.erp.modules.erp.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeCreationRequest request);
    EmployeeResponse toEmployeeResponse(Employee employee);
}
