package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.db.cupboard.xt.commands.DbCommand;

/**
 * Created by asiebert on 26.04.2017.
 */

public @interface QueryContract {

    String[] projection() default {};

    String selection() default ""; //selectionArgs[]).

    String orderBy() default "";

    Class<? extends DbCommand> command();

    String sql() default "";
}
