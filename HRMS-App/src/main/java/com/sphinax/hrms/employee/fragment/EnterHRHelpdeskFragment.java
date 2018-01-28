package com.sphinax.hrms.employee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.QueryListAdapter;
import com.sphinax.hrms.view.QuerySpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class EnterHRHelpdeskFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "EnterHRHelpdeskFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
    private Spinner sp_query_type;
    private EditText ed_query;
    private ListView lv_query;
    private Button bt_submit;
    private int queryTypePosition;
    private ProgressDialog pdia;
    private ArrayList<Ajax> queryTypeList;
    private ArrayList<Ajax> queryList;
    private QuerySpinnerAdapter querySpinnerAdapter;
    private QueryListAdapter queryListAdapter;


    public EnterHRHelpdeskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_hrhelpdesk, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        loadComponent();
        setListeners();
        fetchQueryTypeList();

    }

    private void loadComponent() {
        sp_query_type = mView.findViewById(R.id.sp_hr_query_type);
        ed_query = mView.findViewById(R.id.ed_query);
        bt_submit = mView.findViewById(R.id.bt_submit);
        lv_query = mView.findViewById(R.id.lv_hr_help_desk_data);
    }

    private void setListeners() {
        sp_query_type.setOnItemSelectedListener(this);
        bt_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:

                if (ed_query.getText() != null && !ed_query.getText().toString().equalsIgnoreCase("")) {
                    saveQuery();
                } else {
                    Utility.showCustomToast(context, mView, "Please Enter Your Query");
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_hr_query_type:
                actionTypeSelector(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Its works on Query Spinner Click Action
     **/
    private void actionTypeSelector(int position) {
        Ajax ajax = new Ajax();
        if (queryTypeList != null) {
            ajax = queryTypeList.get(position);
            queryTypePosition = ajax.getReqTypeId();
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
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("companyId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));

            webServiceHandler.getQueryTypeList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    if (obj != null) {
                        CompanyData companyData = (CompanyData) obj;
                        queryTypeList = new ArrayList<>();
                        queryTypeList = (ArrayList<Ajax>) companyData.getAjax();
                        Log.d(TAG, "size --> " + queryTypeList.size());

                        querySpinnerAdapter = new QuerySpinnerAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item, queryTypeList);
                        querySpinnerAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_query_type.setAdapter(querySpinnerAdapter);

                        fetchQueryList();
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

    private void saveQuery() {
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
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("companyId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("requestTo", Global.getLoginInfoData().getReportsTo());
            requestMap.put("requestType", String.valueOf(queryTypePosition));
            requestMap.put("description", ed_query.getText().toString());

            webServiceHandler.saveUserQuery(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    if (flag) {
                        Utility.showCustomToast(context, mView, "Query successfully saved");
                        fetchQueryList();
                        querySpinnerAdapter = new QuerySpinnerAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item, queryTypeList);
                        querySpinnerAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_query_type.setAdapter(querySpinnerAdapter);

                        ed_query.setText("");
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


    private void fetchQueryList() {
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
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            //  requestMap.put("empId",Utility.getPreference(getActivity()).getString(Constants.PREFS_USER_ID, "") );
            requestMap.put("empId", "10002");
            requestMap.put("reqSeqId", "20");

            webServiceHandler.getEMPQueryList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    queryList = new ArrayList<>();
                    queryList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + queryList.size());

                    queryListAdapter = new QueryListAdapter(getActivity(), queryList);
                    lv_query.setAdapter(queryListAdapter);

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
