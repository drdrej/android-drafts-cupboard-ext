package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.commands.RawQueryDbCommand;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriOperation;

/**
 * Created by asiebert on 25.04.2017.
 */
public enum ExampleUriContracts {

    @UriMatcherContract(
            type = ExampleEntity.class, // Type muss hier raus. ist eher die gruppe der Entitäten
            path = "/entity",
            operations = @UriOperation(
                    query = @QueryContract(
                            command = RawQueryDbCommand.class,
                            sql = "SELECT * FROM a"
                    )
            )
    )
    ENTITY_BY_ID,

    @UriMatcherContract(
            type = ExampleEntity.class,
            path = "/entities" )
    ALL_ENTITIES

}
