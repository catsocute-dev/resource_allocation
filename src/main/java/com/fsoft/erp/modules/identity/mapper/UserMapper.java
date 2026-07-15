package com.fsoft.erp.modules.identity.mapper;

import com.fsoft.erp.modules.identity.dto.request.UserCreationRequest;
import com.fsoft.erp.modules.identity.dto.response.UserResponse;
import com.fsoft.erp.modules.identity.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
