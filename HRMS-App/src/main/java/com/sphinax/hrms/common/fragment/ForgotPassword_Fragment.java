package com.sphinax.hrms.common.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.HashMap;

public class ForgotPassword_Fragment extends Fragment implements OnClickListener {

    private static final String TAG = "ForgotPassword_Fragment-";
    private static Context context;
    private static EditText ed_userId, ed_password, ed_conformPassword, ed_otp;
    private static TextView submit, back;
    private static FragmentManager fragmentManager;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
    private LinearLayout ll_password, ll_otp;
    private int validating = 0;
    private String userId;
    private String password;
    private ProgressDialog pdia;

    public ForgotPassword_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        initViews();
        setListeners();
        layoutPassword();
    }

    // Initialize the views
    private void initViews() {
        ed_userId = mView.findViewById(R.id.registered_emailid);
        ed_password = mView.findViewById(R.id.txt_newpass);
        ed_conformPassword = mView.findViewById(R.id.txt_confirmpass);
        ed_otp = mView.findViewById(R.id.txt_otp);
        ll_otp = mView.findViewById(R.id.ll_otp);
        ll_password = mView.findViewById(R.id.ll_password);
        submit = mView.findViewById(R.id.forgot_button);
        back = mView.findViewById(R.id.backToLoginBtn);

    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:
                // Replace Login Fragment on Back Presses
                Utility.addFragment(getActivity(), R.id.frameContainer, fragmentManager, new Login_Fragment(), false, null, Constants.FRAMENT_LOGIN);
                break;

            case R.id.forgot_button:
                // Call Submit button task
                submitButtonTask();
                break;
        }

    }

    private void layoutPassword() {
        ll_otp.setVisibility(View.GONE);
        ll_password.setVisibility(View.VISIBLE);
        validating = 1;
    }

    private void layoutOTP() {
        ll_password.setVisibility(View.GONE);
        ll_otp.setVisibility(View.VISIBLE);
        validating = 2;
    }

    private void submitButtonTask() {

        if (validating == 1) {

            if (ed_userId.getText().toString() != null && !ed_userId.getText().toString().equalsIgnoreCase("")) {

                userId = ed_userId.getText().toString();

                if (ed_password.getText().toString() != null && !ed_password.getText().toString().equalsIgnoreCase("")
                        && ed_conformPassword.getText().toString() != null && !ed_conformPassword.getText().toString().equalsIgnoreCase("")) {

                    if (ed_password.getText().toString().equalsIgnoreCase(ed_conformPassword.getText().toString())) {
                        password = ed_password.getText().toString();
                        genrate_OTP();
                    } else {
                        Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.entercorrectPassWord));
                    }
                } else {
                    Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.enterPassWord));
                }
            } else {
                Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.enterUserId));
            }


        } else if (validating == 2) {

            if (ed_userId.getText().toString() != null && !ed_userId.getText().toString().equalsIgnoreCase("")) {

                if (ed_userId.getText().toString().equalsIgnoreCase(userId)) {

                    if (ed_otp.getText().toString() != null && !ed_otp.getText().toString().equalsIgnoreCase("")) {
                        check_otp();
                    } else {
                        Utility.showCustomToast(getActivity(), mView, "Please Enter OTP");
                    }
                } else {
                    Utility.showCustomToast(getActivity(), mView, "UserId changed");
                    layoutPassword();
                    ed_otp.setText("");
                }
            } else {
                Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.enterUserId));
            }
        }
    }

    private void genrate_OTP() {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {

            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("userId", userId);

            webServiceHandler.genrateOTP(getActivity(), getActivity(), requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    ed_password.setText("");
                    ed_conformPassword.setText("");
                    layoutOTP();
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
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
                    Utility.callErrorScreen(getActivity(), R.id.frameContainer, fragmentManager, new SomeProblemFragment());

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

    private void check_otp() {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {

            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("userId", userId);
            requestMap.put("otp", ed_otp.getText().toString());

            webServiceHandler.checkOTP(getActivity(), getActivity(), requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    if(flag){
                        Utility.showCustomToast(getActivity(), mView, "Password Have Been Changed");
                        layoutPassword();
                        change_Password();
                    }else {
                        Utility.showCustomToast(getActivity(), mView, "Given OTP is incorrect");
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
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
                    Utility.callErrorScreen(getActivity(), R.id.frameContainer, fragmentManager, new SomeProblemFragment());

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

    private void change_Password() {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {

            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("userId", userId);
            requestMap.put("newPass", password);

            webServiceHandler.changePassword(getActivity(), getActivity(), requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    layoutPassword();
                    Utility.addFragment(getActivity(), R.id.frameContainer, fragmentManager, new Login_Fragment(), false, null, Constants.FRAMENT_LOGIN);
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
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
                    Utility.callErrorScreen(getActivity(), R.id.frameContainer, fragmentManager, new SomeProblemFragment());

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
            Utility.callErrorScreen(getActivity(), R.id.frameContainer, fragmentManager, new SomeProblemFragment());
        }
    }
}