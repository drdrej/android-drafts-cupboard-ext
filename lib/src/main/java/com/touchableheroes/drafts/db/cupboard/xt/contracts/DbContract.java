package com.touchableheroes.drafts.db.cupboard.xt.contracts;

/**
 * Created by asiebert on 27.04.2017.
 */

public @interface DbContract {

    int version();

    Class[] entities();

    //TODO: Class<? extends Enum>[] entities() default {};

}
