package com.sphinax.hrms.admin.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.HashMap;

/**
 * Created by ganesaka on 4/26/2018.
 */

public class AdminLeaveFullContent extends Fragment {

    private static final String TAG = "EmployeeLeaveFullContentFragment-";
    private static Context context;
    private View mView;
    private EditText ed_leaveTye, ed_EmpMess, ed_AdminMess;
    private TextView tv_fromdate, tv_todate,tv_nooddays,tv_comp,tv_branch,tv_department,tv_emp,ed_emp_mess,tv_username,tv_designation;
    private EditText ed_admin_mess;
    private Button bt_update,bt_cancel;
    private Ajax ajax;
    private FragmentManager fragmentManager;
    private ProgressDialog pdia;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();

    public AdminLeaveFullContent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_leave_management_approve, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        ajax = (Ajax) getArguments().getSerializable("UserValidateObject");

        loadComponent();

         loadData();
    }

    private void loadComponent() {
        tv_fromdate = mView.findViewById(R.id.tv_fromdate);
        tv_username = mView.findViewById(R.id.tv_username);
        tv_designation = mView.findViewById(R.id.tv_designation);
        tv_todate = mView.findViewById(R.id.tv_todate);
        tv_nooddays = mView.findViewById(R.id.tv_nooddays);
        tv_comp = mView.findViewById(R.id.tv_comp);
        tv_branch = mView.findViewById(R.id.tv_branch);
        tv_department = mView.findViewById(R.id.tv_department);
        tv_emp = mView.findViewById(R.id.tv_emp);
        ed_emp_mess = mView.findViewById(R.id.ed_emp_mess);
        ed_AdminMess = mView.findViewById(R.id.ed_admin_mess);
        bt_update = mView.findViewById(R.id.bt_update);
        bt_cancel = mView.findViewById(R.id.bt_cancel);
        if (!ajax.getLeaveStatus().equalsIgnoreCase("PENDING")) {
            getActivity().findViewById(R.id.ln_admin_msg).setVisibility(View.GONE);
            getActivity().findViewById(R.id.bt_update).setVisibility(View.GONE);
            getActivity().findViewById(R.id.bt_cancel).setVisibility(View.GONE);
        }

//        ed_AppliedOn.setEnabled(false);
//        ed_AppliedFor.setEnabled(false);
//        ed_leaveTye.setEnabled(false);
//        ed_EmpMess.setEnabled(false);
//        ed_AdminMess.setEnabled(false);
    }

    private void loadData() {
        if (ajax != null) {

            tv_fromdate.setText(ajax.getFromDate());
            tv_todate.setText(ajax.getToDate());
            tv_nooddays.setText(String.valueOf(ajax.getNoofdays()));
            tv_username.setText(ajax.getEmpName());
            tv_designation.setText(ajax.getEmpDesign());
//            tv_comp.setText(ajax.);
//            tv_branch.setText(ajax.);
//            tv_department.setText(ajax.);
            tv_emp.setText(String.valueOf(ajax.getEmpId()));
            ed_emp_mess.setText(ajax.getLeaveReason());
           // tv_branch.setText(String.valueOf(ajax.getNoofdays()));

        }
    }

    private void saveleave() {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(context, mView,  getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();
//            requestMap.put("compId", );
//            requestMap.put("globEmpId", Global.getLoginInfoData().getUserId());
//            requestMap.put("empid", );
//            requestMap.put("leaveTypeId", );
//            requestMap.put("leaveId",);
//            requestMap.put("leaveStatusNew", );
//            requestMap.put("branch",String.valueOf(branchPosition) );
//            requestMap.put("annTitle",title );



            webServiceHandler.saveAnnouncement(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag){

                        Utility.showCustomToast(context, mView, "successfully saved");

                    }else {
                        Utility.showCustomToast(context, mView,  "not send kindly try again");

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
                    //  Utility.callServerNotResponding(context);
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());

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

