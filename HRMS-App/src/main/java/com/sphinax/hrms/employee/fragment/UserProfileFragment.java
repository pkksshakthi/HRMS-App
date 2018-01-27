package com.sphinax.hrms.employee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
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


public class UserProfileFragment extends Fragment {

    private static final String TAG = "UserProfileFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
    private ImageView iv_user_image;
    private TextView tv_username, tv_empid, tv_company_name, tv_branch, tv_department, tv_designation, tv_mobile_no, tv_email_id, tv_address;
    private ProgressDialog pdia;
    private ArrayList<Ajax> userInfoList;


    public UserProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        loadComponent();
        fetchUserInfo();
    }

    private void loadComponent() {
        tv_username = mView.findViewById(R.id.tv_username);
        tv_empid = mView.findViewById(R.id.tv_empid);
        tv_company_name = mView.findViewById(R.id.tv_company_name);
        tv_branch = mView.findViewById(R.id.tv_branch);
        tv_department = mView.findViewById(R.id.tv_department);
        tv_designation = mView.findViewById(R.id.tv_designation);
        tv_mobile_no = mView.findViewById(R.id.tv_mobile_no);
        tv_email_id = mView.findViewById(R.id.tv_email_id);
        tv_address = mView.findViewById(R.id.tv_address);
        iv_user_image = mView.findViewById(R.id.iv_user_image);
    }

    private void setUserInfoData() {
        if (userInfoList != null) {
            if (userInfoList.get(0).getEmpName() != null) {
                tv_username.setText(userInfoList.get(0).getEmpName());
            }
            if (userInfoList.get(0).getEmpId() != null) {
                tv_empid.setText(userInfoList.get(0).getEmpId());
            }
            if (userInfoList.get(0).getCompName() != null) {
                tv_company_name.setText(userInfoList.get(0).getCompName());
            }
            if (userInfoList.get(0).getBranchName() != null) {
                tv_branch.setText(userInfoList.get(0).getBranchName());
            }
            if (userInfoList.get(0).getEmpDept() != null) {
                tv_department.setText(userInfoList.get(0).getEmpDept());
            }
            if (userInfoList.get(0).getEmpDesign() != null) {
                tv_designation.setText(userInfoList.get(0).getEmpDesign());
            }
            if (userInfoList.get(0).getEmpMobile() != null) {
                tv_mobile_no.setText(String.valueOf(userInfoList.get(0).getEmpMobile()));
            }
//            if (userInfoList.get(0).get!=null){
//                tv_email_id.setText(userInfoList.get(0).get);
//            }
            if (userInfoList.get(0).getAddress() != null) {
                tv_address.setText(userInfoList.get(0).getAddress());
            }
        }

    }

    private void fetchUserInfo() {
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
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());

            webServiceHandler.getUserInfo(getActivity(), context, requestMap, new ServiceCallback() {

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
                    userInfoList = new ArrayList<>();
                    userInfoList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + userInfoList.size());
                    setUserInfoData();


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
