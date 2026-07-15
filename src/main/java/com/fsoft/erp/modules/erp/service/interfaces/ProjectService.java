package com.fsoft.erp.modules.erp.service.interfaces;

import com.fsoft.erp.common.dto.request.PagingRequest;
import com.fsoft.erp.common.dto.response.PagingResponse;
import com.fsoft.erp.modules.erp.dto.request.ProjectCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.ProjectResponse;

public interface ProjectService {

    ProjectResponse create(ProjectCreationRequest request);

    PagingResponse<ProjectResponse> getProjects(PagingRequest request);

    ProjectResponse getById(String id);
}
