package com.sphinax.hrms.admin.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.employee.fragment.EmployeeAttendanceFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.AdminAjax;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.CompanySpinnerAdapter;
import com.sphinax.hrms.view.DataSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AttendanceReportFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener  {


    public AttendanceReportFragment() {
        // Required empty public constructor
    }
    private static Context context;
    private View mView;
    private ProgressDialog pdia;
    private ArrayList<Ajax> companyList;
    private ArrayList<Ajax> branchList;
    private ArrayList<Ajax> departmentList;
    private ArrayList<Ajax> empList;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private Spinner spCompany,spBranch,spDepartment,spEmp;
    private Button btSubmit;
    //private EditText emp_id;
    private CompanySpinnerAdapter companyDataAdapter;
    private CompanySpinnerAdapter branchDataAdapter;
    private CompanySpinnerAdapter departmentDataAdapter;
    private int companyPosition = 0;
    private int branchPosition = 0;
    private int departmentPosition = 0;
    private FragmentManager fragmentManager;
    private static final String TAG = "AttendanceReportFragment-";
    private static EmployeeAttendanceFragment instance;
    private CalendarView calendarView;
    private TextView txt_present, txt_absent, txt_applyLeave;
    private CompanyData attendanceData;
    private ArrayList<Ajax> attendanceList;
    private String dateValue;
    private int Year, Month;
    private String empPosition ;
    private DataSpinnerAdapter empSpinnerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_attendance_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        loadComponent();
        setListeners();
        fetchCompanyList();
        fragmentManager = getActivity().getSupportFragmentManager();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        Calendar c = Calendar.getInstance();
        dateValue = sdf.format(c.getTime());
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
       // fetchAttendanceReport(dateValue);

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
        spCompany = mView.findViewById(R.id.sp_company);
        spBranch = mView.findViewById(R.id.sp_branch);
        spDepartment = mView.findViewById(R.id.sp_department);
        spEmp = mView.findViewById(R.id.sp_empname);
        btSubmit = mView.findViewById(R.id.bt_submit);
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

    private void setListeners() {
        spCompany.setOnItemSelectedListener(this);
        spBranch.setOnItemSelectedListener(this);
        spDepartment.setOnItemSelectedListener(this);
        btSubmit.setOnClickListener(this);
        spEmp.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_company:
                actionCompanySelector(position);
                break;
            case R.id.sp_branch:
                actionBranchSelector(position);
                break;
            case R.id.sp_department:
                actionDepartmentSelector(position);
                break;
            case R.id.sp_empname:
                actionEmpSelector(position);
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Its works on Company Spinner Click Action
     **/
    private void actionCompanySelector(int position) {
        //spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (companyList != null) {
            ajax = companyList.get(position);
        }
        companyPosition = ajax.getCompId();

            fetchBranchList(ajax.getCompId());
    }

    private void actionBranchSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (branchList != null) {
            ajax = branchList.get(position);
        }
        branchPosition = ajax.getBranchId();

        fetchDepartmentList(ajax.getBranchId());

    }

    private void actionDepartmentSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (departmentList != null) {
            ajax = departmentList.get(position);
        }
        departmentPosition = ajax.getDeptId();
        fetchEmpList();
    }

    private void actionEmpSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (empList != null) {
            ajax = empList.get(position);
        }
        empPosition = ajax.getEmpId();
        Log.d("leaveStatus"," " +empPosition);


        //fetchDepartmentList(ajax.getCompId());
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == btSubmit.getId()) {
           // Ajax ajax = new Ajax();

            if(companyPosition !=  0 && branchPosition != 0 && departmentPosition != 0 && !empPosition.equalsIgnoreCase("")){
                fetchAttendanceReport(dateValue);
            }else{
                Utility.showCustomToast(context, mView,  "Please Select the all feilds");

            }


        }

    }
       private void fetchCompanyList() {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(context, mView,  getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();

            webServiceHandler.getCompanyList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    companyList = new ArrayList<>();
                    companyList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + companyList.size());

                    companyDataAdapter = new CompanySpinnerAdapter(context,
                            companyList,1);
                    companyDataAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCompany.setAdapter(companyDataAdapter);

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
                    //  Utility.callServerNotResponding(context);
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    //  Utility.callMobileVerification(activity, context);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void fetchBranchList(int companyId) {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(context, mView,  getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId",String.valueOf(companyId) );

            webServiceHandler.getBranchList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    branchList = new ArrayList<>();
                    branchList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + branchList.size());
                    Ajax tempObj = new Ajax();
                    tempObj.setBranchId();
                    tempObj.setBranchName();
                    branchList.add(0,tempObj);
                    branchDataAdapter = new CompanySpinnerAdapter(context,
                            branchList,2);
                    branchDataAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spBranch.setAdapter(branchDataAdapter);

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
                    //  Utility.callServerNotResponding(context);
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    //  Utility.callMobileVerification(activity, context);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchDepartmentList(int branchId) {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(context, mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("companyId",String.valueOf(companyPosition) );

            webServiceHandler.getDepartmentList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    departmentList = new ArrayList<>();
                    departmentList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + departmentList.size());
                    Ajax tempObj = new Ajax();
                    tempObj.setDeptId();
                    tempObj.setDeptName();
                    departmentList.add(0,tempObj);
                    departmentDataAdapter = new CompanySpinnerAdapter(context,
                            departmentList,3);
                    departmentDataAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDepartment.setAdapter(departmentDataAdapter);

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
                    //  Utility.callServerNotResponding(context);
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    //  Utility.callMobileVerification(activity, context);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                if (ajax.getMorning().equalsIgnoreCase("P") && ajax.getEvening().equalsIgnoreCase("P")) {
                    events.add(new EventDay(mCal, R.drawable.icon_present));
                }
                else if (ajax.getMorning().equalsIgnoreCase("P") && ajax.getEvening().equalsIgnoreCase("A")) {

                    events.add(new EventDay(mCal, R.drawable.icon_pa));
                }
                else if (ajax.getMorning().equalsIgnoreCase("A") && ajax.getEvening().equalsIgnoreCase("P")) {

                    events.add(new EventDay(mCal, R.drawable.icon_ap));
                }
                else if (ajax.getMorning().equalsIgnoreCase("A") && ajax.getEvening().equalsIgnoreCase("A")) {

                    events.add(new EventDay(mCal, R.drawable.icon_absent));
                }
                else if (ajax.getMorning().equalsIgnoreCase("H") && ajax.getEvening().equalsIgnoreCase("H")) {

                    events.add(new EventDay(mCal, R.drawable.icon_holiday));
                }
                else if (ajax.getMorning().equalsIgnoreCase("S") && ajax.getEvening().equalsIgnoreCase("S")) {

                    events.add(new EventDay(mCal, R.drawable.icon_ss));
                }
                else if (ajax.getMorning().equalsIgnoreCase("LI") && ajax.getEvening().equalsIgnoreCase("A")) {

                    events.add(new EventDay(mCal, R.drawable.icon_lia));
                }
                else if (ajax.getMorning().equalsIgnoreCase("A") && ajax.getEvening().equalsIgnoreCase("LI")) {

                    events.add(new EventDay(mCal, R.drawable.icon_ali));
                }
                else if (ajax.getMorning().equalsIgnoreCase("F-H In - EO") && ajax.getEvening().equalsIgnoreCase("A")) {

                    events.add(new EventDay(mCal, R.drawable.fhineoa));
                }
                else if (ajax.getMorning().equalsIgnoreCase("A") && ajax.getEvening().equalsIgnoreCase("F-H In - EO")) {

                    events.add(new EventDay(mCal, R.drawable.afhineo));
                }
                else {
                    events.add(new EventDay(mCal, R.drawable.icon_red_box));
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
            requestMap.put("compId", String.valueOf(companyPosition) );
            requestMap.put("branchId", String.valueOf(branchPosition) );
            requestMap.put("deptId", String.valueOf(departmentPosition));
            requestMap.put("empId", String.valueOf(empPosition));
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

    private void fetchEmpList() {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showToastMessage(getActivity(), getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("companyId",String.valueOf(companyPosition) );
            Log.d("xdd","" + branchPosition + " " + departmentPosition);
            requestMap.put("branch",String.valueOf(branchPosition) );
            requestMap.put("DeptId",String.valueOf(departmentPosition) );

            webServiceHandler.getEmpList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    empList = new ArrayList<>();
                    empList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + empList.size());
                    Ajax tempObj = new Ajax();
                    tempObj.setEmpId();
                    tempObj.setEmpDesc();
                    empList.add(0,tempObj);

                    empSpinnerAdapter = new DataSpinnerAdapter(context,
                            empList);
                    empSpinnerAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spEmp.setAdapter(empSpinnerAdapter);

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
                    //  Utility.callServerNotResponding(context);
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    //  Utility.callMobileVerification(activity, context);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  }

