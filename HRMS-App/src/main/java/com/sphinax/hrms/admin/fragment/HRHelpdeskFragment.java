package com.sphinax.hrms.admin.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.employee.fragment.UserMainMenuFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.AdminAjax;
import com.sphinax.hrms.model.AdminCompanyData;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.CompanySpinnerAdapter;
import com.sphinax.hrms.view.DataSpinnerAdapter;
import com.sphinax.hrms.view.QuerySpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class HRHelpdeskFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    private static Context context;
    private View mView;
    private ProgressDialog pdia;
    private ArrayList<Ajax> companyList;
    private ArrayList<Ajax> branchList;
    private ArrayList<Ajax> departmentList;
    private ArrayList<Ajax> empList;
    private ArrayList<AdminAjax> queryCount;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private Spinner spCompany,spBranch,spDepartment,spEmp;
   // private Spinner sp_query_type;
    private TextView btSubmit;
    private EditText btDate;
    private EditText ed_mess;
    private CompanySpinnerAdapter companyDataAdapter;
    private CompanySpinnerAdapter branchDataAdapter;
    private CompanySpinnerAdapter departmentDataAdapter;
    private DataSpinnerAdapter empSpinnerAdapter;
    private int companyPosition = 0;
    private int branchPosition = 0;
    private int departmentPosition = 0;
    private String empPosition ;
    private int queryTypePosition;
    private ArrayList<Ajax> queryTypeList;
    private QuerySpinnerAdapter querySpinnerAdapter;
    private FragmentManager fragmentManager;
    private TextView tv_complete,tv_process,tv_hold,tv_info,tv_new;


    public HRHelpdeskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_hrhelpdesk, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        setListeners();
        fetchCompanyList();




    }

    private void loadComponent() {
        spCompany = mView.findViewById(R.id.sp_company);
        spBranch = mView.findViewById(R.id.sp_branch);
        spDepartment = mView.findViewById(R.id.sp_department);
        //spStatus = mView.findViewById(R.id.sp_status);
        spEmp = mView.findViewById(R.id.sp_empname);
//        btDate = mView.findViewById(R.id.bt_date_picker);
//        ed_mess = mView.findViewById(R.id.ed_message_box);
        btSubmit = mView.findViewById(R.id.bt_submit);
       // sp_query_type = mView.findViewById(R.id.sp_hr_query_type);
        tv_complete = mView.findViewById(R.id.tv_complete);
        tv_process = mView.findViewById(R.id.tv_process);
        tv_hold = mView.findViewById(R.id.tv_hold);
        tv_new = mView.findViewById(R.id.tv_new);
        tv_info = mView.findViewById(R.id.tv_info);

    }
    private void setListeners() {
        spCompany.setOnItemSelectedListener(this);
        spBranch.setOnItemSelectedListener(this);
        spDepartment.setOnItemSelectedListener(this);
        // spStatus.setOnItemSelectedListener(this);
        spEmp.setOnItemSelectedListener(this);
      //  sp_query_type.setOnItemSelectedListener(this);

        btSubmit.setOnClickListener(this);
        tv_process.setOnClickListener(this);
        tv_hold.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_new.setOnClickListener(this);

//        btDate.setOnClickListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_company:
                actionCompanySelector(position);
                break;
            case R.id.sp_branch:
                actionBranchSelector(position);
                break;
            case R.id.sp_department:
                actionDepartmentSelector(position);
                break;
//            case R.id.sp_hr_query_type:
//                actionTypeSelector(position);
//                break;

            case R.id.sp_empname:
                actionEmpSelector(position);
                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Its works on Company Spinner Click Action
     **/
    private void actionCompanySelector(int position) {
        //spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (companyList != null) {
            ajax = companyList.get(position);
        }
        companyPosition = ajax.getCompId();
        fetchBranchList(ajax.getCompId());
    }

    private void actionBranchSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (branchList != null) {
            ajax = branchList.get(position);
        }
        branchPosition = ajax.getBranchId();
        fetchDepartmentList(ajax.getBranchId());
    }

    private void actionDepartmentSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (departmentList != null) {
            ajax = departmentList.get(position);
        }
        departmentPosition = ajax.getDeptId();
        fetchEmpList();
    }


    private void actionEmpSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (empList != null) {
            ajax = empList.get(position);
        }
        empPosition = ajax.getEmpId();
        Log.d("leaveStatus"," " +empPosition);
       // fetchQueryTypeList();

        //fetchDepartmentList(ajax.getCompId());
        fetchrCount();
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
        fetchrCount();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btSubmit.getId()) {


            Log.d("cc","" + companyPosition + " " + branchPosition + " " + departmentPosition + " " + queryTypePosition + " " + empPosition);

//            if(btDate.getText()!=null && !btDate.getText().toString().equalsIgnoreCase("") && ed_mess.getText()!=null && !ed_mess.getText().toString().equalsIgnoreCase("")){
//                saveAnnouncement(ed_mess.getText().toString(),btDate.getText().toString());
//            }

        }
        else if(v.getId() == tv_new.getId()){
            Bundle b = new Bundle();
            b.putSerializable("Usertype","new");
            Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new HrHelpDeskList(), true, b, Constants.FRAMENT_USER_MENU);
        }
        else if(v.getId() == tv_info.getId()){
            Bundle b = new Bundle();
            b.putSerializable("Usertype","info");
            Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new HrHelpDeskList(), true, b, Constants.FRAMENT_USER_MENU);
        }
        else if(v.getId() == tv_complete.getId()){
            Bundle b = new Bundle();
            b.putSerializable("Usertype","complete");
            Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new HrHelpDeskList(), true, b, Constants.FRAMENT_USER_MENU);
        }
        else if(v.getId() == tv_hold.getId()){
            Bundle b = new Bundle();
            b.putSerializable("Usertype","hold");
            Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new HrHelpDeskList(), true, b, Constants.FRAMENT_USER_MENU);
        }
        else if(v.getId() == tv_process.getId()){
            Bundle b = new Bundle();
            b.putSerializable("Usertype","process");
            Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new HrHelpDeskList(), true, b, Constants.FRAMENT_USER_MENU);
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
            requestMap.put("companyId",String.valueOf(companyPosition) );

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
                        Ajax tempObj = new Ajax();
                        tempObj.setReqTypeId();
                        tempObj.setReqTypeDesc();
                        queryTypeList.add(0,tempObj);

                        querySpinnerAdapter = new QuerySpinnerAdapter(context,
                                queryTypeList);
                        querySpinnerAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                      //  sp_query_type.setAdapter(querySpinnerAdapter);

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


    private void fetchCompanyList() {
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
            HashMap<String, String> requestMap = new HashMap<>();

            webServiceHandler.getCompanyList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    companyList = new ArrayList<>();
                    companyList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + companyList.size());

                    companyDataAdapter = new CompanySpinnerAdapter(context,
                            companyList,1);
                    companyDataAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCompany.setAdapter(companyDataAdapter);

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


    private void fetchBranchList(int companyId) {
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
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId",String.valueOf(companyId) );

            webServiceHandler.getBranchList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    branchList = new ArrayList<>();
                    branchList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + branchList.size());

                    Ajax tempObj = new Ajax();
                    tempObj.setBranchId();
                    tempObj.setBranchName();
                    branchList.add(0,tempObj);



                    branchDataAdapter = new CompanySpinnerAdapter(context,
                            branchList,2);
                    branchDataAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spBranch.setAdapter(branchDataAdapter);

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

    private void fetchDepartmentList(int branchId) {
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
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("companyId",String.valueOf(companyPosition) );

            webServiceHandler.getDepartmentList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    departmentList = new ArrayList<>();
                    departmentList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + departmentList.size());
                    Ajax tempObj = new Ajax();
                    tempObj.setDeptId();
                    tempObj.setDeptName();
                    departmentList.add(0,tempObj);

                    departmentDataAdapter = new CompanySpinnerAdapter(context,
                            departmentList,3);
                    departmentDataAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDepartment.setAdapter(departmentDataAdapter);

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

    private void fetchEmpList() {
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
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("companyId",String.valueOf(companyPosition) );
            Log.d("xdd","" + branchPosition + " " + departmentPosition);
            requestMap.put("branch",String.valueOf(branchPosition) );
            requestMap.put("DeptId",String.valueOf(departmentPosition) );

            webServiceHandler.getEmpList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    empList = new ArrayList<>();
                    empList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + empList.size());
                    Ajax tempObj = new Ajax();
                    tempObj.setEmpId();
                    tempObj.setEmpDesc();
                    empList.add(0,tempObj);

                    empSpinnerAdapter = new DataSpinnerAdapter(context,
                            empList);
                    empSpinnerAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spEmp.setAdapter(empSpinnerAdapter);

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

    private void fetchrCount() {
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
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId",String.valueOf(companyPosition) );
            if (branchPosition == 0) {
                requestMap.put("branchId", String.valueOf("all"));
            }else{
                requestMap.put("branchId", String.valueOf(branchPosition));
            }

            if (departmentPosition == 0) {
                requestMap.put("deptId", String.valueOf("all"));
            }else{
                requestMap.put("deptId", String.valueOf(departmentPosition));
            }

            if (empPosition.equalsIgnoreCase("")) {
                requestMap.put("empId", String.valueOf("all"));
            }else{
                requestMap.put("empId", String.valueOf(empPosition));
            }

            if (queryTypePosition == 0) {
                requestMap.put("hrhelpStatus", String.valueOf("all"));
            }else{
                requestMap.put("hrhelpStatus", String.valueOf(queryTypePosition));
            }


            webServiceHandler.getHrHelpdeskCountList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    Log.d("ajaxList", "size --> " );
                    AdminCompanyData companyData = (AdminCompanyData) obj;
                    queryCount = new ArrayList<>();
                    queryCount = (ArrayList<AdminAjax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + queryCount.size());

                    setTextforHr();



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

private void setTextforHr(){

        if(queryCount != null){

            tv_new.setText(String.valueOf(queryCount.get(0).getNew()) + " " + "New");
            tv_info.setText(String.valueOf(queryCount.get(1).getNeedInfo())+ " " + "Need Info");
            tv_complete.setText(String.valueOf(queryCount.get(3).getCompleted())+ " " + "Completed");
            tv_hold.setText(String.valueOf(queryCount.get(4).getOnHold())+ " " + "On Hold");
            tv_process.setText(String.valueOf(queryCount.get(5).getInProgress())+ " " + "Process");

        }
}
}
