package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by asiebert on 26.04.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UriOperation {
    Class<?> insert() default Void.class;
    Class<?> delete() default Void.class;
    Class<?> query() default Void.class;
    Class<?> update() default Void.class;
}
