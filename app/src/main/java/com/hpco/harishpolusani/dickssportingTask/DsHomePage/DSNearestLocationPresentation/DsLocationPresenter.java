package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationPresentation;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.DsVenues;
import com.hpco.harishpolusani.dickssportingTask.HttpHandlerUtils;
import com.hpco.harishpolusani.dickssportingTask.HttpManager;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by harishpolusani on 12/13/17.
 */

public class DsLocationPresenter implements  DsLocationsContract.LocationPresenter {
    private DsLocationsContract.LocationView mView;
    private  HttpManager httpManager;
    private CompositeSubscription mSubscriptions;

    protected DsLocationPresenter(DsLocationsContract.LocationView view){
        mView = checkNotNull(view);
        httpManager = HttpManager.getInstance();
        mSubscriptions = new CompositeSubscription();
    }

    /**
     * fetchDSApi method will make a call to https://movesync-qa.dcsg.com/dsglabs/mobile/api/venue/ Api .
     * a callback subscriber is added.
     * adding subscription to CompositeSubscription inorder to manage them .
     */
    @Override
    public void fetchDSApi() {
       Subscription subscription= httpManager.fetchDSApi(HttpHandlerUtils.getDsApiUrl("venue"),new Subscriber<DsVenues>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                 mView.dsApiFailure();
            }

            @Override
            public void onNext(DsVenues dsVenues) {
                mView.dsApiSuccess(dsVenues);
            }
        });
        mSubscriptions.add(subscription);
    }

    /**
     * unsubscribe all the subscription after the view destryoyed
     *
     */
    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
