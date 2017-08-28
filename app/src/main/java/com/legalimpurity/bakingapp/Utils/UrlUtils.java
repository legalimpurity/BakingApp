package com.legalimpurity.bakingapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rajatkhanna on 21/08/17.
 */

public final class UrlUtils {
    public static final String BAKING_API_ROOT_URL = "https://d17h27t6h515a5.cloudfront.net/";
    public static final String BAKING_API_IMAGE_APPEND = "";

    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
