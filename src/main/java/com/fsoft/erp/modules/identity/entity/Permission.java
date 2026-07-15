package com.fsoft.erp.modules.identity.entity;

import com.fsoft.erp.common.entity.BaseEntity;
import com.fsoft.erp.modules.identity.constants.permission.PermissionConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = PermissionConstants.TABLE_PERMISSION)
public class Permission extends BaseEntity {
    @Column(name = PermissionConstants.COL_PERMISSION_NAME,
            nullable = false,
            unique = true,
            columnDefinition = PermissionConstants.PERMISSION_NAME_DEFINITION)
    @Size(min = PermissionConstants.MIN_CHARS_PERMISSION_NAME,
            max = PermissionConstants.MAX_CHARS_PERMISSION_NAME)
    String permissionName;
}
