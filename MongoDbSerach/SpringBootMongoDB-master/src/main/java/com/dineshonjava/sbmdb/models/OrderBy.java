package com.dineshonjava.sbmdb.models;

import org.springframework.data.mongodb.core.query.Order;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface OrderBy {
	/**
	 * 14 Field name 15
	 */
	String value();

	Order order() default Order.ASCENDING;

	SortPhase[] phase() default SortPhase.AFTER_CONVERT;
}
