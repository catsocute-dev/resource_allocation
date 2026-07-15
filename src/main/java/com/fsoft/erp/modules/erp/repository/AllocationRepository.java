package com.fsoft.erp.modules.erp.repository;

import com.fsoft.erp.modules.erp.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, String> {

    List<Allocation> findByEmployeeId(String employeeId);

    List<Allocation> findByProjectId(String projectId);

    @Query("SELECT COALESCE(SUM(a.allocationPercent), 0) FROM Allocation a WHERE a.employee.id = :employeeId")
    Integer sumAllocationPercentByEmployeeId(@Param("employeeId") String employeeId);

    @Query("SELECT a.employee.id, a.employee.fullName, SUM(a.allocationPercent) " +
            "FROM Allocation a GROUP BY a.employee.id, a.employee.fullName")
    List<Object[]> findUtilizationReport();

    @Query("SELECT a.employee.id, a.employee.fullName, SUM(a.allocationPercent) " +
            "FROM Allocation a GROUP BY a.employee.id, a.employee.fullName HAVING SUM(a.allocationPercent) < 100")
    List<Object[]> findAvailableResources();

    @Query("SELECT a.employee.id, a.employee.fullName, SUM(a.allocationPercent) " +
            "FROM Allocation a GROUP BY a.employee.id, a.employee.fullName HAVING SUM(a.allocationPercent) > 90")
    List<Object[]> findOverloadedEmployees();
}
