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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class UpdateHRHelpDeskFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "Login_Fragment-";
    private static View mView;
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ProgressDialog pdia;
    private FragmentManager fragmentManager;
    private Ajax ajax;
    TextView tv_refid,tv_qty_dec,tv_status;
    EditText ed_upadteNot;
    Button bt_cancl,bt_update;



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
        setListeners();
        ajax = (Ajax) getArguments().getSerializable("UserValidateObject");
        loadData();


    }


    private void loadComponent() {
        ed_upadteNot = mView.findViewById(R.id.ed_upadteNot);
        tv_refid = mView.findViewById(R.id.tv_refid);
        tv_qty_dec = mView.findViewById(R.id.tv_qty_dec);
         tv_status = mView.findViewById(R.id.tv_status);
        bt_update = mView.findViewById(R.id.bt_update);
        bt_cancl = mView.findViewById(R.id.bt_cancl);
    }

    private void setListeners() {
        bt_update.setOnClickListener(this);
        bt_cancl.setOnClickListener(this);
    }

    private void loadData() {
        if (ajax != null) {

            tv_status.setText(ajax.getStatus());
            tv_qty_dec.setText(ajax.getReqTypeDesc());
            tv_refid.setText(String.valueOf(ajax.getReqId()));


        }
    }

    private void  getdatatCallservice(){

        if(ed_upadteNot.getText().toString() != null && !ed_upadteNot.getText().toString().equalsIgnoreCase("") ){
            updateHrDesk(ed_upadteNot.getText().toString());
        }
    }
    private void updateHrDesk(String userNameValue) {
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
            requestMap.put("requestStatus", ajax.getStatus());
            requestMap.put("description", userNameValue);
            requestMap.put("reqId", String.valueOf(ajax.getReqId()));


           // requestMap.put("url", url);

            webServiceHandler.upodateHrHelp(getActivity(), getActivity(), requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    if (flag == true) {
                        //  startMenuActivity("user");

                        ed_upadteNot.setText("");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update:
                getdatatCallservice();
                break;

            case R.id.bt_cancl:

                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EnterHRHelpdeskFragment(), false, null, Constants.FRAMENT_LEAVE_MANAGEMENT);

                break;
        }
    }


}
