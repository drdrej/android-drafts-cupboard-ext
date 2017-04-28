package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.db.cupboard.xt.commands.InsertCommand;

/**
 * Created by asiebert on 27.04.2017.
 */

public @interface InsertContract {

    Class<?> entity() default Void.class;

    Class<? extends InsertCommand> command();
}
