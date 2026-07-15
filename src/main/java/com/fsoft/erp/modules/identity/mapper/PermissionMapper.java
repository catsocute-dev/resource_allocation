package com.fsoft.erp.modules.identity.mapper;

import com.fsoft.erp.modules.identity.dto.response.PermissionResponse;
import com.fsoft.erp.modules.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);
}
