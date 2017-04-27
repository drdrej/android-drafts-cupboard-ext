package com.touchableheroes.drafts.db.cupboard.xt.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

//import com.touchableheroes.drafts.cupboard.xt.contracts.ContractUriUtil;
//import com.touchableheroes.drafts.cupboard.xt.contracts.UriMatcherContract;
//import com.touchableheroes.drafts.mr.blackbox.semantics.LoaderIDs;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.util.ContractUriUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;

/**
 * supports only v4 version
 *
 * Created by asiebert on 27.02.16.
 */
public abstract class CupboardLoaderCallback<T extends Enum<T>, R>
        implements LoaderCallbacks<Cursor> {


    private final T type;
    private final Context ctx;

    public CupboardLoaderCallback(final T type,
                                  final Context ctx) {
        this.type  = type;
        this.ctx = ctx;
    }


    @Override
    public final Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if( type.ordinal() != id ) {
            throw new UnsupportedOperationException( "Loader and Callback needs to supports the same id (check passed enum)" );
        }

        return onCreateLoader(type);
    }

    public Loader onCreateLoader(final Enum<T> loaderId) {
        // TODO: Parameter-Handling ueberlegen

        final UriMatcherContract contract = EnumTool.withEnum(loaderId).annotation(UriMatcherContract.class);
        final QueryContract query = contract.operations().query();

        final Uri uriCall = ContractUriUtil.createQuery(loaderId);

        final String[] projection = query.projection();
        final String selection = query.selection();
        final String[] selectionArgs = null;
        final String sortOrder = query.orderBy();

        return new CursorLoader(
                ctx, uriCall, projection, selection,
                selectionArgs, sortOrder);
    }

    /*
    protected String[] projection(final UriMatcherContract def) {
        final List<String> names = new ArrayList<>();

        for( final Field field : def.type().getFields() ) {
            if( isEntityField(field) ) {
                names.add(field.getName());
            }
        }

        final String[] rval = new String[ names.size() ];
        return names.toArray(rval);
    }
    */

    private boolean isEntityField(final Field field) {
        return (field.isAccessible() && !field.isSynthetic());
    }


    @Override
    public final void onLoadFinished(final Loader<Cursor> loader,
                                     final Cursor data) {

        final CursorIteratorConverter<R> rval = new CursorIteratorConverter<R>(data);
        onLoadFinished(rval);
    }

    public abstract void onLoadFinished( final CursorIteratorConverter<R> data );

    @Override
    public final void onLoaderReset(final Loader<Cursor> loader) {
        onLoaderReset();
    }

    public abstract void onLoaderReset();

}
