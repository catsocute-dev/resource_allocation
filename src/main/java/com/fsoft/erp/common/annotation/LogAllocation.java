package com.fsoft.erp.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to log allocation operations (CREATE, UPDATE, DELETE).
 * Applied at method level; intercepted by {@code AllocationLogAspect}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAllocation {

    /**
     * The operation being performed: CREATE, UPDATE, DELETE.
     */
    String operation();
}
