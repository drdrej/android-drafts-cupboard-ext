package com.touchableheroes.drafts.db.cupboard.xt.loader.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.touchableheroes.drafts.core.obj.Structure;
import com.touchableheroes.drafts.db.cupboard.xt.loader.ContractLoaderCallback;
import com.touchableheroes.drafts.db.cupboard.xt.loader.ContractLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asiebert on 26.02.16.
 */
public class SimpleContractLoader<T extends Enum>
       implements ContractLoader {

    private final LoaderManager mgr;
    private final Context ctx;

    private final Structure loaders;

    public SimpleContractLoader(final LoaderManager mgr,
                                final Context ctx,
                                final Class<T> type){
        this.mgr = mgr;
        this.ctx = ctx;

        loaders = new Structure(type);
    }

    @Override
    public void load(final ContractLoaderCallback callbacks,
                     final KeyValue... vars ) {

        final Bundle bundle = toBundle(vars);
/*
        if(Tracer.isDevMode()) {
            if( vars.length < 1) {
                throw new UnsupportedOperationException( "PLEASE IMPL!" );
            }
        }
*/

        callbacks.inject(ctx);

        final Enum contract = callbacks.getContract();
        mgr.initLoader(contract.ordinal(), bundle, callbacks);

        final Load load = new Load();
        load.bundle = bundle;
        load.callbacks = callbacks;

        loaders.modify(contract, load);
    }

    public void reload(final Enum contract) {
        final Load load = (Load) this.loaders.get(contract);

        if( load != null ) {
            load.callbacks.inject(ctx);
            mgr.restartLoader(contract.ordinal(), load.bundle, load.callbacks);
        }

        System.err.println( "LOADER [=" + contract + "] restarted." );
    }

    private Bundle toBundle(final KeyValue... vars) {
        final Bundle bundle = new Bundle();

        /* TODO: impl.
        for (final KeyValue kv : vars) {
           kv.key()
        }
        */

        return bundle;
    }

    static class Load {
        private Bundle bundle;
        private ContractLoaderCallback callbacks;
    }

}
