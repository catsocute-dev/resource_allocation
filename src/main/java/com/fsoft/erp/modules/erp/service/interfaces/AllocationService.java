package com.fsoft.erp.modules.erp.service.interfaces;

import com.fsoft.erp.modules.erp.dto.request.AllocationCreationRequest;
import com.fsoft.erp.modules.erp.dto.request.AllocationUpdateRequest;
import com.fsoft.erp.modules.erp.dto.response.AllocationResponse;
import com.fsoft.erp.modules.erp.dto.response.UtilizationResponse;
import com.fsoft.erp.modules.erp.dto.response.WorkloadResponse;

import java.util.List;

public interface AllocationService {

    AllocationResponse create(AllocationCreationRequest request);

    AllocationResponse update(String id, AllocationUpdateRequest request);

    void delete(String id);

    WorkloadResponse getEmployeeWorkload(String employeeId);

    List<UtilizationResponse> getUtilizationReport();

    List<UtilizationResponse> getAvailableResources();

    List<UtilizationResponse> getOverloadedEmployees();
}
