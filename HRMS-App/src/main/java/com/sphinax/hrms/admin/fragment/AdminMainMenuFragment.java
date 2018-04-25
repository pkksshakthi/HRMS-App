package com.sphinax.hrms.admin.fragment;

import android.app.Activity;
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
import android.widget.LinearLayout;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.employee.fragment.UserMainMenuFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class AdminMainMenuFragment extends Fragment implements View.OnClickListener {

    // --Commented out by Inspection (3/5/2018 1:07 AM):private static final String TAG = "AdminMainMenuFragment-";
    private static Context context;
    private static AdminMainMenuFragment instance;
    private View mView;
    private FragmentManager fragmentManager;
    private LinearLayout ll_attendance_report, ll_leave_mana, ll_announcement, ll_helpdesk;

    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> userInfoList;
    private ProgressDialog pdia;
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
        if(!Global.isUserDataTaken()){
            fetchUserInfo();
        }

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
                Utility.addFragment( (Activity) context, R.id.content_frame,fragmentManager, new AttendanceReportFragment(), true, null,"");
                break;
            case R.id.ll_leave_mana:
                Utility.addFragment((Activity) context, R.id.content_frame, fragmentManager, new LeaveManagementFragment(), true, null, "");
                break;
            case R.id.ll_announcement:
                Utility.addFragment((Activity) context, R.id.content_frame, fragmentManager, new AdminAnnouncementListFragment(), true, null, Constants.FRAMENT_ADMIN_ANNOUNCEMENT_LIST);
                break;
            case R.id.ll_helpdesk:
                   Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new HRHelpdeskFragment(), true, null, Constants.FRAMENT_HR_HELPDESK_ENTER);
                break;
        }
    }
    private void fetchUserInfo() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showCustomToast(context,mView, getResources().getString(R.string.invalidInternetConnection));
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
            requestMap.put("empId", Global.getLoginInfoData().getUserId());

            webServiceHandler.getUserInfo(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    Log.d(TAG , " "+ flag);
                    if(flag){
                        Global.setUserDataTaken(flag);
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    CompanyData companyData = (CompanyData) obj;
                    userInfoList = new ArrayList<>();
                    userInfoList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "userInfoList --> " + userInfoList.size());
                    Global.setUserInfoData(userInfoList.get(0));


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

}
