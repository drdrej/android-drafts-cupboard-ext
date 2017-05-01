package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.db.cupboard.xt.commands.QueryCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by asiebert on 26.04.2017.
 */
public @interface QueryContract {

    String[] projection() default {};

    String selection() default ""; //selectionArgs[]).

    String orderBy() default "";

    Class<? extends QueryCommand> command();

    String sql() default "";

    Class<? extends Object> entity() default Void.class;

    String[] args() default {};
}
