package com.touchableheroes.drafts.db.cupboard.xt;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard_ext.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by asiebert on 15.04.2017.
 */

public class DbConfig {

    private final String name;
    private final int version;
    private final Iterable<Class<?>> entities;

    private final Class<? extends Enum> uriContracts;


    public DbConfig(final String name,
                    final int version,
                    final Class<?>[] entities,
                    final Class<? extends Enum> uriContracts) {
        this.name = name;
        this.version = version;
        this.entities = Arrays.asList(entities);

        this.uriContracts = uriContracts;
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

    public Iterator<Enum> uris () {
        final EnumSet values = EnumSet.allOf(uriContracts);
        return values.iterator();
    }
}
