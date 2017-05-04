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

import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.util.UriMatcherContractUtil;
import com.touchableheroes.drafts.db.cupboard.xt.util.ContractUriUtil;
import com.touchableheroes.drafts.db.cupboard.xt.util.SelectionArgsTool;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;

/**
 * supports only v4 version
 *
 * Created by asiebert on 27.02.16.
 */
public abstract class ContractLoaderCallback<T extends Enum<T>, R>
        implements LoaderCallbacks<Cursor> {

    private final T type;
    private final Context ctx;
    private final String[] queryArgs;

    public ContractLoaderCallback(final T type,
                                  final Context ctx,
                                  final String[] queryArgs) {
        this.type  = type;
        this.ctx = ctx;
        this.queryArgs = queryArgs;
    }

    @Override
    public final Loader<Cursor> onCreateLoader(
            final int id,
            final Bundle args) {

        if( type.ordinal() != id ) {
            throw new UnsupportedOperationException( "ContractLoader and Callback needs to supports the same id (check passed enum)" );
        }

        return onCreateLoader(type);
    }

    public Loader onCreateLoader(final Enum<T> loaderId) {
        // TODO: Parameter-Handling ueberlegen

        final UriMatcherContract contract = UriMatcherContractUtil.load(loaderId);
        final QueryContract query = contract.operations().query();

        final Uri uriCall = ContractUriUtil.createQuery(loaderId);

        final String[] projection = query.projection();
        final String selection = query.selection();

        final String[] selectionArgs = SelectionArgsTool.merge(queryArgs, query.args());
        final String sortOrder = query.orderBy();

        return new CursorLoader(
                ctx, uriCall, projection, selection,
                selectionArgs, sortOrder);
    }

    @Override
    public final void onLoadFinished(final Loader<Cursor> loader,
                                     final Cursor data) {
        // final CursorIteratorConverter<R> rval = new CursorIteratorConverter<R>(data);
        onLoadFinished((R) data);
    }

    public abstract void onLoadFinished( final R data );

    @Override
    public final void onLoaderReset(final Loader<Cursor> loader) {
        onLoaderReset();
    }

    public abstract void onLoaderReset();

    public T getContract() {
        return type;
    }
}
