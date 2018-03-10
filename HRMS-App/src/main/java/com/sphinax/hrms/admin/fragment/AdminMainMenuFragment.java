package com.sphinax.hrms.admin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sphinax.hrms.R;
import com.sphinax.hrms.utils.Utility;


public class AdminMainMenuFragment extends Fragment implements View.OnClickListener {

    // --Commented out by Inspection (3/5/2018 1:07 AM):private static final String TAG = "AdminMainMenuFragment-";
    private static Context context;
    private static AdminMainMenuFragment instance;
    private View mView;
    private FragmentManager fragmentManager;
    private LinearLayout ll_attendance_report, ll_leave_mana, ll_announcement, ll_helpdesk;


    public AdminMainMenuFragment() {
        // Required empty public constructor
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public static AdminMainMenuFragment getInstance() {
//        if (instance == null)
//            instance = new AdminMainMenuFragment();
//        return instance;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();


        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        setListeners();


    }


    private void loadComponent() {
        ll_attendance_report = mView.findViewById(R.id.ll_attendance_report);
        ll_leave_mana = mView.findViewById(R.id.ll_leave_mana);
        ll_announcement = mView.findViewById(R.id.ll_announcement);
        ll_helpdesk = mView.findViewById(R.id.ll_helpdesk);
    }

    private void setListeners() {
        ll_attendance_report.setOnClickListener(this);
        ll_leave_mana.setOnClickListener(this);
        ll_announcement.setOnClickListener(this);
        ll_helpdesk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_attendance_report:
                //    Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EmployeeAttendanceFragment(), true, null, Constants.FRAMENT_ANNOUNCEMENT_LIST);
                break;
            case R.id.ll_leave_mana:
                Utility.addFragment((Activity) context, R.id.content_frame, fragmentManager, new LeaveManagementFragment(), true, null, "");
                break;
            case R.id.ll_announcement:
                Utility.addFragment((Activity) context, R.id.content_frame, fragmentManager, new AnnouncementCreateFragment(), true, null, "");
                break;
            case R.id.ll_helpdesk:
                //   Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EnterHRHelpdeskFragment(), true, null, Constants.FRAMENT_HR_HELPDESK_ENTER);
                break;
        }
    }

}
