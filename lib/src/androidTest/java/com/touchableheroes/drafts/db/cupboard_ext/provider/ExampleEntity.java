package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Key/Value Object stored in SQLIte as example-entity.
 *
 * Created by asiebert on 25.04.2017.
 */
public class ExampleEntity implements Entity {

    public Long _id;

    public Date _createdAt;
    public Date _lastChangeAt;

    public String name;
    public String value;

}
