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
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class EmployeeAttendanceFragment extends Fragment {

    private static final String TAG = "EmployeeAttendanceFragment-";
    private static Context context;
    private static EmployeeAttendanceFragment instance;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
    private CalendarView calendarView;
    private TextView txt_present, txt_absent, txt_applyLeave;
    private ProgressDialog pdia;
    private CompanyData attendanceData;
    private ArrayList<Ajax> attendanceList;
    private String dateValue;
    private int Year, Month;
    private FragmentManager fragmentManager;

    public EmployeeAttendanceFragment() {
        // Required empty public constructor
    }

    public static EmployeeAttendanceFragment getInstance() {
        if (instance == null)
            instance = new EmployeeAttendanceFragment();
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        Calendar c = Calendar.getInstance();
        dateValue = sdf.format(c.getTime());
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        fetchAttendanceReport(dateValue);

        calendarView.setOnPreviousButtonClickListener(() -> {

//                Toast.makeText(getActivity(), calendarView.getCurrentPageDate().getTime().toString(), Toast.LENGTH_SHORT).show();
            Year = calendarView.getCurrentPageDate().get(Calendar.YEAR);
            Month = calendarView.getCurrentPageDate().get(Calendar.MONTH);
            dateValue = "01" + "/" + (Month + 1) + "/" + Year;
            fetchAttendanceReport(dateValue);

        });

        calendarView.setOnForwardButtonClickListener(() -> {

//                Toast.makeText(getActivity(), calendarView.getCurrentPageDate().getTime().toString(), Toast.LENGTH_SHORT).show();
            Year = calendarView.getCurrentPageDate().get(Calendar.YEAR);
            Month = calendarView.getCurrentPageDate().get(Calendar.MONTH);
            dateValue = "01" + "/" + (Month + 1) + "/" + Year;
            fetchAttendanceReport(dateValue);

        });


    }

    private void loadComponent() {
        calendarView = mView.findViewById(R.id.calendarView);
        txt_present = mView.findViewById(R.id.txt_present);
        txt_absent = mView.findViewById(R.id.txt_absert);
        txt_applyLeave = mView.findViewById(R.id.txt_applyleave);
    }


    private void loaddatainview() {

        txt_present.setText(Global.nullChecking("" + attendanceData.getPresentCount()));
        txt_applyLeave.setText(Global.nullChecking("" + attendanceData.getLeaveApplied()));
        txt_absent.setText(Global.nullChecking("" + attendanceData.getAbsentCount()));

        attendanceList = (ArrayList<Ajax>) attendanceData.getAjax();
        loadCalendarData(Year, Month);

    }

    @SuppressLint("LongLogTag")
    private void loadCalendarData(int year, int month) {
        Log.d(TAG, "loadCalendarData: " + year);
        Log.d(TAG, "loadCalendarData: " + Month);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        try {
            calendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        List<EventDay> events = new ArrayList<>();
        if (attendanceList != null && attendanceList.size() > 0) {
            for (int counter = 0; counter < attendanceList.size(); counter++) {
                //noinspection UnusedAssignment
                @SuppressWarnings("UnusedAssignment") Ajax ajax = new Ajax();
                ajax = attendanceList.get(counter);

                Calendar mCal = (Calendar) calendar.clone();

                mCal.add(Calendar.DAY_OF_MONTH, counter);

                if (ajax.getMorning().equalsIgnoreCase("H") && ajax.getEvening().equalsIgnoreCase("H")) {
                    events.add(new EventDay(mCal, R.drawable.icon_red_box));
                } else if (ajax.getMorning().equalsIgnoreCase("A") && ajax.getEvening().equalsIgnoreCase("A")) {

                    events.add(new EventDay(mCal, R.drawable.icon_absent));
                } else {
                    events.add(new EventDay(mCal, R.drawable.icon_holiday));
                }

            }
            calendarView.setEvents(events);
        }

    }

    private void fetchAttendanceReport(String dateValue) {
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
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("branchId", String.valueOf(Global.getUserInfoData().getEmpBranchId()));
            requestMap.put("deptId", String.valueOf(Global.getLoginInfoData().getDeptId()));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("datChs", dateValue);

            webServiceHandler.getAttendanceReport(getActivity(), context, requestMap, new ServiceCallback() {

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
                    attendanceData = new CompanyData();
                    attendanceData = companyData;
                    //Log.d(TAG, "size --> " + attendanceData.getAjax().size());

                    loaddatainview();

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
