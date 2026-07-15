package com.fsoft.erp.modules.erp.service.impl;

import com.fsoft.erp.common.constant.GlobalVariableConstant;
import com.fsoft.erp.common.dto.request.PagingRequest;
import com.fsoft.erp.common.dto.response.PagingResponse;
import com.fsoft.erp.common.enums.ErrorCode;
import com.fsoft.erp.common.exception.AppException;
import com.fsoft.erp.common.utils.PagingUtil;
import com.fsoft.erp.modules.erp.dto.request.EmployeeCreationRequest;
import com.fsoft.erp.modules.erp.dto.response.EmployeeResponse;
import com.fsoft.erp.modules.erp.entity.Employee;
import com.fsoft.erp.modules.erp.mapper.EmployeeMapper;
import com.fsoft.erp.modules.erp.repository.EmployeeRepository;
import com.fsoft.erp.modules.erp.service.interfaces.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeCreationRequest request) {
        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new AppException(ErrorCode.EMPLOYEE_CODE_EXISTS);
        }

        Employee employee = employeeMapper.toEmployee(request);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponse(saved);
    }

    @Override
    public PagingResponse<EmployeeResponse> getEmployees(PagingRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() - GlobalVariableConstant.PAGE_SIZE_INDEX,
                request.getPageSize(),
                PagingUtil.createSort(request)
        );

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return PagingResponse.<EmployeeResponse>builder()
                .currentPage(request.getPage())
                .pageSize(employeePage.getSize())
                .totalPages(employeePage.getTotalPages())
                .totalElement(employeePage.getTotalElements())
                .data(employeePage.getContent().stream()
                        .map(employeeMapper::toEmployeeResponse)
                        .toList())
                .build();
    }

    @Override
    public EmployeeResponse getById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
        return employeeMapper.toEmployeeResponse(employee);
    }
}
