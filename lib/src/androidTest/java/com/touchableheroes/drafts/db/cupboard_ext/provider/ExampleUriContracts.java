package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

/**
 * Created by asiebert on 25.04.2017.
 */

public enum ExampleUriContracts {

    @UriMatcherContract(
            type = ExampleEntity.class,
            path = "/all" )
    ALL_ENTITIES

}
