package com.fsoft.erp.modules.erp.service.impl;

import com.fsoft.erp.common.annotation.LogAllocation;
import com.fsoft.erp.common.enums.ErrorCode;
import com.fsoft.erp.common.exception.AppException;
import com.fsoft.erp.modules.erp.dto.request.AllocationCreationRequest;
import com.fsoft.erp.modules.erp.dto.request.AllocationUpdateRequest;
import com.fsoft.erp.modules.erp.dto.response.AllocationResponse;
import com.fsoft.erp.modules.erp.dto.response.UtilizationResponse;
import com.fsoft.erp.modules.erp.dto.response.WorkloadResponse;
import com.fsoft.erp.modules.erp.entity.Allocation;
import com.fsoft.erp.modules.erp.entity.Employee;
import com.fsoft.erp.modules.erp.entity.Project;
import com.fsoft.erp.modules.erp.enums.ProjectStatus;
import com.fsoft.erp.modules.erp.mapper.AllocationMapper;
import com.fsoft.erp.modules.erp.repository.AllocationRepository;
import com.fsoft.erp.modules.erp.repository.EmployeeRepository;
import com.fsoft.erp.modules.erp.repository.ProjectRepository;
import com.fsoft.erp.modules.erp.service.interfaces.AllocationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AllocationServiceImpl implements AllocationService {

    AllocationRepository allocationRepository;
    EmployeeRepository employeeRepository;
    ProjectRepository projectRepository;
    AllocationMapper allocationMapper;

    @Override
    @Transactional
    @LogAllocation(operation = "CREATE")
    public AllocationResponse create(AllocationCreationRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        // Business Rule 3: Cannot allocate to COMPLETED project
        if (project.getStatus() == ProjectStatus.COMPLETED) {
            throw new AppException(ErrorCode.ALLOCATION_PROJECT_COMPLETED);
        }

        // Business Rule 2: Total allocation must not exceed 100%
        Integer currentTotal = allocationRepository.sumAllocationPercentByEmployeeId(employee.getId());
        if (currentTotal + request.getAllocationPercent() > 100) {
            throw new AppException(ErrorCode.ALLOCATION_EXCEEDED);
        }

        Allocation allocation = allocationMapper.toAllocation(request);
        allocation.setEmployee(employee);
        allocation.setProject(project);

        Allocation saved = allocationRepository.save(allocation);
        log.info("Created allocation: employee={}, project={}, percent={}",
                employee.getEmployeeCode(), project.getProjectCode(), saved.getAllocationPercent());

        return allocationMapper.toAllocationResponse(saved);
    }

    @Override
    @Transactional
    @LogAllocation(operation = "UPDATE")
    public AllocationResponse update(String id, AllocationUpdateRequest request) {
        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ALLOCATION_NOT_FOUND));

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
            allocation.setEmployee(employee);
        }

        if (request.getProjectId() != null) {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

            if (project.getStatus() == ProjectStatus.COMPLETED) {
                throw new AppException(ErrorCode.ALLOCATION_PROJECT_COMPLETED);
            }
            allocation.setProject(project);
        }

        if (request.getAllocationPercent() != null) {
            // Recalculate total excluding current allocation
            Integer currentTotal = allocationRepository.sumAllocationPercentByEmployeeId(
                    allocation.getEmployee().getId());
            int totalWithoutCurrent = currentTotal - allocation.getAllocationPercent();

            if (totalWithoutCurrent + request.getAllocationPercent() > 100) {
                throw new AppException(ErrorCode.ALLOCATION_EXCEEDED);
            }
            allocation.setAllocationPercent(request.getAllocationPercent());
        }

        if (request.getRoleInProject() != null) {
            allocation.setRoleInProject(request.getRoleInProject());
        }
        if (request.getStartDate() != null) {
            allocation.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            allocation.setEndDate(request.getEndDate());
        }

        Allocation updated = allocationRepository.save(allocation);
        log.info("Updated allocation: id={}, percent={}", updated.getId(), updated.getAllocationPercent());

        return allocationMapper.toAllocationResponse(updated);
    }

    @Override
    @Transactional
    @LogAllocation(operation = "DELETE")
    public void delete(String id) {
        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ALLOCATION_NOT_FOUND));

        allocationRepository.delete(allocation);
        log.info("Deleted allocation: id={}", id);
    }

    @Override
    public WorkloadResponse getEmployeeWorkload(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Integer totalAllocation = allocationRepository.sumAllocationPercentByEmployeeId(employeeId);
        int available = 100 - totalAllocation;

        return WorkloadResponse.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getFullName())
                .totalAllocation(totalAllocation)
                .available(available)
                .build();
    }

    @Override
    public List<UtilizationResponse> getUtilizationReport() {
        List<Object[]> results = allocationRepository.findUtilizationReport();
        return results.stream()
                .map(row -> UtilizationResponse.builder()
                        .employeeId((String) row[0])
                        .employeeName((String) row[1])
                        .totalAllocation(((Number) row[2]).intValue())
                        .build())
                .toList();
    }

    @Override
    public List<UtilizationResponse> getAvailableResources() {
        List<Object[]> results = allocationRepository.findAvailableResources();
        return results.stream()
                .map(row -> UtilizationResponse.builder()
                        .employeeId((String) row[0])
                        .employeeName((String) row[1])
                        .totalAllocation(((Number) row[2]).intValue())
                        .build())
                .toList();
    }

    @Override
    public List<UtilizationResponse> getOverloadedEmployees() {
        List<Object[]> results = allocationRepository.findOverloadedEmployees();
        return results.stream()
                .map(row -> UtilizationResponse.builder()
                        .employeeId((String) row[0])
                        .employeeName((String) row[1])
                        .totalAllocation(((Number) row[2]).intValue())
                        .build())
                .toList();
    }
}
