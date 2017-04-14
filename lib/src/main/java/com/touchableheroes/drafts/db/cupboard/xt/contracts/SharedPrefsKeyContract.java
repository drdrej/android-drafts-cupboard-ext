package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by asiebert on 04.03.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SharedPrefsKeyContract {

    String key() default "";

    Type type();

    String scopeId() default "";

    public enum Type {
        INT,
        LONG,
        STRING
    }
}
