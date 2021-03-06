package com.sphinax.hrms.employee.fragment;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.AnnouncementListAdapter;
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AnnouncementListFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    static final String[] Months = new String[]{"January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private static final String TAG = "AnnouncementListFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<String> years = new ArrayList<>();
    private View mView;

    private ImageView img_month;
    private ListView lv_announcement;
    private ProgressDialog pdia;
    private ArrayList<Ajax> announmentList;
    private AnnouncementListAdapter announcementListAdapter;
    private int year = 0;
    private int month = 0;
    private boolean firstTime = true;
    private FragmentManager fragmentManager;
    private TextView tv_noData;
    private int selectedYear = 2017;
    private int selectedMonth = 8;

    public AnnouncementListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        setListeners();
        loadSpinnerData();
        loadSpinnerCurrentDate();
        fetchAnnouncementList();
    }

    private void loadComponent() {
        //bt_display = mView.findViewById(R.id.bt_display);
        img_month = mView.findViewById(R.id.img_date);
        // sp_year = mView.findViewById(R.id.sp_year);
        tv_noData = mView.findViewById(R.id.tv_nodata);
        lv_announcement = mView.findViewById(R.id.lv_announcement_data);
    }

    private void setListeners() {
        img_month.setOnClickListener(this);
        //sp_year.setOnItemSelectedListener(this);
        //bt_display.setOnClickListener(this);
    }

    private void loadSpinnerCurrentDate() {
        Calendar c = Calendar.getInstance();
        int yearTemp = c.get(Calendar.YEAR);
        int monthTemp = c.get(Calendar.MONTH);
        year = years.indexOf(String.valueOf(yearTemp));
        month = monthTemp + 1;
        //sp_year.setSelection(year);
        //sp_month.setSelection(monthTemp);

    }

    private void loadSpinnerData() {
        years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
//        sp_year.setAdapter(adapter);
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Months);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        sp_month.setAdapter(arrayAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_date:
                getDateMonth();
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
                    announmentList = new ArrayList<>();
                    announmentList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + announmentList.size());

                    announcementListAdapter = new AnnouncementListAdapter(getActivity(), announmentList);
                    if (announmentList.size() == 0) {
                        lv_announcement.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.VISIBLE);
                    } else {
                        lv_announcement.setVisibility(View.VISIBLE);
                        tv_noData.setVisibility(View.GONE);
                        lv_announcement.setAdapter(announcementListAdapter);
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
            // Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
        }
    }
}
