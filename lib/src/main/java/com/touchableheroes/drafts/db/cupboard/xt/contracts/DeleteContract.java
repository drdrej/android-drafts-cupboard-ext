package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.db.cupboard.xt.commands.DeleteCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.InsertCommand;

/**
 * Created by asiebert on 27.04.2017.
 */
public @interface DeleteContract {

    Class<?> entity() default Void.class;

    Class<? extends DeleteCommand> command();

    String selection() default "";
}
