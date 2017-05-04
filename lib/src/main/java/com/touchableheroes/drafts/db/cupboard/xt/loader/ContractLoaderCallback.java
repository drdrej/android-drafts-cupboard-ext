package com.touchableheroes.drafts.db.cupboard.xt.loader;

import
        android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.util.UriMatcherContractUtil;
import com.touchableheroes.drafts.db.cupboard.xt.util.ContractUriUtil;
import com.touchableheroes.drafts.db.cupboard.xt.util.SelectionArgsTool;

import java.lang.ref.WeakReference;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;


/**
 * supports only v4 version
 *
 * Created by asiebert on 27.02.16.
 */
public abstract class ContractLoaderCallback<T extends Enum<T>, R>
        implements LoaderCallbacks<Cursor> {

    private final T type;
    private final String[] queryArgs;

    private WeakReference<Context> ctx;

    public ContractLoaderCallback(final T type,
                                  final String[] queryArgs) {
        this.type  = type;
        this.ctx = new WeakReference<Context>(null);
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

        final Context ctx = this.ctx.get();
        if( ctx == null ) {
            throw new IllegalStateException( "Context needs to be reassigned. Because Referense is loosed." );
        }

        return new CursorLoader(
                this.ctx.get(), uriCall, projection, selection,
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

    public void onLoaderReset() {
        ;
    }

    public T getContract() {
        return type;
    }

    public void inject(final Context ctx) {
        this.ctx = new WeakReference<Context>(ctx);
    }

}
