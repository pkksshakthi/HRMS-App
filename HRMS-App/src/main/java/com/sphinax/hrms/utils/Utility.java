package com.sphinax.hrms.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

//    public static void addFragment(Activity activity, int containerId, FragmentManager fragmentManager, android.support.v4.app.Fragment fragment, boolean needBackstack, Bundle bundle) {
//        try {
//            android.support.v4.app.FragmentTransaction ftNavigation = fragmentManager.beginTransaction();
//            if (bundle != null) {
//                fragment.setArguments(bundle);
//            }
//            ftNavigation.replace(containerId, fragment);
////            ftNavigation.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            if (needBackstack) {
//                ftNavigation.addToBackStack(null);
//            }
//            ftNavigation.commit();
//            if (containerId == R.id.content_frame) {
//                currentFragment = fragment;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void addFragment(Activity activity, int containerId, FragmentManager fragmentManager, android.support.v4.app.Fragment fragment, boolean needBackstack, Bundle bundle,String fragmentName) {
        try {
           android.support.v4.app.FragmentTransaction ftNavigation = fragmentManager.beginTransaction();
            ftNavigation.setCustomAnimations(R.anim.left_enter, R.anim.right_out);
            ftNavigation.add(fragment,fragmentName);
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            ftNavigation.replace(containerId, fragment);
            if (needBackstack) {
                ftNavigation.addToBackStack(null);
            }
            ftNavigation.commit();
            if (containerId == R.id.content_frame) {
                currentFragment = fragment;
                Log.d(TAG, currentFragment.getTag());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void callErrorScreen(Activity activity, int containerId, FragmentManager fragmentManager, android.support.v4.app.Fragment fragment, boolean needBackstack, Bundle bundle,String fragmentName) {
        try {
        android.support.v4.app.FragmentTransaction ftNavigation = fragmentManager.beginTransaction();
        ftNavigation.setCustomAnimations(R.anim.left_enter, R.anim.right_out);
        ftNavigation.add(fragment,fragmentName);
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        ftNavigation.replace(containerId, fragment);
        if (needBackstack) {
            ftNavigation.addToBackStack(null);
        }
        ftNavigation.commit();
        if (containerId == R.id.content_frame) {
            currentFragment = fragment;
            Log.d(TAG, currentFragment.getTag());
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    // Custom Toast Method
    public static void  showCustomToast(Context context, View view, String error) {

        // Layout Inflater for inflating custom view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the layout over view
        View layout = inflater.inflate(R.layout.custom_toast, view.findViewById(R.id.toast_root));
        // Get TextView id and set error
        TextView text =  layout.findViewById(R.id.toast_error);
        text.setText(error);

        Toast toast = new Toast(context);// Get Toast Context
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set
             toast.setDuration(Toast.LENGTH_SHORT);// Set Duration
        toast.setView(layout); // Set Custom View over toast

        toast.show();// Finally show toast
    }

}
