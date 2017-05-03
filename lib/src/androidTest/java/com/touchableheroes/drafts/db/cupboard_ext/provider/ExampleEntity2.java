package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.SQliteTypeContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.types.Entity;
import com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping.StringSQLite;

/**
 * Key/Value Object stored in SQLIte as example-entity.
 *
 * Created by asiebert on 25.04.2017.
 */
public enum ExampleEntity2 implements Entity {

    _id,

    _createdAt,
    _lastChangeAt,

    @SQliteTypeContract(
            type = StringSQLite.class
    )
    name,
    value

}
