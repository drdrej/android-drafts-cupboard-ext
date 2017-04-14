package com.touchableheroes.drafts.db.cupboard.xt;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.CupboardContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.CupboardLoaderContract;

@CupboardContract(
        authority = "com.touchableheroes.mr.blackbox.tracks",
        entities = {String.class})
public enum LoaderIDs {

    /*

    example:
    @CupboardLoaderContract(
            type= CurrentTrack.class,
            path = "/tracks/#" )
    CURRENT_TRACK,

    @CupboardLoaderContract(
            type = TrackEntity.class,
            path = "/tracks" )
    ALL_TRACKS

*/

}
