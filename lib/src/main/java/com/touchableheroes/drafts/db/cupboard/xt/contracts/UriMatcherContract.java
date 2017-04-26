package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.db.cupboard.xt.commands.CupboardBasedQueryDbCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by asiebert on 27.02.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UriMatcherContract {

    Class<?> type();

    String path();

    UriOperation operations() default @UriOperation(
        query = CupboardBasedQueryDbCommand.class
    );
}
