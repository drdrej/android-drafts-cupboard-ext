package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

/**
 * Created by asiebert on 02.05.2017.
 */
public abstract class SQLiteTypeMappingManager<T> {

    public static SQLiteTypeMapping instance(
            final Class<? extends SQLiteTypeMapping> type ) {
        try {
            return type.newInstance();
        } catch( Throwable t) {
            throw new IllegalStateException( "Couldn't instantiate.", t );
        }
    }
}
