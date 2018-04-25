package com.sphinax.hrms.admin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;

/**
 * Created by ganesaka on 4/26/2018.
 */

public class AdminHrHelpDeskFull extends Fragment {

    private static final String TAG = "EmployeeLeaveFullContentFragment-";
    private static Context context;
    private View mView;
    private EditText ed_leaveTye, ed_EmpMess, ed_AdminMess;
    private TextView tv_fromDate, tv_toDate, tv_startSession, tv_endSession, tv_leaveStatus,ed_AppliedOn,ed_AppliedFor;
    private Ajax ajax;
    private FragmentManager fragmentManager;
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

       // loadData();
    }

    private void loadComponent() {
//        tv_fromDate = mView.findViewById(R.id.tv_fromdate);
//        tv_startSession = mView.findViewById(R.id.tv_session_start);
//        tv_toDate = mView.findViewById(R.id.tv_toDate);
//        tv_endSession = mView.findViewById(R.id.tv_session_end);
//        ed_AppliedOn = mView.findViewById(R.id.ed_applied_on);
//        ed_AppliedFor = mView.findViewById(R.id.ed_applied_for);
//        ed_leaveTye = mView.findViewById(R.id.ed_leave_type);
//        ed_EmpMess = mView.findViewById(R.id.ed_emp_mess);
//        ed_AdminMess = mView.findViewById(R.id.ed_admin_mess);
//        tv_leaveStatus = mView.findViewById(R.id.tv_leaetype);
//        ed_AppliedOn.setEnabled(false);
//        ed_AppliedFor.setEnabled(false);
//        ed_leaveTye.setEnabled(false);
//        ed_EmpMess.setEnabled(false);
//        ed_AdminMess.setEnabled(false);
    }

//    private void loadData() {
//        if (ajax != null) {
//
//            tv_leaveStatus.setText(ajax.getLeaveStatusDesc());
//            tv_fromDate.setText(ajax.getFromDate());
//            tv_startSession.setText(ajax.getFromsessionDesc());
//            tv_toDate.setText(ajax.getToDate());
//            tv_endSession.setText(ajax.getToSessionDesc());
//            ed_AppliedOn.setText(ajax.getAppliedOn());
//            ed_AppliedFor.setText(String.valueOf(ajax.getNoofdays()));
//            ed_leaveTye.setText(ajax.getLeaveTypeDesc());
//            ed_EmpMess.setText(ajax.getEmployeeDescription());
//            ed_AdminMess.setText(ajax.getRemarks());
//            if (ajax.getLeaveStatusDesc().equalsIgnoreCase("PENDING")) {
//                getActivity().findViewById(R.id.ln_admin_msg).setVisibility(View.GONE);
//            }
//        }
//    }


}

