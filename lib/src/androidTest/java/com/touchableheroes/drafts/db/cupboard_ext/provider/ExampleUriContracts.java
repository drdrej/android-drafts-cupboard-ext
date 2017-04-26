package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriOperation;

/**
 * Created by asiebert on 25.04.2017.
 */

public enum ExampleUriContracts {

    @UriMatcherContract(
            type = ExampleEntity.class,
            path = "/entity",
            operations = @UriOperation(

            )
    )
    ENTITY_BY_ID,

    @UriMatcherContract(
            type = ExampleEntity.class,
            path = "/all",
            operations = @UriOperation(
                
            ))
    ALL_ENTITIES

}
