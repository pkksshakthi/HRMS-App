package com.sphinax.hrms.admin.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.QuerySpinnerAdapter;
import com.sphinax.hrms.view.StatusSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by ganesaka on 4/26/2018.
 */

public class AdminHrHelpDeskFull extends Fragment implements View.OnClickListener {

    private static final String TAG = "EmployeeLeaveFullContentFragment-";
    private static Context context;
    private View mView;
    private EditText  ed_admin_mess;
    private Spinner sp_status;
    private TextView tv_query, tv_refid, tv_compdetail, tv_query_type, tv_emp_message;
    private Button bt_submit,bt_cancel;
    private Ajax ajax;
    private FragmentManager fragmentManager;
    private int statusTypePosition;
    private ArrayList<Ajax> statusTypeList;
    private StatusSpinnerAdapter statusSpinnerAdapter;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ProgressDialog pdia;
    private  String typeUser;


    public AdminHrHelpDeskFull() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_hrhelpdesk_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        ajax = (Ajax) getArguments().getSerializable("UserValidateObject");

       // loadComponent();
        typeUser = Global.getHrTye();
        if(typeUser.equalsIgnoreCase("new")){
            tv_query.setText("New");
        }else if(typeUser.equalsIgnoreCase("info")){
            tv_query.setText("Need Info");
        }else if(typeUser.equalsIgnoreCase("complete")){
            tv_query.setText("Complete");
        }else if(typeUser.equalsIgnoreCase("hold")){
            tv_query.setText("On Hold");
        }else if(typeUser.equalsIgnoreCase("process")){
            tv_query.setText("In Process");
        }


        fetchQueryTypeList();
        loadData();
    }

    private void loadComponent() {
        ed_admin_mess = mView.findViewById(R.id.ed_admin_mess);
        sp_status = mView.findViewById(R.id.sp_status);
        tv_query = mView.findViewById(R.id.tv_query);
        tv_refid = mView.findViewById(R.id.tv_refid);
        tv_compdetail = mView.findViewById(R.id.tv_compdetail);
        tv_query_type = mView.findViewById(R.id.tv_query_type);
        tv_emp_message = mView.findViewById(R.id.tv_emp_message);
        bt_submit = mView.findViewById(R.id.bt_submit);
        bt_cancel = mView.findViewById(R.id.bt_cancel);
    }

    private void loadData() {
        if (ajax != null) {

            tv_refid.setText(String.valueOf(ajax.getReqId()));
            //tv_compdetail.setText(ajax.);
            tv_query_type.setText(ajax.getReqTypeDesc());
            tv_emp_message.setText(ajax.getReqDesc());


        }
    }

    private void fetchQueryTypeList() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showCustomToast(context, mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );

            webServiceHandler.getStatusTypeList(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @SuppressLint("LongLogTag")
                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    if (obj != null) {
                        CompanyData companyData = (CompanyData) obj;
                        statusTypeList = new ArrayList<>();
                        statusTypeList = (ArrayList<Ajax>) companyData.getAjax();
                        Log.d(TAG, "size --> " + statusTypeList.size());
                        Ajax tempObj = new Ajax();
                        tempObj.setReqStatusId();
                        tempObj.setReqStatusDesc();
                        statusTypeList.add(0,tempObj);

                        statusSpinnerAdapter = new StatusSpinnerAdapter(context,
                                statusTypeList);
                        statusSpinnerAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_status.setAdapter(statusSpinnerAdapter);

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
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());

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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_cancel:

                break;
            case R.id. bt_submit:

                break;
        }
    }
}

