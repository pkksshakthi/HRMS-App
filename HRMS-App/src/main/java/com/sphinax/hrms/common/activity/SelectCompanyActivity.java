package com.sphinax.hrms.common.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.model.ServiceRequest;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SelectCompanyActivity extends AppCompatActivity {

    private ProgressDialog pdia;
    private Context context;
    private Ajax ajax;
   // private static ArrayList<Ajax> ajaxList;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selectcompany);
        context = this.getApplicationContext();


        if (!HRMSNetworkCheck.checkInternetConnection(getApplicationContext())) {
            Utility.showToastMessage(this, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
       fetchCompanyList();fetchBranchList();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
       // delayedHide(100);
    }






    /***Method used to get Company List from the OPS Service **/

    private void fetchCompanyList() {
        if (!HRMSNetworkCheck.checkInternetConnection(getApplicationContext())) {
            Utility.showToastMessage(this, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(this);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            ServiceRequest serviceRequest = new ServiceRequest();
            webServiceHandler.getCompanyList(this, context,serviceRequest, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                   CompanyData companyData = (CompanyData) obj;
                    ArrayList<Ajax> ajaxList  = new ArrayList<>();
                    ajaxList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> "  + ajaxList.size());
                }

                @Override
                public void onParseError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onNetworkError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                  //  Utility.callServerNotResponding(context);
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                  //  Utility.callMobileVerification(activity, context);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***Method used to get Branch List from the OPS Service **/

    private void fetchBranchList() {
        if (!HRMSNetworkCheck.checkInternetConnection(getApplicationContext())) {
            Utility.showToastMessage(this, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(this);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            ServiceRequest serviceRequest = new ServiceRequest() ;
            serviceRequest.setCompId(String.valueOf(1000));

            webServiceHandler.getBranchList(this, context,serviceRequest, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    CompanyData companyData = (CompanyData) obj;
                    ArrayList<Ajax>  ajaxList = new ArrayList<>();
                    ajaxList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> "  + ajaxList.size());
                }

                @Override
                public void onParseError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onNetworkError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    //  Utility.callServerNotResponding(context);
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    //  Utility.callMobileVerification(activity, context);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
