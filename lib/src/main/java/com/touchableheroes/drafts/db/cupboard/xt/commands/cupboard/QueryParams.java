package com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard;

import com.touchableheroes.drafts.core.tools.ArrayTool;
import com.touchableheroes.drafts.core.tools.StringTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;

/**
 * Created by asiebert on 09.05.2017.
 */

public class QueryParams {

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
        final boolean a1 = StringTool.isEmpty(query.selection());
        final boolean a2 = StringTool.isEmpty(selection);

        if( a1 && a2 ) {
            return null;
        }

        if( a1 && !a2 ) {
            return selection;
        }

        if( !a1 && a2 ) {
            return query.selection();
        }

        return concat( "(", selection, ") and (", query.selection(), ")" );
    }

    private String concat(final String... values) {
        final StringBuilder b = new StringBuilder( 10 );

        int co = 0;
        for ( final String val : values ) {
            if( co > 0 ) {
                b.append( " " );
            }

            if( val != null ) {
                b.append( val );
            }

            b.append( " " );

            co++;
        }

        return b.toString();
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
        if( !ArrayTool.isEmpty(args) )
            return args;

        if( !ArrayTool.isEmpty(query.args()))
            return query.args();

        return null;
    }

    public String orderBy() {
        return this.sortBy;
    }
}
