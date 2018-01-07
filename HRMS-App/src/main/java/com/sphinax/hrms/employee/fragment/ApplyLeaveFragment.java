package com.sphinax.hrms.employee.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.CompanySpinnerAdapter;
import com.sphinax.hrms.view.LeaveTypeListAdapter;
import com.sphinax.hrms.view.LeaveTypeSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplyLeaveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplyLeaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyLeaveFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static Context context;
    private View mView;
    private Button bt_submit;
    private TextView tv_start_date,tv_end_date,tv_count;
    private Spinner  sp_session_start,sp_session_end,sp_leave_type;
    private ListView lv_leave_type;
    private ProgressDialog pdia;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> leaveTypeList;
    private LeaveTypeSpinnerAdapter leaveTypeSpinnerAdapter;
    private LeaveTypeListAdapter leaveTypeListAdapter;
    private int leaveTypePosition = 0;
    private int startsession = 0;
    private int endsession =0;
    String[] leaveSession = { "First Half", "Second Half" };
    private int mYear, mMonth, mDay, mHour, mMinute;


    public ApplyLeaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyLeaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyLeaveFragment newInstance(String param1, String param2) {
        ApplyLeaveFragment fragment = new ApplyLeaveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        bt_submit = (Button) mView.findViewById(R.id.bt_submit);
        tv_count  = (TextView) mView.findViewById(R.id.tv_count);
        tv_start_date= (TextView) mView.findViewById(R.id.tv_start_date);
        tv_end_date= (TextView) mView.findViewById(R.id.tv_end_date);
        sp_session_start   = (Spinner) mView.findViewById(R.id.sp_leave_session_start);
        sp_session_end  = (Spinner) mView.findViewById(R.id.sp_leave_session_end);
        sp_leave_type    = (Spinner) mView.findViewById(R.id.sp_leave_type);
        lv_leave_type   = (ListView) mView.findViewById(R.id.lv_leave_data);



        setListeners();



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,leaveSession);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_session_start.setAdapter(arrayAdapter);
        sp_session_end.setAdapter(arrayAdapter);

        fetchCompanyList();
    }


    private void setListeners() {
        sp_leave_type.setOnItemSelectedListener(this);
        sp_session_start.setOnItemSelectedListener(this);
        sp_session_end.setOnItemSelectedListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);

        //btNext.setOnClickListener(this);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_leave_type:
                actionCompanySelector(position);
                break;
            case R.id.sp_leave_session_start:
                startsession = position;
                break;
            case R.id.sp_leave_session_end:
                endsession = position;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getDate(final String txtValue){

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        if(txtValue.equalsIgnoreCase("start")){
                            tv_start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datediffernet();
                        }else if(txtValue.equalsIgnoreCase("end")){
                            tv_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datediffernet();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void datediffernet(){


        String dateStart = tv_start_date.getText().toString();
        String dateStop = tv_end_date.getText().toString();
if(dateStart != null && !dateStart.equalsIgnoreCase("") && dateStop != null && !dateStop.equalsIgnoreCase("")) {
    //HH converts hour in 24 hours format (0-23), day calculation
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    Date d1 = null;
    Date d2 = null;

    try {
        d1 = format.parse(dateStart);
        d2 = format.parse(dateStop);

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

//            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        Long l = new Long(diffDays);
        double dateValue = l.doubleValue();
        Log.d("Data", " days, " + diffDays);

        //int dateValue= Integer.parseInt(String.valueOf(diffDays));
        dateValue = dateValue + 1 ;
        if(startsession == 1){
            dateValue = dateValue - .5;

        }
        if(endsession == 0){
            dateValue = dateValue - .5;
        }




        tv_count.setText(String.valueOf(dateValue));


        System.out.print(diffDays + " days, ");
//            System.out.print(diffHours + " hours, ");
//            System.out.print(diffMinutes + " minutes, ");
//            System.out.print(diffSeconds + " seconds.");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    }
    /**
     * Its works on Company Spinner Click Action
     **/
    private void actionCompanySelector(int position) {
        leaveTypePosition = position;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void fetchCompanyList() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showToastMessage(context, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            HashMap<String, String> requestMap = new HashMap<String, String>();

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
                    leaveTypeList = new ArrayList<>();
                    leaveTypeList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + leaveTypeList.size());

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
