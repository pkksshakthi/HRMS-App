package com.sphinax.hrms.admin.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.CompanySpinnerAdapter;
import com.sphinax.hrms.view.DataSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeaveManagementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeaveManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaveManagementFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static Context context;
    private View mView;
    private ProgressDialog pdia;
    private ArrayList<Ajax> companyList;
    private ArrayList<Ajax> branchList;
    private ArrayList<Ajax> departmentList;
    private ArrayList<Ajax> empList;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private Spinner spCompany,spBranch,spDepartment,spStatus,spEmp;
    private Button btSubmit;
    private EditText btDate;
    private EditText ed_mess;
    private CompanySpinnerAdapter companyDataAdapter;
    private CompanySpinnerAdapter branchDataAdapter;
    private CompanySpinnerAdapter departmentDataAdapter;
    private DataSpinnerAdapter statusSpinnerAdapter;
    private DataSpinnerAdapter empSpinnerAdapter;
    private int companyPosition = 0;
    private int branchPosition = 0;
    private int departmentPosition = 0;
    private int statusPosition = 0;
    private String empPosition ;
    private final String[] leaveTypes = {"Select Status","Approved", "Rejected","Pending"};



    public LeaveManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaveManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaveManagementFragment newInstance(String param1, String param2) {
        LeaveManagementFragment fragment = new LeaveManagementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_leave_management, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        loadComponent();
        setListeners();
        fetchCompanyList();

        //noinspection unchecked
        @SuppressWarnings("unchecked") ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, leaveTypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spStatus.setAdapter(arrayAdapter);


    }

    private void loadComponent() {
        spCompany = mView.findViewById(R.id.sp_company);
        spBranch = mView.findViewById(R.id.sp_branch);
        spDepartment = mView.findViewById(R.id.sp_department);
        spStatus = mView.findViewById(R.id.sp_status);
        spEmp = mView.findViewById(R.id.sp_empname);
//        btDate = mView.findViewById(R.id.bt_date_picker);
//        ed_mess = mView.findViewById(R.id.ed_message_box);
        btSubmit = mView.findViewById(R.id.bt_submit);

    }
    private void setListeners() {
        spCompany.setOnItemSelectedListener(this);
        spBranch.setOnItemSelectedListener(this);
        spDepartment.setOnItemSelectedListener(this);
        spStatus.setOnItemSelectedListener(this);
        spEmp.setOnItemSelectedListener(this);

        btSubmit.setOnClickListener(this);
//        btDate.setOnClickListener(this);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
            case R.id.sp_status:
                actionStatusSelector(position);
                break;

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

    private void actionStatusSelector(int position) {
     Log.d("leaveStatus"," " +position);
        statusPosition = position;
    }

    private void actionEmpSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (empList != null) {
            ajax = empList.get(position);
        }
        empPosition = ajax.getEmpId();
        Log.d("leaveStatus"," " +empPosition);


        //fetchDepartmentList(ajax.getCompId());
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == btSubmit.getId()) {


            Log.d("cc","" + companyPosition + " " + branchPosition + " " + departmentPosition + " " + statusPosition + " " + empPosition);

//            if(btDate.getText()!=null && !btDate.getText().toString().equalsIgnoreCase("") && ed_mess.getText()!=null && !ed_mess.getText().toString().equalsIgnoreCase("")){
//                saveAnnouncement(ed_mess.getText().toString(),btDate.getText().toString());
//            }

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
}
