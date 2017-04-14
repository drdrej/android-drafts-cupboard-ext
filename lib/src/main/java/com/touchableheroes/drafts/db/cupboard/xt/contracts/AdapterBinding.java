package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Example:
 *      AdapterBinding binding = EnumTool.withEnum(cursorBindingSemantics).annotation(AdapterBinding.class);
 *
 * Created by asiebert on 16.04.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AdapterBinding {

    Class<?> type();
}
