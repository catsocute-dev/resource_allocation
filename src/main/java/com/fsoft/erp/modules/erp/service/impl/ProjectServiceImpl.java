package com.fsoft.erp.modules.erp.service.impl;

import com.fsoft.erp.common.constant.GlobalVariableConstant;
import com.fsoft.erp.common.dto.request.PagingRequest;
import com.fsoft.erp.common.dto.response.PagingResponse;
import com.fsoft.erp.common.enums.ErrorCode;
import com.fsoft.erp.common.exception.AppException;
import com.fsoft.erp.common.utils.PagingUtil;
import com.fsoft.erp.modules.erp.dto.request.ProjectCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.ProjectResponse;
import com.fsoft.erp.modules.erp.entity.Project;
import com.fsoft.erp.modules.erp.mapper.ProjectMapper;
import com.fsoft.erp.modules.erp.repository.ProjectRepository;
import com.fsoft.erp.modules.erp.service.interfaces.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponse create(ProjectCreationRequest request) {
        if (projectRepository.existsByProjectCode(request.getProjectCode())) {
            throw new AppException(ErrorCode.PROJECT_CODE_EXISTS);
        }

        Project project = projectMapper.toProject(request);
        Project saved = projectRepository.save(project);
        return projectMapper.toProjectResponse(saved);
    }

    @Override
    public PagingResponse<ProjectResponse> getProjects(PagingRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<Project> projectPage = projectRepository.findAll(pageable);

        return PagingResponse.<ProjectResponse>builder()
                .currentPage(request.getPage())
                .pageSize(projectPage.getSize())
                .totalPages(projectPage.getTotalPages())
                .totalElement(projectPage.getTotalElements())
                .data(projectPage.getContent().stream()
                        .map(projectMapper::toProjectResponse)
                        .toList())
                .build();
    }

    @Override
    public ProjectResponse getById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));
        return projectMapper.toProjectResponse(project);
    }
}
