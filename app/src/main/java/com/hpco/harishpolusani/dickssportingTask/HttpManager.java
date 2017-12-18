package com.hpco.harishpolusani.dickssportingTask;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.DsVenues;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by harishpolusani on 12/16/17.
 */

public class HttpManager {
    public static final String BASE_URL = "https://movesync-qa.dcsg.com/";
    protected HttpService httpService;
    protected volatile static HttpManager INSTANCE;

    /**
     * this constructor builds retrofit instance .
     */
   private HttpManager(){
       OkHttpClient.Builder builder = new OkHttpClient.Builder();
       Retrofit retrofit = new Retrofit.Builder()
               .client(builder.build())
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
               .baseUrl(BASE_URL)
               .build();

       httpService = retrofit.create(HttpService.class);
    }

    /**
     *
     * @return returns the singletonInstance of HttpManager
     */
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     *
     * @param Url    Api Url (can be formated as per need)
     * @param dsSubcriber subscriber that consumes the emitted data from observable .
     * @return returns a subscription in order to add it to the pool of subscriptions
     */
    public Subscription fetchDSApi(String Url,Subscriber<DsVenues> dsSubcriber){
         return httpService.fetchDSApi(Url).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(dsSubcriber);
    }
}
