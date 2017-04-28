package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard.CupboardBasedQueryQueryCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.raw.RawQueryQueryCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard.CupboardBasedInsertCommand;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.DbContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.InsertContract;
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
            operations = @UriOperation(
                    insert = @InsertContract(
                            entity = ExampleEntity.class,
                            command = CupboardBasedInsertCommand.class
                    ),
                    query = @QueryContract(
                            command = RawQueryQueryCommand.class,
                            sql = "SELECT * FROM ExampleEntity"
                    )
            )
    )
    ENTITY,

    @UriMatcherContract(
            operations = @UriOperation(
                    query = @QueryContract(
                            command = CupboardBasedQueryQueryCommand.class,
                            entity = ExampleEntity.class
                    )
            )
    )
    ENTITY_cupboardbased,

    @UriMatcherContract(
            operations = @UriOperation(
                    query = @QueryContract(
                            command = RawQueryQueryCommand.class,
                            sql = "SELECT * FROM a" // table not exists
                    )
            )
    )
    BROKEN_RAW_QUERY,

    @UriMatcherContract(
            operations = @UriOperation(
                    query = @QueryContract(
                            command = CupboardBasedQueryQueryCommand.class,
                            entity  = ExampleEntity.class
                    )
            )
    )
    ENTITIES

}
