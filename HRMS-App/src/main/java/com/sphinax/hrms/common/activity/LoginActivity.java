package com.sphinax.hrms.common.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.CustomSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog pdia;
    private Context context;
    private ArrayList<Ajax> ajaxList;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private EditText ed_UserName,ed_Password;
    private Button bt_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this.getApplicationContext();

        loadComponent();
        setListeners();
    }

    private void loadComponent() {
        ed_UserName = findViewById(R.id.ed_username);
        ed_Password = findViewById(R.id.ed_password);
        bt_Login = findViewById(R.id.bt_login);

    }

    private void setListeners() {
        bt_Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                getEditTextValue();
                break;
        }


    }

    private void getEditTextValue(){
        String userNameValue     = ed_UserName.getText().toString().trim();
        String userPasswordValue = ed_Password.getText().toString().trim();

        if(!userNameValue.equalsIgnoreCase("") && !userPasswordValue.equalsIgnoreCase("")){
            loginValidation(userNameValue,userPasswordValue);
        }else{
            Utility.showToastMessage(this, getResources().getString(R.string.invalidUser));
        }

    }

    /***Method used to Login validation from the OPS Service **/

    private void loginValidation(String userNameValue , String userPasswordValue) {
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
            requestMap.put("userId",userNameValue  );
            requestMap.put("userpwd",userPasswordValue );
            requestMap.put("compId",Utility.getPreference(this).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("url", "http://e-lite.in:8080/ezhrRest/marketing/validateLogin");

            webServiceHandler.validateUser(this, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    Utility.showToastMessage(context, getResources().getString(R.string.invalidUser));

                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    CompanyData companyData = (CompanyData) obj;
                    Log.d("ajaxList", "size --> " + companyData.getUserId());

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
