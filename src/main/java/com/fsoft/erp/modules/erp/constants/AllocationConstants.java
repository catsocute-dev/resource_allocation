package com.fsoft.erp.modules.erp.constants;

public class AllocationConstants {
    private AllocationConstants() {}

    public static final String TABLE_ALLOCATION = "allocation";

    public static final String COL_EMPLOYEE_ID = "employee_id";
    public static final String COL_PROJECT_ID = "project_id";
    public static final String COL_ALLOCATION_PERCENT = "allocation_percent";
    public static final String COL_ROLE_IN_PROJECT = "role_in_project";
    public static final String COL_START_DATE = "start_date";
    public static final String COL_END_DATE = "end_date";

    public static final int MIN_ALLOCATION_PERCENT = 1;
    public static final int MAX_ALLOCATION_PERCENT = 100;
    public static final int MAX_ROLE_IN_PROJECT = 100;
}
