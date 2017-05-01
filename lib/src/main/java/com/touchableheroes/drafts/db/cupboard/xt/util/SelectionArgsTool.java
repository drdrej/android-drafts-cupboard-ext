package com.touchableheroes.drafts.db.cupboard.xt.util;

import com.touchableheroes.drafts.core.tools.ArrayTool;

/**
 * Created by asiebert on 28.04.2017.
 */
public class SelectionArgsTool {

    /**
     *
     * @return
     */
    public static String[] merge(final String[] son,
                                 final String[] parent) {
        if(ArrayTool.isEmpty(parent)) {
           return son;
        }

        if( son.length == parent.length ) {
            return son;
        } else if( son.length < parent.length ) {
            final String[] rval = new String[parent.length];

            // init
            System.arraycopy(parent, 0, rval, 0, parent.length);
            // overwriter with younger values
            System.arraycopy(son, 0, rval, 0, son.length);

            return rval;
        } else {
           throw new IllegalStateException( "son can have only same lenth(max) as parent." );
        }
    }
}
