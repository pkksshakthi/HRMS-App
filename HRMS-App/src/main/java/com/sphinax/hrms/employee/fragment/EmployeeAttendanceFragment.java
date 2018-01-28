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
        fetchAttendanceReport();

































         events = new ArrayList<>();
  /* Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.sample_icon_1));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, 6);
        events.add(new EventDay(calendar1, R.drawable.sample_icon_2));*/

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, -5);
        events.add(new EventDay(calendar2, R.drawable.icon_red_box, Color.WHITE));

        //CalendarView calendarView = (CalendarView) mView.findViewById(R.id.calendarView);

        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -4);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 60);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(eventDay ->
                Toast.makeText(getActivity(),
                        eventDay.getCalendar().getTime().toString(),
                        Toast.LENGTH_SHORT).show());

        Button setDateButton = (Button) mView.findViewById(R.id.setDateButton);
        setDateButton.setOnClickListener(v -> {
            try {
                calendarView.setDate(getRandomCalendar());
            } catch (OutOfDateRangeException exception) {
                exception.printStackTrace();

                Toast.makeText(getActivity(),
                        "Date is out of range",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadComponent(){
         calendarView =  mView.findViewById(R.id.calendarView);
         txt_present =  mView.findViewById(R.id.txt_present);
         txt_absent =  mView.findViewById(R.id.txt_absert);
         txt_applyLeave =  mView.findViewById(R.id.txt_applyleave);
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }

    private void loaddatainview(){

        txt_present.setText(attendanceData.getPresentCount().toString());
        txt_applyLeave.setText(attendanceData.getLeaveApplied().toString());
        txt_absent.setText(attendanceData.getAbsentCount().toString());

        attendanceList = (ArrayList<Ajax>) attendanceData.getAjax();


    }


    private void fetchAttendanceReport() {
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
            requestMap.put("datChs", "01/11/2017");

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

                    loaddatainview();

//                    announmentList = new ArrayList<>();
//                    announmentList = (ArrayList<Ajax>) companyData.getAjax();
//                    Log.d(TAG, "size --> " + announmentList.size());
//
//                    announcementListAdapter = new AnnouncementListAdapter(getActivity(), announmentList);
//                    lv_announcement.setAdapter(announcementListAdapter);

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
