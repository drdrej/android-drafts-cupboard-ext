package com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard;

import com.touchableheroes.drafts.core.tools.ArrayTool;
import com.touchableheroes.drafts.core.tools.StringTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;

/**
 * Created by asiebert on 09.05.2017.
 */

public class QueryParams
        extends AbstractSelectionParams {

    private final QueryContract query;
    private final String[] projection;
    private final String selection;
    private final String sortBy;

    public QueryParams(
            final QueryContract query,
            final String[] projection,
            final String selection,
            final String sortOrder) {
        this.query = query;

        this.projection = mergeProjection(projection);
        this.selection = createSelection(selection);
        this.sortBy = createSort( sortOrder );
    }

    private String createSort(
            final String sortOrder) {
        if( !StringTool.isEmpty(sortOrder) ) {
            return sortOrder;
        }

        if( !StringTool.isEmpty(query.orderBy()) ) {
            return query.orderBy();
        }

        return null;
    }

    private String createSelection(
            final String selection) {
        return super.createSelection(query.selection(), selection);
    }

    private String[] mergeProjection(final String[] dynamic) {
        if( !ArrayTool.isEmpty(dynamic) ) {
            return dynamic;
        }

        if( !ArrayTool.isEmpty(query.projection()) ) {
            return query.projection();
        }

        return null;
    }

    public String[] projection() {
        return projection;
    }

    public String selection() {
        return selection;
    }

    public String[] args(final String[] args) {
        return createArgs(query.args(), args);
    }

    public String orderBy() {
        return this.sortBy;
    }
}
