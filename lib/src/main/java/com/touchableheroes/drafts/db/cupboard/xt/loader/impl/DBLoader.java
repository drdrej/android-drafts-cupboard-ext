package com.touchableheroes.drafts.db.cupboard.xt.loader.impl;

import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.LoaderManager;

import com.touchableheroes.drafts.db.cupboard.xt.loader.Loader;

/**
 * Created by asiebert on 26.02.16.
 */
public class DBLoader implements Loader<LoaderManager, LoaderManager.LoaderCallbacks> {

    private final LoaderManager mgr;

    public DBLoader(final LoaderManager mgr ){
        this.mgr = mgr;
    }

    @Override
    public void load(final Enum loaderId,
                     final LoaderManager.LoaderCallbacks callbacks,
                     final KeyValue... vars ) {
        Bundle bundle = new Bundle();

        mgr.initLoader(loaderId.ordinal(), bundle, callbacks);
    }

}
