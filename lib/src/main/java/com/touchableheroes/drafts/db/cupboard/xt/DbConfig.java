package com.touchableheroes.drafts.db.cupboard.xt;

import com.touchableheroes.drafts.db.cupboard_ext.R;

import java.util.List;

/**
 * Created by asiebert on 15.04.2017.
 */

public class DbConfig {

    private final String name;
    private final int version;
    private final Iterable<Class<?>> entities;


    public DbConfig(final String name,
                    final int version,
                    final List<Class<?>> entities) {
        this.name = name;
        this.version = version;
        this.entities = entities;
    }

    public String name() {
        return name;
    }

    public int version() {
        return version;
    }

    public Iterable<Class<?>> entities() {
        return this.entities;
    }
}
