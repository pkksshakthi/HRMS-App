package com.sphinax.hrms.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class Utility {

    private static final String TAG = "Utility";
    private static Utility instance;
    private static int whichFragment = 0;
    private static android.support.v4.app.Fragment currentFragment;

    private Utility() {
    }

    public static Utility getInstance() {
        if (instance == null)
            instance = new Utility();
        return instance;
    }


    /**
     * The method used for declaring SharedPreferences
     **/
    public static SharedPreferences getPreference(Activity activity) {
        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = activity.getSharedPreferences(Constants.PREFS_NAME, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sharedPreferences;
    }


    /** The method used for showing Toast Message  **/
    public static void showToastMessage(Context context, String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFragment(Activity activity, int containerId, FragmentManager fragmentManager, android.support.v4.app.Fragment fragment, boolean needBackstack, Bundle bundle) {
        try {
            android.support.v4.app.FragmentTransaction ftNavigation = fragmentManager.beginTransaction();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            ftNavigation.replace(containerId, fragment);
//            ftNavigation.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (needBackstack) {
                ftNavigation.addToBackStack(null);
            }
            ftNavigation.commit();
            if (containerId == R.id.content_frame) {
                currentFragment = fragment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
