package com.sphinax.hrms.common.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.model.ServiceRequest;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.CustomSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SelectCompanyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener  {

    private ProgressDialog pdia;
    private Context context;
    private ArrayList<Ajax> ajaxList;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private Spinner spCompany;
    private Button btNext;
    private CustomSpinnerAdapter companyDataAdapter;
    private int spinnerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selectcompany);
        context = this.getApplicationContext();
        loadComponent();
        setListeners();
        fetchCompanyList();


    }

    private void loadComponent() {
        spCompany = findViewById(R.id.sp_company);
        btNext = findViewById(R.id.bt_next);

    }

    private void setListeners() {
        spCompany.setOnItemSelectedListener(this);
        btNext.setOnClickListener(this);
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
            HashMap<String, String> requestMap = new HashMap<String, String>();

            webServiceHandler.getCompanyList(this, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    CompanyData companyData = (CompanyData) obj;
                    ajaxList = new ArrayList<>();
                    ajaxList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + ajaxList.size());

                    companyDataAdapter = new CustomSpinnerAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item, ajaxList);
                    companyDataAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCompany.setAdapter(companyDataAdapter);

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_company:
                actionCompanySelector(position);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Its works on Company Spinner Click Action
     **/
    private void actionCompanySelector(int position) {
       spinnerPosition = position;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btNext.getId()) {
            Ajax ajax = new Ajax();
            if (ajaxList != null) {
                 ajax = ajaxList.get(spinnerPosition);
            }
    SharedPreferences.Editor editor = Utility.getPreference(this).edit();
            editor.putString(Constants.PREFS_COMPANY_ID, String.valueOf(ajax.getCompId()));
            editor.putString(Constants.PREFS_COMPANY_NAME,ajax.getCompName());
            editor.commit();
        }
    }



}
