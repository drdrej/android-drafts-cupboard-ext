package com.touchableheroes.drafts.db.cupboard.xt.contracts;

/**
 * Created by asiebert on 27.04.2017.
 */

public @interface DbContract {

    String name();
    int version();

    Class[] entities();

    // Class<? extends Enum>[] entities() default {};

}
