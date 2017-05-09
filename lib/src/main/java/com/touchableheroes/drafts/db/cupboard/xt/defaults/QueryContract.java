package com.touchableheroes.drafts.db.cupboard.xt.defaults;

import com.touchableheroes.drafts.db.cupboard.xt.commands.QueryCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard.CupboardBasedQueryQueryCommand;

import java.lang.annotation.Annotation;

/**
 * Created by asiebert on 09.05.2017.
 */

public class QueryContract
       implements com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract {

    public static QueryContract DEFAULT = new QueryContract(null, null, null);

    private final String[] proj;
    private final String sel;
    private final String ord;

    public QueryContract(
            final String[] projection,
            final String selection,
            final String orderBy) {
        this.proj = projection;
        this.sel = selection;
        this.ord = orderBy;
    }

    @Override
    public String[] projection() {
        return proj;
    }

    @Override
    public String selection() {
        return sel;
    }

    @Override
    public String orderBy() {
        return ord;
    }

    @Override
    public Class<? extends QueryCommand> command() {
        return CupboardBasedQueryQueryCommand.class;
    }

    @Override
    public String sql() {
        return null;
    }

    @Override
    public Class<? extends Object> entity() {
        return null;
    }

    @Override
    public String[] args() {
        return new String[0];
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract.class;
    }

    public static com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract
                create(
                        final String[] projection,
                        final String selection,
                        final String orderBy) {
        return new QueryContract(projection, selection, orderBy);
    }
}
