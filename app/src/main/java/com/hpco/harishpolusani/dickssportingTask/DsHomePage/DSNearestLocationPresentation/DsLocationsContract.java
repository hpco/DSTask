package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationPresentation;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.DsVenues;

/**
 * Created by harishpolusani on 12/16/17.
 */

public class DsLocationsContract {

    interface LocationView{
        void dsApiSuccess(DsVenues dsVenues);
        void  dsApiFailure();
    }
    interface LocationPresenter{
        void fetchDSApi();
        void unSubscribe();
    }
}
