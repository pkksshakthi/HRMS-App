package com.sphinax.hrms.common.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.admin.activity.AdminMenuActivity;
import com.sphinax.hrms.employee.activity.UserMenuActivity;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.LoginData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.HashMap;

public class Login_Fragment extends Fragment implements OnClickListener {

    private static final String TAG = "Login_Fragment-";
    private static View mView;
    private static Context context;
    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ProgressDialog pdia;


    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews();
        setListeners();

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }


    // Initiate Views
    private void initViews() {
        emailid = mView.findViewById(R.id.ed_username);
        password = mView.findViewById(R.id.ed_password);
        loginButton = mView.findViewById(R.id.bt_login);
        forgotPassword = mView.findViewById(R.id.forgot_password);
        show_hide_password = mView.findViewById(R.id.show_hide_password);
        loginLayout = mView.findViewById(R.id.login_layout);
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                checkValidation();
                break;

            case R.id.forgot_password:
                Utility.addFragment(getActivity(), R.id.frameContainer, fragmentManager, new ForgotPassword_Fragment(), true, null, Constants.FRAMENT_FORGOT_PASSWORD);
                break;
        }

    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();
        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.enterUser));
        } else {
            loginValidation(getEmailId, getPassword);
        }
    }


    private void startMenuActivity(String user) {
        if (user.equalsIgnoreCase("E")) {
            Intent intent = new Intent(getActivity(), UserMenuActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else if (user.equalsIgnoreCase("A")) {
            Intent intent = new Intent(getActivity(), AdminMenuActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }


    private void loginValidation(String userNameValue, String password) {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.setCancelable(false);
            pdia.show();
        }
        try {

            String url = Constants.LOGIN_REQUEST_URL;
            url = url.replace("{COMPANYID}", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("userId", userNameValue.toString());
            requestMap.put("userpwd", password.toString());
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("url", url);

            webServiceHandler.validateUser(getActivity(), getActivity(), requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    if (flag == true) {
                        //  startMenuActivity("user");
                    } else {
                        Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidUser));
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    LoginData loginData = (LoginData) obj;
                    Log.d(TAG, "size --> " + loginData.getUserId());

                    if (loginData != null && loginData.getResCode() == 1) {
                        Global.setLoginInfoData(loginData);
                        startMenuActivity(loginData.getAdminOremp());
                    }

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
                    Utility.callErrorScreen(getActivity(), R.id.frameContainer, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);

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
    public void onResume() {
        super.onResume();
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.callErrorScreen(getActivity(), R.id.frameContainer, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }
    }
}
