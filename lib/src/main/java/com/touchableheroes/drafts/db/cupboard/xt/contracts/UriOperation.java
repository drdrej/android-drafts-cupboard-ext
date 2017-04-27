package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.db.cupboard.xt.commands.RawQueryDbCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard.CupboardBasedInsertCommand;

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

    InsertContract insert() default @InsertContract(
            command = CupboardBasedInsertCommand.class
    );

    QueryContract query() default @QueryContract(
            command = RawQueryDbCommand.class
    );

    Class<?> update() default Void.class;

    Class<?> delete() default Void.class;

}
