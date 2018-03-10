package com.sphinax.hrms.employee.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sphinax.hrms.utils.RecyclerTouchListener;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.EmployeeLeaveListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LeaveApproveListFragment extends Fragment {

    private static final String TAG = "LeaveApproveListFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Ajax> approveList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmployeeLeaveListAdapter mAdapter;
    private ProgressDialog pdia;
    private FragmentManager fragmentManager;


    public LeaveApproveListFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//
//            // load data here
//            fetchLeaveList();
//        }else{
//            // fragment is no longer visible
//        }
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leave_approve_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });


        mAdapter = new EmployeeLeaveListAdapter(approveList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView .addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
//        ViewPager host = getActivity().findViewById(R.id.pager);
//        try {
//
//            host.setCurrentItem(Global.getTabPosition());
//        }catch (Exception e){
//            e.printStackTrace();
//           // host.setCurrentItem(2);
//        }
        //if(Global.getTabPosition() == 0){
            fetchLeaveList();

       // }
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Ajax ajaxApp = approveList.get(position);
                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
//                Bundle b = new Bundle();
//                b.putSerializable("UserValidateObject",ajaxApp);
//                Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new EmployeeLeaveFullContentFragment(), true, b, Constants.FRAMENT_LEAVE_LIST_CONTENT);

                FragmentManager fragmentManager = getFragmentManager();

                /** Getting the fragmenttransaction object, which can be used to add, remove or replace a fragment */
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                /** Getting the existing detailed fragment object, if it already exists.
                 *  The fragment object is retrieved by its tag name
                 * */
                Fragment prevFrag = fragmentManager.findFragmentByTag(Constants.FRAMENT_LEAVE_LIST_CONTENT);

                /** Remove the existing detailed fragment object if it exists */
                if(prevFrag!=null)
                    fragmentTransaction.remove(prevFrag);

                /** Instantiating the fragment CountryDetailsFragment */
                EmployeeLeaveFullContentFragment fragment = new EmployeeLeaveFullContentFragment();

                /** Creating a bundle object to pass the data(the clicked item's position) from the activity to the fragment */
                Bundle b = new Bundle();

                /** Setting the data to the bundle object */
              //  b.putInt("position", position);
                b.putSerializable("UserValidateObject",ajaxApp);
                /** Setting the bundle object to the fragment */
                fragment.setArguments(b);

                /** Adding the fragment to the fragment transaction */
                fragmentTransaction.add(R.id.content_frame, fragment,Constants.FRAMENT_LEAVE_LIST_CONTENT);

                /** Adding this transaction to backstack */
                fragmentTransaction.addToBackStack(null);

                /** Making this transaction in effect */
                fragmentTransaction.commit();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void loadComponent() {
        recyclerView = mView.findViewById(R.id.itemsRecyclerView);
        mSwipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);

    }

    void refreshItems() {
        // Load items
        // ...
        fetchLeaveList();
        // Load complete

    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
        //mSwipeRefreshLayout.setEnabled(false);

    }


    private void fetchLeaveList() {
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
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("leavestatus", "1");
            //requestMap.put("leavestatus", "All");
            requestMap.put("empId", Global.getLoginInfoData().getUserId());

            webServiceHandler.getEmpLeaveList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    mAdapter = new EmployeeLeaveListAdapter(approveList);
                    recyclerView.setAdapter(mAdapter);

                    onItemsLoadComplete();

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
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);

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
            Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }

    }
}
