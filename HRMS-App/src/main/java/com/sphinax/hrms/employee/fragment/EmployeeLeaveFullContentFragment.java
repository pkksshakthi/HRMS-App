package com.sphinax.hrms.employee.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;


public class EmployeeLeaveFullContentFragment extends Fragment {

    private static final String TAG = "EmployeeLeaveFullContentFragment-";
    private static Context context;
    private View mView;
    private EditText ed_AppliedOn, ed_AppliedFor, ed_leaveTye, ed_EmpMess, ed_AdminMess;
    private TextView tv_fromDate, tv_toDate, tv_startSession, tv_endSession, tv_leaveStatus;
    private Ajax ajax;
    private FragmentManager fragmentManager;
    public EmployeeLeaveFullContentFragment() {
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
        return inflater.inflate(R.layout.fragment_employee_leave_full_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        ajax = (Ajax) getArguments().getSerializable("UserValidateObject");
        loadData();
    }

    private void loadComponent() {
        tv_fromDate = mView.findViewById(R.id.tv_fromdate);
        tv_startSession = mView.findViewById(R.id.tv_session_start);
        tv_toDate = mView.findViewById(R.id.tv_toDate);
        tv_endSession = mView.findViewById(R.id.tv_session_end);
        ed_AppliedOn = mView.findViewById(R.id.ed_applied_on);
        ed_AppliedFor = mView.findViewById(R.id.ed_applied_for);
        ed_leaveTye = mView.findViewById(R.id.ed_leave_type);
        ed_EmpMess = mView.findViewById(R.id.ed_emp_mess);
        ed_AdminMess = mView.findViewById(R.id.ed_admin_mess);
        tv_leaveStatus = mView.findViewById(R.id.tv_leaetype);
        ed_AppliedOn.setEnabled(false);
        ed_AppliedFor.setEnabled(false);
        ed_leaveTye.setEnabled(false);
        ed_EmpMess.setEnabled(false);
        ed_AdminMess.setEnabled(false);
    }

    private void loadData() {
        if (ajax != null) {

            tv_leaveStatus.setText(ajax.getLeaveStatusDesc());
            tv_fromDate.setText(ajax.getFromDate());
            tv_startSession.setText(ajax.getFromsessionDesc());
            tv_toDate.setText(ajax.getToDate());
            tv_endSession.setText(ajax.getToSessionDesc());
            ed_AppliedOn.setText(ajax.getAppliedOn());
            ed_AppliedFor.setText(String.valueOf(ajax.getNoofdays()));
            ed_leaveTye.setText(ajax.getLeaveTypeDesc());
            ed_EmpMess.setText(ajax.getEmployeeDescription());
            ed_AdminMess.setText(ajax.getRemarks());

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

                    if (ajax.getLeaveStatusDesc().equalsIgnoreCase("PENDING")){
                        Global.setTabPosition(1);
                    }else if (ajax.getLeaveStatusDesc().equalsIgnoreCase("APPROVED")){
                        Global.setTabPosition(0);
                    }else{
                        Global.setTabPosition(2);
                    }
                    Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EmployeeLeaveManagementFragment(), false, null, Constants.FRAMENT_LEAVE_MANAGEMENT);

                    return true;

                }

                return false;
            }
        });

    }
}
