package com.hpco.harishpolusani.dickssportingTask;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.DsVenues;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by harishpolusani on 12/16/17.
 */

public interface HttpService {

    //Fetcth the DickSportingAPi
    @GET
    Observable<DsVenues> fetchDSApi(@Url String url);
}
