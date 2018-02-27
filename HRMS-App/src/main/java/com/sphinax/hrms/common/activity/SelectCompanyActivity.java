package com.sphinax.hrms.common.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.CompanySpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SelectCompanyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "SelectCompanyActivity-";
    private static View view;
    private static FragmentManager fragmentManager;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private Context context;
    private ArrayList<Ajax> companyajaxList;
    private ProgressDialog pdia;
    private Spinner spCompany;
    private Button btNext;
    private CompanySpinnerAdapter companyDataAdapter;
    private int spinnerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_selectcompany);
        context = this.getApplicationContext();
        fragmentManager = getSupportFragmentManager();
        view = getWindow().getDecorView().getRootView();
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
            if (companyajaxList != null) {
                ajax = companyajaxList.get(spinnerPosition);
            }
            SharedPreferences.Editor editor = Utility.getPreference(this).edit();
            editor.putString(Constants.PREFS_COMPANY_ID, String.valueOf(ajax.getCompId()));
            editor.putString(Constants.PREFS_COMPANY_NAME, ajax.getCompName());
            editor.putString(Constants.PREFS_COMPANY_SHORT_NAME, ajax.getShortName());
            editor.putString(Constants.PREFS_COMPANY_IMAGE, ajax.getCompImg());
            editor.commit();
        }

        Intent intent = new Intent(SelectCompanyActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    /***Method used to get Company List from the OPS Service **/

    private void fetchCompanyList() {
        if (!HRMSNetworkCheck.checkInternetConnection(getApplicationContext())) {
            Utility.showCustomToast(this, view, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(this);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.setCancelable(false);
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
                    companyajaxList = new ArrayList<>();
                    companyajaxList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + companyajaxList.size());

                    companyDataAdapter = new CompanySpinnerAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item, companyajaxList, 1);
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
                    Utility.callErrorScreen(SelectCompanyActivity.this, R.id.frameContainer, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);

                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!HRMSNetworkCheck.checkInternetConnection(getApplicationContext())) {
            Utility.callErrorScreen(this, R.id.frameContainer, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }
    }
}
