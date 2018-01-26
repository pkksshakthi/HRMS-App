package com.sphinax.hrms.employee.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.utils.Utility;


public class UserMainMenuFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static Context context;
    private static UserMainMenuFragment instance;
    private View mView;
    private FragmentManager fragmentManager;
    private LinearLayout ll_mark_attendance, ll_attendance_report, ll_leave_app, ll_leave_mana, ll_info, ll_announcement, ll_payslip, ll_helpdesk;

    public UserMainMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserMainMenuFragment newInstance(String param1, String param2) {
        UserMainMenuFragment fragment = new UserMainMenuFragment();
        return fragment;
    }

    public static UserMainMenuFragment getInstance() {
        if (instance == null)
            instance = new UserMainMenuFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_main_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        loadComponent();
        setListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadComponent() {
        ll_mark_attendance = mView.findViewById(R.id.ll_mark_attendance);
        ll_attendance_report = mView.findViewById(R.id.ll_attendance_report);
        ll_leave_app = mView.findViewById(R.id.ll_leave_app);
        ll_leave_mana = mView.findViewById(R.id.ll_leave_mana);
        ll_info = mView.findViewById(R.id.ll_info);
        ll_announcement = mView.findViewById(R.id.ll_announcement);
        ll_payslip = mView.findViewById(R.id.ll_payslip);
        ll_helpdesk = mView.findViewById(R.id.ll_helpdesk);
    }

    private void setListeners() {
        ll_mark_attendance.setOnClickListener(this);
        ll_attendance_report.setOnClickListener(this);
        ll_leave_app.setOnClickListener(this);
        ll_leave_mana.setOnClickListener(this);
        ll_info.setOnClickListener(this);
        ll_announcement.setOnClickListener(this);
        ll_payslip.setOnClickListener(this);
        ll_helpdesk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mark_attendance:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new AttendanceEnterFragment(), true, null, Constants.FRAMENT_ANTTENDANCE_ENTER);
                break;
            case R.id.ll_attendance_report:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EmployeeAttendanceFragment(), true, null, Constants.FRAMENT_ANNOUNCEMENT_LIST);
                break;
            case R.id.ll_leave_app:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new ApplyLeaveFragment(), true, null, Constants.FRAMENT_LEAVE_APPLY);
                break;
            case R.id.ll_leave_mana:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EmployeeLeaveManagementFragment(), true, null, Constants.FRAMENT_LEAVE_MANAGEMENT);
                break;
            case R.id.ll_info:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new UserProfileFragment(), true, null, Constants.FRAMENT_USER_INFO);
                break;
            case R.id.ll_announcement:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new AnnouncementListFragment(), true, null, Constants.FRAMENT_ANNOUNCEMENT_LIST);
                break;
            case R.id.ll_payslip:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new PaySlipFragment(), true, null, Constants.FRAMENT_PAYSLIP);
                break;
            case R.id.ll_helpdesk:
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EnterHRHelpdeskFragment(), true, null, Constants.FRAMENT_HR_HELPDESK_ENTER);
                break;
        }
    }

}
