package com.fsoft.erp.modules.identity.service.interfaces;


import com.fsoft.erp.common.dto.request.PagingRequest;
import com.fsoft.erp.common.dto.response.PagingResponse;
import com.fsoft.erp.modules.identity.dto.request.RoleUpdateRequest;
import com.fsoft.erp.modules.identity.dto.response.RoleResponse;

public interface RoleService {

    PagingResponse<RoleResponse> getRoles(PagingRequest request);

    RoleResponse getById(String roleId);

    RoleResponse update(String roleId, RoleUpdateRequest request);

    void deleteById(String roleId);
}

