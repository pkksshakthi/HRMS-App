package com.sphinax.hrms.employee.fragment;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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
import com.sphinax.hrms.view.QueryListAdapter;
import com.sphinax.hrms.view.QuerySpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class    EnterHRHelpdeskFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

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
    private FragmentManager fragmentManager;

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
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        setListeners();
        fetchQueryTypeList();

        lv_query.setOnItemClickListener((parent, view1, position, id) -> {

            Ajax ajaxApp = queryList.get(position);
            if(ajaxApp.getStatus().equalsIgnoreCase("Need Info")) {
                Bundle b = new Bundle();
                b.putSerializable("UserValidateObject", ajaxApp);
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new UpdateHRHelpDeskFragment(), true, b, Constants.FRAMENT_HRHELP_UPDATE_CONTENT);
            }
        });

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
        @SuppressWarnings("UnusedAssignment") Ajax ajax = new Ajax();
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
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("companyId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));

            webServiceHandler.getQueryTypeList(getActivity(), context, requestMap, new ServiceCallback() {

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
                        queryTypeList = new ArrayList<>();
                        queryTypeList = (ArrayList<Ajax>) companyData.getAjax();
                        Log.d(TAG, "size --> " + queryTypeList.size());

                        querySpinnerAdapter = new QuerySpinnerAdapter(context,
                                queryTypeList);
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
            HashMap<String, String> requestMap = new HashMap<>();
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
                                queryTypeList);
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
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empId",Global.getLoginInfoData().getUserId() );

            webServiceHandler.getEMPQueryList(getActivity(), context, requestMap, new ServiceCallback() {

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
    @SuppressLint("LongLogTag")
    @Override
    public void onResume() {
        super.onResume();
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());
            return;
        }


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EnterHRHelpdeskFragment(), false, null, Constants.FRAMENT_LEAVE_MANAGEMENT);

                try {
                    if (getActivity().getFragmentManager().findFragmentById(R.id.content_frame).getTag() == null) {
                       // Global.setTabPosition(0);
                        Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new UserMainMenuFragment(), false, null, Constants.FRAMENT_LEAVE_MANAGEMENT);

                    } else if (getActivity().getFragmentManager().findFragmentById(R.id.content_frame).getTag().equalsIgnoreCase(Constants.FRAMENT_HR_HELPDESK_ENTER)) {
                        Log.d(TAG, "onKey: " + getActivity().getFragmentManager().findFragmentById(R.id.content_frame).getTag());

                        //Global.setTabPosition(0);
                        Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new UserMainMenuFragment(), false, null, Constants.FRAMENT_HR_HELPDESK_ENTER);

                    } else if (getActivity().getFragmentManager().findFragmentById(R.id.content_frame).getTag().equalsIgnoreCase(Constants.FRAMENT_HRHELP_UPDATE_CONTENT)) {
                        Log.d(TAG, "onKey: " + getActivity().getFragmentManager().findFragmentById(R.id.content_frame).getTag());

                        Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new UpdateHRHelpDeskFragment(), false, null, Constants.FRAMENT_HRHELP_UPDATE_CONTENT);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //Global.setTabPosition(0);
                    Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new UserMainMenuFragment(), false, null, Constants.FRAMENT_LEAVE_MANAGEMENT);

                }


                return true;

            }

            return false;
        });

    }
}
