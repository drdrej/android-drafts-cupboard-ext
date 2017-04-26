package com.touchableheroes.drafts.db.cupboard.xt.contracts;

/**
 * Created by asiebert on 26.04.2017.
 */

public @interface ContentProviderQueryContract {

    String[] projection() default {};
    String[] selection() default {}; //selectionArgs).
    String orderBy() default "";

}
