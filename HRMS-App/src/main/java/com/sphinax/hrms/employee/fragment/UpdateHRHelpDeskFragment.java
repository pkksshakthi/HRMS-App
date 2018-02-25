package com.sphinax.hrms.employee.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.LoginData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateHRHelpDeskFragment extends Fragment {

    private static final String TAG = "Login_Fragment-";
    private static View mView;
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ProgressDialog pdia;
    private FragmentManager fragmentManager;
    private Ajax ajax;



    public UpdateHRHelpDeskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_hrhelp_desk, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        ajax = (Ajax) getArguments().getSerializable("UserValidateObject");

    }


    private void loadComponent() {
//        calendarView = mView.findViewById(R.id.calendarView);
//        txt_present = mView.findViewById(R.id.txt_present);
//        txt_absent = mView.findViewById(R.id.txt_absert);
//        txt_applyLeave = mView.findViewById(R.id.txt_applyleave);
    }


    private void updateHrDesk(String userNameValue, String password) {
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

           // String url = Constants.LOGIN_REQUEST_URL;
           // url = url.replace("{COMPANYID}", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("companyId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("requestStatus", "4");
            requestMap.put("description", "4");
            requestMap.put("reqId", "45");


           // requestMap.put("url", url);

            webServiceHandler.upodateHrHelp(getActivity(), getActivity(), requestMap, new ServiceCallback() {

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
//                    LoginData loginData = (LoginData) obj;
//                    Log.d(TAG, "size --> " + loginData.getUserId());
//
//                    if (loginData != null && loginData.getResCode() == 1) {
//                        Global.setLoginInfoData(loginData);
//                        startMenuActivity(loginData.getAdminOremp());
//                    }

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
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);

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
            Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EnterHRHelpdeskFragment(), false, null, Constants.FRAMENT_LEAVE_MANAGEMENT);

                    return true;

                }

                return false;
            }
        });

    }
}
