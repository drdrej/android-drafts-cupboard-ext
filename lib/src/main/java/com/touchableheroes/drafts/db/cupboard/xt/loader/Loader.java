package com.touchableheroes.drafts.db.cupboard.xt.loader;

import com.touchableheroes.drafts.db.cupboard.xt.loader.impl.KeyValue;

/**
 * Created by asiebert on 26.02.16.
 */
public interface Loader<L, C> {

    public void load(final Enum loaderId, final C callbacks, final KeyValue... vars);

}
