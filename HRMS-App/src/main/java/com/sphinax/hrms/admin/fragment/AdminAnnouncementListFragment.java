package com.sphinax.hrms.admin.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.employee.fragment.EmployeeLeaveFullContentFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.RecyclerTouchListener;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.AdminAnnouncementListAdapter;
import com.sphinax.hrms.view.AnnouncementListAdapter;
import com.sphinax.hrms.view.EmployeeLeaveListAdapter;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminAnnouncementListFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "AdminAnnouncementListFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
   private List<Ajax> announcmentList = new ArrayList<>();
    private RecyclerView recyclerView;
   private AdminAnnouncementListAdapter mAdapter;
    private ProgressDialog pdia;
    private FragmentManager fragmentManager;
    private ImageView img_month;
    private Button bt_new;
    private int selectedYear = 2017;
    private int selectedMonth = 8;
    private int year = 0;
    private int month = 0;
    private ArrayList<String> years = new ArrayList<>();



    public AdminAnnouncementListFragment() {
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
        return inflater.inflate(R.layout.fragment_admin_announcementlist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        setListeners();
        loadSpinnerData();
        loadSpinnerCurrentDate();





        mAdapter = new AdminAnnouncementListAdapter(announcmentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView .addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        fetchAnnouncementList();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Ajax ajaxApp = announcmentList.get(position);

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Fragment prevFrag = fragmentManager.findFragmentByTag(Constants.FRAMENT_LEAVE_LIST_CONTENT);

                if(prevFrag!=null)
                    fragmentTransaction.remove(prevFrag);

                EmployeeLeaveFullContentFragment fragment = new EmployeeLeaveFullContentFragment();

                Bundle b = new Bundle();

                //  b.putInt("position", position);
                b.putSerializable("UserValidateObject",ajaxApp);
                fragment.setArguments(b);

                fragmentTransaction.add(R.id.content_frame, fragment,Constants.FRAMENT_LEAVE_LIST_CONTENT);

                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @SuppressLint("WrongViewCast")
    private void loadComponent() {
        recyclerView = mView.findViewById(R.id.lv_announcement_data);
        img_month = mView.findViewById(R.id.img_date);
        bt_new = mView.findViewById(R.id.bt_new);

    }

    private void setListeners() {
        img_month.setOnClickListener(this);
        //sp_year.setOnItemSelectedListener(this);
        bt_new.setOnClickListener(this);
    }

    private void loadSpinnerCurrentDate() {
        Calendar c = Calendar.getInstance();
        int yearTemp = c.get(Calendar.YEAR);
        int monthTemp = c.get(Calendar.MONTH);
        year = years.indexOf(String.valueOf(yearTemp));
        month = monthTemp + 1;
    }

    private void loadSpinnerData() {
        years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

    }

    private void fetchAnnouncementList() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showCustomToast(context, mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.setCancelable(false);
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("month", String.valueOf(selectedMonth));
            requestMap.put("year", String.valueOf(selectedYear));
            requestMap.put("branch", String.valueOf(Global.getUserInfoData().getEmpBranchId()));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("deptId", String.valueOf(Global.getLoginInfoData().getDeptId()));

            webServiceHandler.getAnnouncementList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    announcmentList = new ArrayList<>();
                    announcmentList = companyData.getAjax();
                    Log.d(TAG, "size --> " + announcmentList.size());
                    mAdapter = new AdminAnnouncementListAdapter(announcmentList);
                    recyclerView.setAdapter(mAdapter);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_date:
                getDateMonth();
                break;
            case R.id.bt_new:
                callCreateAnnoucment();
                break;
        }
    }


    private void getDateMonth() {
        YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(getActivity(), (year, month) -> {

            selectedYear = year;
            selectedMonth = month+1;
            fetchAnnouncementList();
        });
        yearMonthPickerDialog.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_year:
                year = position;
                break;
            case R.id.sp_month:
                month = position + 1;
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
 private void callCreateAnnoucment(){
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment prevFrag = fragmentManager.findFragmentByTag(Constants.FRAMENT_LEAVE_LIST_CONTENT);

        if(prevFrag!=null)
            fragmentTransaction.remove(prevFrag);

        AnnouncementCreateFragment fragment = new AnnouncementCreateFragment();

        fragment.setArguments(null);

        fragmentTransaction.add(R.id.content_frame, fragment,Constants.FRAMENT_LEAVE_LIST_CONTENT);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}
