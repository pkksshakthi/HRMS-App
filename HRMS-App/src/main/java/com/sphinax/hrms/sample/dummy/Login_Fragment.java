package com.sphinax.hrms.sample.dummy;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
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
import com.sphinax.hrms.model.LoginData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private ProgressDialog pdia;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.ed_username);
        password = (EditText) view.findViewById(R.id.ed_password);
        loginButton = (Button) view.findViewById(R.id.bt_login);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                checkValidation();
                break;

            case R.id.forgot_password:

                // Replace forgot password fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPassword_Fragment(),
                                Utils.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new OTP_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        } else {
            loginValidation(emailid, password);
        }
//		// Check if email id is valid or not
//		else if (!m.find())
//			new CustomToast().Show_Toast(getActivity(), view,
//					"Your Email Id is Invalid.");
//		// Else do login and do your stuff
//		else
//			loginValidation(emailid,password);
////			Toast.makeText(getActivity(), "Do Login.", Toast.LENGTH_SHORT)
////					.show();

    }

    private void loginValidation(EditText userNameValue, EditText password) {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showToastMessage(getActivity(), getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
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
                        new CustomToast().Show_Toast(getActivity(), view,
                                getResources().getString(R.string.invalidUser));
                      //  Utility.showToastMessage(getContext(), getResources().getString(R.string.invalidUser));
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    LoginData companyData = (LoginData) obj;
                    Log.d("ajaxList", "size --> " + companyData.getUserId());

                    if (companyData != null && companyData.getResCode() == 1) {
                        startMenuActivity(companyData.getAdminOremp());
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
