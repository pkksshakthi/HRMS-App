package com.sphinax.hrms.common.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.activity.LoginActivity;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.LoginData;
import com.sphinax.hrms.sample.dummy.CustomToast;
import com.sphinax.hrms.sample.dummy.Utils;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword_Fragment extends Fragment implements
        OnClickListener {
	private static final String TAG = "ApplyLeaveFragment-";
	private static Context context;
	private final WebServiceHandler webServiceHandler = new WebServiceHandler();
	private View mView;
	private static EditText ed_userId,ed_password,ed_conformPassword,ed_otp;
	private LinearLayout ll_password,ll_otp;
	private static TextView submit, back;
	private static FragmentManager fragmentManager;
	private int validating = 0 ;
	private String userId ;
	private String password ;
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
		submit =  mView.findViewById(R.id.forgot_button);
		back =  mView.findViewById(R.id.backToLoginBtn);

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

	private void layoutPassword(){
		ll_otp.setVisibility(View.GONE);
		ll_password.setVisibility(View.VISIBLE);
		validating = 1 ;
	}
	private void layoutOTP(){
		ll_password.setVisibility(View.GONE);
		ll_otp.setVisibility(View.VISIBLE);
		validating = 2 ;
	}
	private void submitButtonTask() {

		if(validating == 1){


			 if(ed_userId.getText().toString() != null && !ed_userId.getText().toString().equalsIgnoreCase("")){

				 userId = ed_userId.getText().toString();

				 if(ed_password.getText().toString() != null && !ed_password.getText().toString().equalsIgnoreCase("")
					 && ed_conformPassword.getText().toString() != null && !ed_conformPassword.getText().toString().equalsIgnoreCase("")){


			 	if(ed_password.getText().toString().equalsIgnoreCase(ed_conformPassword.getText().toString())){
			 		password = ed_password.getText().toString();
					genrate_OTP();

				}else {
					Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.entercorrectPassWord));
				}

			 }else {
				 Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.enterPassWord));

			 }

			 }else {
				 Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.enterUserId));

			 }


		}else if(validating == 2){


			if(ed_userId.getText().toString() != null && !ed_userId.getText().toString().equalsIgnoreCase("")){

				if( ed_userId.getText().toString().equalsIgnoreCase(userId)){

					if(ed_otp.getText().toString() != null && !ed_otp.getText().toString().equalsIgnoreCase("")){

						check_otp();


					}else {
						Utility.showCustomToast(getActivity(), mView, "Please Enter OTP");

					}

					}else {
					Utility.showCustomToast(getActivity(), mView, "UserId changed");
					layoutPassword();
					ed_otp.setText("");

				}


				}else {
				Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.enterUserId));

			}

		}




//		String getEmailId = emailId.getText().toString();
//
//		// Pattern for email id validation
//		Pattern p = Pattern.compile(Utils.regEx);
//
//		// Match the pattern
//		Matcher m = p.matcher(getEmailId);
//
//		// First check if email id is not null else show error toast
//		if (getEmailId.equals("") || getEmailId.length() == 0)
//
//			new CustomToast().Show_Toast(getActivity(), view,
//					"Please enter your Email Id.");
//
//		// Check if email id is valid or not
//		else if (!m.find())
//			new CustomToast().Show_Toast(getActivity(), view,
//					"Your Email Id is Invalid.");
//
//		// Else submit email id and fetch passwod or do your stuff
//		else
//			Toast.makeText(getActivity(), "Get Forgot Password.",
//					Toast.LENGTH_SHORT).show();
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

			HashMap<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
			requestMap.put("userId", userId);

			webServiceHandler.genrateOTP(getActivity(), getActivity(), requestMap, new ServiceCallback() {

				@Override
				public void onSuccess(boolean flag) {
					if (pdia != null) {
						pdia.dismiss();
					}
//					if (flag == true) {
//						//  startMenuActivity("user");
//					} else {
//						Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidUser));
//					}
					ed_password.setText("");
					ed_conformPassword.setText("");
					layoutOTP();

				}

				@Override
				public void onReturnObject(Object obj) {
					if (pdia != null) {
						pdia.dismiss();
					}
//					LoginData loginData = (LoginData) obj;
//					Log.d(TAG, "size --> " + loginData.getUserId());
//
//					if (loginData != null && loginData.getResCode() == 1) {
//						Global.setLoginInfoData(loginData);
//						startMenuActivity(loginData.getAdminOremp());
//					}

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

			HashMap<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
			requestMap.put("userId", userId);
			requestMap.put("otp", ed_otp.getText().toString() );

			webServiceHandler.checkOTP(getActivity(), getActivity(), requestMap, new ServiceCallback() {

				@Override
				public void onSuccess(boolean flag) {
					if (pdia != null) {
						pdia.dismiss();
					}
//					if (flag == true) {
//						//  startMenuActivity("user");
//					} else {
//						Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidUser));
//					}
					layoutPassword();
					change_Password();
				}

				@Override
				public void onReturnObject(Object obj) {
					if (pdia != null) {
						pdia.dismiss();
					}
//					LoginData loginData = (LoginData) obj;
//					Log.d(TAG, "size --> " + loginData.getUserId());
//
//					if (loginData != null && loginData.getResCode() == 1) {
//						Global.setLoginInfoData(loginData);
//						startMenuActivity(loginData.getAdminOremp());
//					}

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

			HashMap<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
			requestMap.put("userId", userId);
			requestMap.put("newPass", password );

			webServiceHandler.changePassword(getActivity(), getActivity(), requestMap, new ServiceCallback() {

				@Override
				public void onSuccess(boolean flag) {
					if (pdia != null) {
						pdia.dismiss();
					}
//					if (flag == true) {
//						//  startMenuActivity("user");
//					} else {
//						Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidUser));
//					}
					layoutPassword();
					Utility.addFragment(getActivity(), R.id.frameContainer, fragmentManager, new Login_Fragment(), false, null, Constants.FRAMENT_LOGIN);

				}

				@Override
				public void onReturnObject(Object obj) {
					if (pdia != null) {
						pdia.dismiss();
					}
//					LoginData loginData = (LoginData) obj;
//					Log.d(TAG, "size --> " + loginData.getUserId());
//
//					if (loginData != null && loginData.getResCode() == 1) {
//						Global.setLoginInfoData(loginData);
//						startMenuActivity(loginData.getAdminOremp());
//					}

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


}