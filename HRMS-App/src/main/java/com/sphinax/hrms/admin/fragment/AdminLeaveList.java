package com.sphinax.hrms.admin.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.RecyclerTouchListener;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.AdminHrHelpDeskAdapter;
import com.sphinax.hrms.view.AdminLeaveAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ganesaka on 4/26/2018.
 */

public class AdminLeaveList extends Fragment {

    private static final String TAG = "LeaveApproveListFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
    private List<Ajax> approveList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdminLeaveAdapter mAdapter;
    private ProgressDialog pdia;
    private FragmentManager fragmentManager;
    private int reqId;
    private TextView tv_type;
    private  String typeUser;

    public AdminLeaveList() {
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
        return inflater.inflate(R.layout.fragment_admin_leave_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        typeUser = (String)getArguments().getSerializable("Usertype");
        loadComponent();
        if(typeUser.equalsIgnoreCase("approval")){
            reqId = 1;
            tv_type.setText("Approval");
        }else if(typeUser.equalsIgnoreCase("reject")){
            reqId = 2;
            tv_type.setText("Reject");
        }else if(typeUser.equalsIgnoreCase("pending")){
            reqId = 3;
            tv_type.setText("Pending");
        }





        mAdapter = new AdminLeaveAdapter(approveList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView .addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        fetchList();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Ajax ajaxApp = approveList.get(position);

                Global.setLeaveSatus(typeUser);
                Bundle b = new Bundle();
                b.putSerializable("UserValidateObject",ajaxApp);
                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new AdminLeaveFullContent(), true, b, Constants.FRAMENT_USER_MENU);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void loadComponent() {
        recyclerView = mView.findViewById(R.id.itemsRecyclerView);
        tv_type = mView.findViewById(R.id.tv_usertype);

    }



    private void fetchList() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            if (mView!=null) {


                Utility.showCustomToast(context, mView, getResources().getString(R.string.invalidInternetConnection));
            }
            return;
        }
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("adminId", Global.getLoginInfoData().getUserId());
            requestMap.put("empId", "all");
            requestMap.put("department", "all");
            requestMap.put("branch", "all");
            requestMap.put("leavestatus", String.valueOf(reqId));

            webServiceHandler.getAdminLeaveList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    CompanyData companyData = (CompanyData) obj;
                    approveList = new ArrayList<>();
                    approveList = companyData.getAjax();
                    Log.d(TAG, "size --> " + approveList.size());
                    mAdapter = new AdminLeaveAdapter(approveList);
                    recyclerView.setAdapter(mAdapter);



                    if(typeUser.equalsIgnoreCase("approval")){
                        tv_type.setText("Approval" + " [" +  + approveList.size() + "] ");
                    }else if(typeUser.equalsIgnoreCase("reject")){
                        tv_type.setText("Reject" + " [" +  + approveList.size() + "] ");
                    }else if(typeUser.equalsIgnoreCase("pending")){
                        tv_type.setText("Pending" + " [" +  + approveList.size() + "] ");
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
    @Override
    public void onResume() {
        super.onResume();
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());
        }

    }
}

