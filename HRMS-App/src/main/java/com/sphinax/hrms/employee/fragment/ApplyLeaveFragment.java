package com.sphinax.hrms.employee.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.sphinax.hrms.view.LeaveTypeListAdapter;
import com.sphinax.hrms.view.LeaveTypeSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class ApplyLeaveFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "ApplyLeaveFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    String[] leaveSession = {"First Half", "Second Half"};
    private View mView;
    private Button bt_submit, bt_cancel;
    private TextView tv_start_date, tv_end_date, tv_count;
    private Spinner sp_session_start, sp_session_end, sp_leave_type;
    private EditText ed_reason;
    private ProgressDialog pdia;
    private ArrayList<Ajax> leaveTypeList;
    private LeaveTypeSpinnerAdapter leaveTypeSpinnerAdapter;
    private LeaveTypeListAdapter leaveTypeListAdapter;
    private int leaveTypePosition = 0;
    private int startsession = 0;
    private int endsession = 0;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int aYear, aMonth;
    //
    //
    // private ImageView img_leave_application;
    //private ImageView img_leave_management;
    private ScrollView scroll_leaveapplication;
    private RecyclerView lv_leave_type;
    private String leaveReason;
    private FragmentManager fragmentManager;

    public ApplyLeaveFragment() {
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
        return inflater.inflate(R.layout.fragment_apply_leave, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();
        setListeners();

        lv_leave_type.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        lv_leave_type.setLayoutManager(layoutManager);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, leaveSession);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_session_start.setAdapter(arrayAdapter);
        sp_session_end.setAdapter(arrayAdapter);

        fetchLeaveList();
    }

    private void loadComponent() {
        bt_submit = mView.findViewById(R.id.bt_submit);
        bt_cancel = mView.findViewById(R.id.bt_cancel);
        tv_count = mView.findViewById(R.id.tv_count);
        tv_start_date = mView.findViewById(R.id.tv_start_date);
        tv_end_date = mView.findViewById(R.id.tv_end_date);
        sp_session_start = mView.findViewById(R.id.sp_leave_session_start);
        sp_session_end = mView.findViewById(R.id.sp_leave_session_end);
        sp_leave_type = mView.findViewById(R.id.sp_leave_type);
        lv_leave_type = mView.findViewById(R.id.lv_leave_data);
        //img_leave_application = mView.findViewById(R.id.img_leave_application);
        //img_leave_management = mView.findViewById(R.id.img_leave_management);
        scroll_leaveapplication = mView.findViewById(R.id.scroll_leave_Applicatoion);
        ed_reason = mView.findViewById(R.id.ed_reason);
    }

    private void setListeners() {
        sp_leave_type.setOnItemSelectedListener(this);
        sp_session_start.setOnItemSelectedListener(this);
        sp_session_end.setOnItemSelectedListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);


       // img_leave_application.setOnClickListener(this);
        //img_leave_management.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_date:
                getDate("start");
                break;

            case R.id.tv_end_date:
                getDate("end");
                break;
