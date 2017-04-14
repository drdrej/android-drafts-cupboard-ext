package com.touchableheroes.drafts.db.cupboard.xt.loader;

/**
 * Created by asiebert on 26.02.16.
 */
public interface LoaderTX<L, C> {

    public void load(final Enum loaderId, final C callbacks, final String... vars);

}
