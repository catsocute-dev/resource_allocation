package com.fsoft.erp.modules.identity.service.interfaces;

import com.fsoft.erp.common.dto.request.PagingRequest;
import com.fsoft.erp.common.dto.response.PagingResponse;
import com.fsoft.erp.modules.identity.dto.request.UserCreationRequest;
import com.fsoft.erp.modules.identity.dto.request.UserRolesUpdateRequest;
import com.fsoft.erp.modules.identity.dto.response.UserResponse;

public interface UserService {

    UserResponse create(UserCreationRequest request);

    PagingResponse<UserResponse> getUsers(PagingRequest request);

    UserResponse updateRoles(String userId, UserRolesUpdateRequest request);

    UserResponse getMyInfo();

    void deleteById(String userId);

}