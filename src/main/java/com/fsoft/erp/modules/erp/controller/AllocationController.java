package com.fsoft.erp.modules.erp.controller;

import com.fsoft.erp.common.constant.ApiConstant;
import com.fsoft.erp.common.dto.response.ApiResponse;
import com.fsoft.erp.modules.erp.dto.request.AllocationCreationRequest;
import com.fsoft.erp.modules.erp.dto.request.AllocationUpdateRequest;
import com.fsoft.erp.modules.erp.dto.response.AllocationResponse;
import com.fsoft.erp.modules.erp.dto.response.UtilizationResponse;
import com.fsoft.erp.modules.erp.dto.response.WorkloadResponse;
import com.fsoft.erp.modules.erp.service.interfaces.AllocationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AllocationController {

    AllocationService allocationService;

    @PostMapping("/allocations")
    public ResponseEntity<ApiResponse<AllocationResponse>> createAllocation(
            @Valid @RequestBody AllocationCreationRequest request
    ) {
        ApiResponse<AllocationResponse> response = ApiResponse.<AllocationResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(allocationService.create(request))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/allocations/{id}")
    public ResponseEntity<ApiResponse<AllocationResponse>> updateAllocation(
            @PathVariable String id,
            @Valid @RequestBody AllocationUpdateRequest request
    ) {
        ApiResponse<AllocationResponse> response = ApiResponse.<AllocationResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(allocationService.update(id, request))
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/allocations/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAllocation(@PathVariable String id) {
        allocationService.delete(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(ApiConstant.SUCCESS)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/{id}/workload")
    public ResponseEntity<ApiResponse<WorkloadResponse>> getEmployeeWorkload(
            @PathVariable String id
    ) {
        ApiResponse<WorkloadResponse> response = ApiResponse.<WorkloadResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(allocationService.getEmployeeWorkload(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/utilization")
    public ResponseEntity<ApiResponse<List<UtilizationResponse>>> getUtilizationReport() {
        ApiResponse<List<UtilizationResponse>> response = ApiResponse.<List<UtilizationResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(allocationService.getUtilizationReport())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/available")
    public ResponseEntity<ApiResponse<List<UtilizationResponse>>> getAvailableResources() {
        ApiResponse<List<UtilizationResponse>> response = ApiResponse.<List<UtilizationResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(allocationService.getAvailableResources())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/overloaded")
    public ResponseEntity<ApiResponse<List<UtilizationResponse>>> getOverloadedEmployees() {
        ApiResponse<List<UtilizationResponse>> response = ApiResponse.<List<UtilizationResponse>>builder()
                .success(ApiConstant.SUCCESS)
                .data(allocationService.getOverloadedEmployees())
                .build();

        return ResponseEntity.ok(response);
    }
}
