package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.db.cupboard.xt.commands.DbCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.InsertDbCommand;

/**
 * Created by asiebert on 27.04.2017.
 */

public @interface InsertContract {

    Class<?> entity() default Void.class;

    Class<? extends InsertDbCommand> command();
}