//            case R.id.img_leave_application:
//                if (scroll_leaveapplication.getVisibility() == View.VISIBLE) {
//                    scroll_leaveapplication.setVisibility(View.GONE);
//                } else {
//                    scroll_leaveapplication.setVisibility(View.VISIBLE);
//                }
//                break;
//            case R.id.img_leave_management:
//
//                if (lv_leave_type.getVisibility() == View.VISIBLE) {
//                    lv_leave_type.setVisibility(View.GONE);
//                } else {
//                    lv_leave_type.setVisibility(View.VISIBLE);
//                }
//                break;

            case R.id.bt_submit:
                checkAllDataEnter();
                break;
            case R.id.bt_cancel:
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_leave_type:
                actionCompanySelector(position);
                break;
            case R.id.sp_leave_session_start:
                startsession = position + 1;
                datediffernet();
                break;
            case R.id.sp_leave_session_end:
                endsession = position + 1;
                datediffernet();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getDate(final String txtValue) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        if (txtValue.equalsIgnoreCase("start")) {
                            tv_start_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            aYear = year;
                            aMonth = monthOfYear + 1;
                            datediffernet();
                        } else if (txtValue.equalsIgnoreCase("end")) {
                            tv_end_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            datediffernet();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void datediffernet() {


        String dateStart = tv_start_date.getText().toString();
        String dateStop = tv_end_date.getText().toString();
        if (dateStart != null && !dateStart.equalsIgnoreCase("") && dateStop != null && !dateStop.equalsIgnoreCase("")) {
            //HH converts hour in 24 hours format (0-23), day calculation
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date d1 = null;
            Date d2 = null;

            try {
                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);
                long diff = d2.getTime() - d1.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);
                Long l = new Long(diffDays);
                double dateValue = l.doubleValue();
                Log.d(TAG, " days, " + diffDays);
                dateValue = dateValue + 1;
                if (startsession == 1) {
                    dateValue = dateValue - .5;
                }
                if (endsession == 0) {
                    dateValue = dateValue - .5;
                }
                tv_count.setText(String.valueOf(dateValue));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Its works on LeaveType Spinner Click Action
     **/
    private void actionCompanySelector(int position) {
        Ajax leavetype = new Ajax();
        if (leaveTypeList != null) {
            leavetype = leaveTypeList.get(position);
            leaveTypePosition = leavetype.getLeaveTypeIds();
            Log.d(TAG, "actionCompanySelector: " +leaveTypePosition);
           // leaveTypePosition = position;
        }
    }

    private void checkAllDataEnter() {

        if (tv_count.getText().toString().length() > 0) {
            if (ed_reason.getText().toString() != null && !ed_reason.getText().toString().equalsIgnoreCase("")) {
                leaveReason = ed_reason.getText().toString();
                applyLeave();
            } else {
                Utility.showCustomToast(context, mView, "Kindly Enter the Leave Reason");
            }
        } else {
            Utility.showCustomToast(context, mView, "Kindly Select the Start Date and End Date");
        }



    }


    private void fetchLeaveList() {
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
            requestMap.put("empId", Global.getLoginInfoData().getUserId().toString());

            webServiceHandler.getLeaveTypeList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    leaveTypeList = new ArrayList<>();
                    leaveTypeList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + leaveTypeList.size());

                    leaveTypeSpinnerAdapter = new LeaveTypeSpinnerAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item, leaveTypeList);
                    leaveTypeSpinnerAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_leave_type.setAdapter(leaveTypeSpinnerAdapter);

                    leaveTypeListAdapter = new LeaveTypeListAdapter(getActivity(), leaveTypeList);
                    lv_leave_type.setAdapter(leaveTypeListAdapter);

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


    private void applyLeave() {
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.showCustomToast(getActivity(), mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(getActivity());
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empID", Global.getLoginInfoData().getUserId());
            requestMap.put("toWhom", Global.getLoginInfoData().getReportsTo());
            requestMap.put("leaveType", String.valueOf(leaveTypePosition));
            requestMap.put("reaSon", leaveReason);
            requestMap.put("Fromdate", tv_start_date.getText().toString());
            requestMap.put("FromSession", String.valueOf(startsession));
            requestMap.put("Todate", tv_end_date.getText().toString());
            requestMap.put("ToSession", String.valueOf(endsession));
            requestMap.put("appliedDays", tv_count.getText().toString());
            requestMap.put("leaveMonth", String.valueOf(aMonth));
            requestMap.put("leaveYear", String.valueOf(aYear));

            webServiceHandler.applyLeaveRequest(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag) {
                        Utility.showCustomToast(getActivity(),mView, "Leave Applied Successfully");
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        Utility.addFragment(getActivity(), R.id.content_frame, fragmentManager, new ApplyLeaveFragment(), false, null, Constants.FRAMENT_LEAVE_APPLY);
                    } else {
                        Utility.showCustomToast(getActivity(), mView,"Leave not applied kindly try again");

                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
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
            //Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }
    }

}
