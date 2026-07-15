package com.fsoft.erp.modules.erp.mapper;

import com.fsoft.erp.modules.erp.dto.request.AllocationCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.AllocationResponse;
import com.fsoft.erp.modules.erp.entity.Allocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllocationMapper {

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "project", ignore = true)
    Allocation toAllocation(AllocationCreationRequest request);

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee.fullName")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.projectName")
    AllocationResponse toAllocationResponse(Allocation allocation);
}
