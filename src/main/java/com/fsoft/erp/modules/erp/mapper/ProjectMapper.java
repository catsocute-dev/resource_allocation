package com.fsoft.erp.modules.erp.mapper;

import com.fsoft.erp.modules.erp.dto.request.ProjectCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.ProjectResponse;
import com.fsoft.erp.modules.erp.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toProject(ProjectCreationRequest request);
    ProjectResponse toProjectResponse(Project project);
}
