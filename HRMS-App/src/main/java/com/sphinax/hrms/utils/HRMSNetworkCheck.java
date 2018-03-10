package com.sphinax.hrms.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class HRMSNetworkCheck {

    private static Context context;

    public static boolean checkInternetConnection(Context context) {
        try {
            HRMSNetworkCheck.context = context;
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isAvailable() && ni.isConnected();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
