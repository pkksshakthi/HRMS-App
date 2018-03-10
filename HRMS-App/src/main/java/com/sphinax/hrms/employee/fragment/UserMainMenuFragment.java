package com.sphinax.hrms.employee.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.RequestPermissionHandler;
import com.sphinax.hrms.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.LOCATION_SERVICE;


public class UserMainMenuFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> userInfoList;
    private static UserMainMenuFragment instance;
    private ProgressDialog pdia;
    private View mView;
    private LinearLayout ll_mark_attendance, ll_attendance_report, ll_leave_app, ll_leave_mana, ll_info, ll_announcement, ll_payslip, ll_helpdesk;
    private static final String TAG = "UserMainMenuFragment-";
    private static final int PERMISSION_CALLBACK_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING = 102;
    private View view;
    private FragmentManager fragmentManager;
    private RequestPermissionHandler mRequestPermissionHandler;


    public UserMainMenuFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
            }
        }
    }


    private void handleButtonClicked(){
        mRequestPermissionHandler.requestPermission(getActivity(), new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
               // Toast.makeText(getActivity(), "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(getActivity(), "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });
//        mRequestPermissionHandler.requestPermission(getActivity(), new String[] {
//                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//        }, 124, new RequestPermissionHandler.RequestPermissionListener() {
//            @Override
//            public void onSuccess() {
//               // Toast.makeText(getActivity(), "request permission success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailed() {
//                Toast.makeText(getActivity(), "request permission failed", Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    @Override
    public void onResume() {
        super.onResume();

        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());
        }

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        loadComponent();
        setListeners();
        mRequestPermissionHandler = new RequestPermissionHandler();

        handleButtonClicked();
        if(!Global.isUserDataTaken()){
            fetchUserInfo();
        }
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


                if (locationService())
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

    private boolean locationService() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                // Show location settings when the user acknowledges the alert dialog
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            return false;
        }else{
            return true;
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
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
            requestMap.put("empId",Global.getLoginInfoData().getUserId());

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
