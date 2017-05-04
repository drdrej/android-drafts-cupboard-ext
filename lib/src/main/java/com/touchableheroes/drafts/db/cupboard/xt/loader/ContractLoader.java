package com.touchableheroes.drafts.db.cupboard.xt.loader;

import com.touchableheroes.drafts.db.cupboard.xt.loader.impl.KeyValue;

/**
 * Created by asiebert on 26.02.16.
 */
public interface ContractLoader {

    public void load(final ContractLoaderCallback callbacks,
                     final KeyValue... vars);

}
