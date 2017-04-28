package com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.commands.InsertDbCommand;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.InsertContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 27.04.2017.
 */
public class CupboardBasedInsertCommand
       extends InsertDbCommand {

    public CupboardBasedInsertCommand(final SQLiteDatabase db) {
        super(db);
    }

    @Override
    public long[] exec(final Enum uriEnum,
                       final long id,
                       final ContentValues values) {
        final UriMatcherContract contract = EnumTool.withEnum(uriEnum).annotation(UriMatcherContract.class);

        final InsertContract insertContract = contract.operations().insert();
        final Class<?> entity = insertContract.entity();

        if( entity == Void.class ) {
            if( Tracer.isDevMode() ) {
                throw new IllegalStateException( "Insert-Command need an entity-class. [enum = " + uriEnum.name() + "]" );
            } else {
                return new long[0];
            }
        }

        if (id < 1) {
            return new long[] { cupboard().withDatabase( writable() ).put( entity, values) };
        } else {
            cupboard().withDatabase( writable() ).update(entity, values);
            return new long[] { id };
        }
    }
}
