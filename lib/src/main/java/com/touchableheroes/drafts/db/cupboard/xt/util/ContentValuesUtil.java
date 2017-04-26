package com.touchableheroes.drafts.db.cupboard.xt.util;

import android.content.ContentValues;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.types.Entity;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 26.04.2017.
 */

public class ContentValuesUtil {

    public static ContentValues entityToContentValues(final Entity entity) {
        ContentValues values = new ContentValues();

        Class<Entity> cl = (Class<Entity>) entity.getClass();
        cupboard().getEntityConverter(cl).toValues(entity, values );

        return values;
    }



}
