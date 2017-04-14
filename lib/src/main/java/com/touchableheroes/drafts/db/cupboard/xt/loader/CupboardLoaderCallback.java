package com.touchableheroes.drafts.db.cupboard.xt.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.touchableheroes.drafts.cupboard.xt.contracts.ContractUriUtil;
import com.touchableheroes.drafts.cupboard.xt.contracts.CupboardLoaderContract;
import com.touchableheroes.drafts.mr.blackbox.semantics.LoaderIDs;

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

    public CupboardLoaderCallback(final T type, final Context ctx) {
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

    public Loader onCreateLoader(Enum<T> loaderId) {
        final UriTemplate uriTemplate =  ContractUriUtil.uriByState(LoaderIDs.CURRENT_TRACK);
        final Uri uriCall = uriTemplate.create();


        final String[] projection = null; // projection(def); // --> Map auf die Entity

        // TODO: Parameter-Handling ueberlegen
        // TODO: Loader-Aufruf an der richtigen Stelle einbauen! (nicht hier :) im Fragment)
        // ! mache hier bewusst einen kaputten code rein, damit ich die stelle wieder finde.

        String selection = null; // --> where-Params
        String[] selectionArgs = null; // --> where-Names.
        String sortOrder = null;

        // Cursor -> d.h. muss eine Query-Uri sein!

        return new CursorLoader(
                ctx, uriCall, projection, selection,
                selectionArgs, sortOrder);
    }

    protected String[] projection(final CupboardLoaderContract def) {
        final List<String> names = new ArrayList<>();

        for( final Field field : def.type().getFields() ) {
            if( isEntityField(field) ) {
                names.add(field.getName());
            }
        }

        final String[] rval = new String[ names.size() ];
        return names.toArray(rval);
    }

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
