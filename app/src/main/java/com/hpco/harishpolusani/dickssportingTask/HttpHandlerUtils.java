package com.hpco.harishpolusani.dickssportingTask;

/**
 * Created by harishpolusani on 12/16/17.
 */

public class HttpHandlerUtils {
    //This string should be placed in String.xml
    // the query parameters can be changed accroding to the need
   private static String ds_api= "dsglabs/mobile/api/venue/";

    // the query parameters can be changed accroding to the need
    // inthis case it is venue ,but it can be changed as per our need
    public static String getDsApiUrl(String query) {
        return String.format("%s%s",HttpManager.BASE_URL,ds_api);
    }


}
