package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.commands.RawQueryDbCommand;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.DbContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriOperation;

/**
 * Created by asiebert on 25.04.2017.
 */
@DbContract(
        name="example-db",
        version=1,
        entities = {
                ExampleEntity.class
        }
)
public enum ExampleUris {

    @UriMatcherContract(
            type = ExampleEntity.class, // Type muss hier raus. ist eher die gruppe der Entitäten
            operations = @UriOperation(
                    query = @QueryContract(
                            command = RawQueryDbCommand.class,
                            sql = "SELECT * FROM ExampleEntity"
                    )
            )
    )
    ENTITY,

    @UriMatcherContract(
            type = ExampleEntity.class, // Type muss hier raus. ist eher die gruppe der Entitäten
            operations = @UriOperation(
                    query = @QueryContract(
                            command = RawQueryDbCommand.class,
                            sql = "SELECT * FROM a" // table not exists
                    )
            )
    )
    BROKEN_RAW_QUERY,

    @UriMatcherContract(
            type = ExampleEntity.class
    )
    ENTITIES

}
