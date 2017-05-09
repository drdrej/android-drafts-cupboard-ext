package com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard;

import android.database.sqlite.SQLiteDatabase;

import com.touchableheroes.drafts.core.logger.Fact;
import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.commands.DeleteCommand;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.DeleteContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.InsertContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

import static nl.qbusict.cupboard.CupboardFactory.*;
/**
 * Created by asiebert on 28.04.2017.
 */

public class CupboardBasedDeleteCommand
       extends DeleteCommand {


    public CupboardBasedDeleteCommand(final SQLiteDatabase writable) {
        super(writable);
    }

    @Override
    public int exec(
            final Enum uri,
            final String selection,
            final String[] selectionArgs) {
        final UriMatcherContract contract = EnumTool.withEnum(uri).annotation(UriMatcherContract.class);

        final DeleteContract opContract = contract.operations().delete();
        final Class<?> entity = opContract.entity();


        Tracer.prove(new Fact() {

            @Override
            public void check() {
                if( entity == Void.class ) {
                    if( Tracer.isDevMode() ) {
                        throw new IllegalStateException( "Delete-Command need an entity-class. [enum = " + uri.name() + "]" );
                    } /* else {
                        return -1;
                    } */
                }

            }
        });

        /*
        if (id < 1) {
            return new long[] { cupboard().withDatabase( writable() ).put( entity, values) };
        } else {
            cupboard().withDatabase( writable() ).update(entity, values);
            return new long[] { id };
        }
        */

        final DeleteParams p = new DeleteParams( opContract, selection );

        return cupboard().withDatabase(writable()).delete(entity,
                p.selection(),
                p.args(selectionArgs) );
    }
}
