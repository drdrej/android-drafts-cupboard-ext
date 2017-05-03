package com.touchableheroes.drafts.db.cupboard.xt.cursor;

import android.database.Cursor;

import com.touchableheroes.drafts.core.obj.Structure;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.defaults.Defaults;
import com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping.SQLiteTypeMapping;
import com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping.SQLiteTypeMappingManager;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.SQliteTypeContract;


/**
 * Created by asiebert on 02.05.2017.
 */

public class ConverterCursorList<T extends Enum> extends CursorList {

    private final Class<T> projection;

    public ConverterCursorList(
            final Cursor src,
            final Class<T> projection) {
        super(src);
        this.projection = projection;
    }

    @Override
    public Structure<T> parse( final Cursor cursor ) {
        final Enum[] values = EnumTool.withEnum(projection).values();
        final Structure rval = new Structure<>(projection);

        for (final Enum key : values) {
            final int cidx = cursor.getColumnIndex(key.name());

            if( cidx < 0 ) {
                System.err.println( ">> Couldn't map [key= "+ key.name() +"], no cursor-column found." );
                continue;
            }

            final EnumTool.EnumWrapper keyWrap = EnumTool.withEnum(key);

            SQliteTypeContract annotation = keyWrap.annotation(SQliteTypeContract.class);
            if(annotation == null) {
               annotation = Defaults.DEFAULT_SQLITE_TYPE_MAPPING;
            }

            final Class<? extends SQLiteTypeMapping> type = annotation.type();
            final SQLiteTypeMapping mapper = SQLiteTypeMappingManager.instance(type);

            rval.modify(key,
                    mapper.to(cursor, cidx));
        }

        return rval;
    }
}
