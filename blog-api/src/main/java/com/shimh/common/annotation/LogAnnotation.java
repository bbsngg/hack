package com.shimh.common.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author CSE
 * <p>
 * 2019年4月18日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}
