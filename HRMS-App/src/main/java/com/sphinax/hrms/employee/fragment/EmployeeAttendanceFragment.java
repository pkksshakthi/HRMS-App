package com.sphinax.hrms.employee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnNavigationButtonClickListener;
import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.AnnouncementListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class EmployeeAttendanceFragment extends Fragment {

    private static Context context;
    private View mView;
    private CalendarView calendarView;
    private List<EventDay> events ;
    private TextView txt_present,txt_absent,txt_applyLeave;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ProgressDialog pdia;
    private static final String TAG = "EmployeeAttendanceFragment-";
    private CompanyData attendanceData;
    private ArrayList<Ajax> attendanceList;




    public EmployeeAttendanceFragment() {
        // Required empty public constructor
    }
    private static EmployeeAttendanceFragment instance;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_attendance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        loadComponent();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String dateValue =  sdf.format(c.getTime()).toString();
        fetchAttendanceReport(dateValue);

        calendarView.setOnPreviousButtonClickListener(new OnNavigationButtonClickListener() {
            @Override
            public void onClick() {

                Toast.makeText(getActivity(), calendarView.getCurrentPageDate().getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.setOnForwardButtonClickListener(new OnNavigationButtonClickListener() {
            @Override
            public void onClick() {

                Toast.makeText(getActivity(), calendarView.getCurrentPageDate().getTime().toString(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void loadComponent(){
         calendarView =  mView.findViewById(R.id.calendarView);
         txt_present =  mView.findViewById(R.id.txt_present);
         txt_absent =  mView.findViewById(R.id.txt_absert);
         txt_applyLeave =  mView.findViewById(R.id.txt_applyleave);
    }


    private void loaddatainview(){

        txt_present.setText(String.valueOf(attendanceData.getPresentCount()));
        txt_applyLeave.setText(String.valueOf(attendanceData.getLeaveApplied()));
        txt_absent.setText(String.valueOf(attendanceData.getAbsentCount()));

        attendanceList = (ArrayList<Ajax>) attendanceData.getAjax();
       loadCalendarData();

    }
private void loadCalendarData(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    calendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 01);
    try {
        calendarView.setDate(calendar);
    } catch (OutOfDateRangeException e) {
        e.printStackTrace();
    }

    events = new ArrayList<>();
    if(attendanceList !=null && attendanceList.size() >0){
        for (int counter = 0; counter < attendanceList.size(); counter++) {
            Ajax ajax = new Ajax();
            ajax = attendanceList.get(counter);

            Calendar mCal = (Calendar)calendar.clone();

            mCal.add(calendar.DAY_OF_MONTH, counter);

            if(ajax.getMorning().equalsIgnoreCase("H") &&ajax.getEvening().equalsIgnoreCase("H")){
                events.add(new EventDay(mCal, R.drawable.icon_red_box, Color.WHITE));
            }else if (ajax.getMorning().equalsIgnoreCase("A") &&ajax.getEvening().equalsIgnoreCase("A")){

                events.add(new EventDay(mCal, R.drawable.sample_icon_2, Color.WHITE));
            }else {
                events.add(new EventDay(mCal, R.drawable.sample_icon_3, Color.WHITE));
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
            HashMap<String, String> requestMap = new HashMap<String, String>();
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
                    Log.d(TAG, "size --> " + attendanceData.getAjax().size());

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
