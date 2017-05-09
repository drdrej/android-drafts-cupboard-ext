package com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.DeleteContract;

/**
 * Created by asiebert on 09.05.2017.
 */

class DeleteParams
        extends AbstractSelectionParams {

    private final DeleteContract config;

    private final String selection;

    public DeleteParams(
            final DeleteContract opContract,
            final String selection) {
        this.config = opContract;
        this.selection = createSelection( selection );
    }

    private String createSelection(String selection) {
        return createSelection(config.selection(), selection);
    }

    public String selection() {
        return selection;
    }

    public String[] args(final String[] args) {
        return createArgs(config.args(), args );
    }

}
