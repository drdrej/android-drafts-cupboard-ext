package com.touchableheroes.drafts.db.cupboard.xt.loader.impl;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.touchableheroes.drafts.db.cupboard.xt.loader.ContractLoaderCallback;
import com.touchableheroes.drafts.db.cupboard.xt.loader.ContractLoader;

/**
 * Created by asiebert on 26.02.16.
 */
public class SimpleContractLoader
       implements ContractLoader {

    private final LoaderManager mgr;

    public SimpleContractLoader(final LoaderManager mgr ){
        this.mgr = mgr;
    }

    @Override
    public void load(final ContractLoaderCallback callbacks,
                     final KeyValue... vars ) {

        // TODO: fill from KeyValue Pairs...
        final Bundle bundle = new Bundle();

/*
        if(Tracer.isDevMode()) {
            if( vars.length < 1) {
                throw new UnsupportedOperationException( "PLEASE IMPL!" );
            }
        }
*/
        final Enum contract = callbacks.getContract();
        mgr.initLoader(contract.ordinal(), bundle, callbacks);
    }

}
