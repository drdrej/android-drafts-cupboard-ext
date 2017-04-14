package com.touchableheroes.drafts.db.cupboard.xt.loader.impl;

import android.support.v4.app.LoaderManager;

import com.touchableheroes.drafts.db.cupboard.xt.loader.LoaderTX;

/**
 * Created by asiebert on 26.02.16.
 */
public class DBLoaderTX implements LoaderTX<LoaderManager, LoaderManager.LoaderCallbacks> {

    private final LoaderManager mgr;

    public DBLoaderTX( final LoaderManager mgr ){
        this.mgr = mgr;
    }

    @Override
    public void load(final Enum loaderId,
                     final LoaderManager.LoaderCallbacks callbacks,
                     final String... vars ) {
        mgr.initLoader(loaderId.ordinal(), null, callbacks);
    }

}
