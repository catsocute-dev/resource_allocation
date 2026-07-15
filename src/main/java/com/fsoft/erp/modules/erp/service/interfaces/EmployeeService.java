package com.fsoft.erp.modules.erp.service.interfaces;

import com.fsoft.erp.common.dto.request.PagingRequest;
import com.fsoft.erp.common.dto.response.PagingResponse;
import com.fsoft.erp.modules.erp.dto.request.EmployeeCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse create(EmployeeCreationRequest request);

    PagingResponse<EmployeeResponse> getEmployees(PagingRequest request);

    EmployeeResponse getById(String id);
}
