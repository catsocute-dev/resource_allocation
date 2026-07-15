package com.fsoft.erp.modules.erp.controller;

import com.fsoft.erp.common.constant.ApiConstant;
import com.fsoft.erp.common.constant.PaginationConstant;
import com.fsoft.erp.common.dto.request.PagingRequest;
import com.fsoft.erp.common.dto.request.SortRequest;
import com.fsoft.erp.common.dto.response.ApiResponse;
import com.fsoft.erp.common.dto.response.PagingResponse;
import com.fsoft.erp.modules.erp.dto.request.ProjectCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.ProjectResponse;
import com.fsoft.erp.modules.erp.service.interfaces.ProjectService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {

    ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody ProjectCreationRequest request
    ) {
        ApiResponse<ProjectResponse> response = ApiResponse.<ProjectResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(projectService.create(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResponse<ProjectResponse>>> getProjects(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = PaginationConstant.DESC) String direction,
            @RequestParam(required = false, defaultValue = "createdAt") String field
    ) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .pageSize(size)
                .sortRequest(SortRequest.builder()
                        .direction(direction)
                        .field(field)
                        .build())
                .build();

        ApiResponse<PagingResponse<ProjectResponse>> response = ApiResponse.<PagingResponse<ProjectResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(projectService.getProjects(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(
            @PathVariable String id
    ) {
        ApiResponse<ProjectResponse> response = ApiResponse.<ProjectResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(projectService.getById(id))
                .build();

        return ResponseEntity.ok(response);
    }
}
