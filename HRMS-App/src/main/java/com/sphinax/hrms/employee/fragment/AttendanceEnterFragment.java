package com.sphinax.hrms.employee.fragment;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;

import com.sphinax.hrms.R;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AttendanceEnterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttendanceEnterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceEnterFragment extends Fragment {
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
    private Button bt_In_att,bt_out_att;
    private TextView tv_att_details;
    private ProgressDialog pdia;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> ajaxList;

    public AttendanceEnterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttendanceEnterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendanceEnterFragment newInstance(String param1, String param2) {
        AttendanceEnterFragment fragment = new AttendanceEnterFragment();
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
        return inflater.inflate(R.layout.fragment_attendance_enter, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        bt_In_att = (Button) mView.findViewById(R.id.bt_mark_in_att);
        bt_out_att = (Button) mView.findViewById(R.id.bt_mark_out_att);
        tv_att_details  = (TextView) mView.findViewById(R.id.tv_att_details);





        fetchDailyUserAttendance();

        if(Global.isMarkAttendance()){
            bt_In_att.setEnabled(false);
            bt_In_att.setClickable(false);

        }
        bt_In_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUserAttendance();
            }
        });

       // Check-In Time 9:00PM

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


    /***Method used to get Company List from the OPS Service **/

    private void fetchDailyUserAttendance() {
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
            Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            String datetime = dateformat.format(c.getTime());
            Log.d("mnb", "onViewCreated: " + datetime);
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("refId", "5019");
            requestMap.put("InDate", datetime);

            webServiceHandler.getDailyUserAttendance((Activity) context, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag){
                        //enterUserAttendance();
                        Global.setMarkAttendance(flag);
                        bt_In_att.setEnabled(false);
                        bt_In_att.setClickable(false);
                    }else {
                        Global.setMarkAttendance(false);
                        bt_In_att.setText("Check-In Time");
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    CompanyData companyData = (CompanyData) obj;
                    ajaxList = new ArrayList<>();
                    ajaxList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + ajaxList.size());

                    checkAttendanceMarked();



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


    private void enterUserAttendance() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showToastMessage(context, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        final String datetime;
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
             datetime = dateformat.format(c.getTime());
            Log.d("mnb", "onViewCreated: " + datetime);
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("empId",Utility.getPreference(getActivity()).getString(Constants.PREFS_USER_ID, "") );
            requestMap.put("refId", "5019");
            requestMap.put("locationId", "Chennai");
            requestMap.put("empimageDesc", "");
            requestMap.put("clkInTime", datetime);

            webServiceHandler.markDailyUserAttendance((Activity) context, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (!flag){
                        bt_In_att.setText("Check-In Time");
                        tv_att_details.setText("");
                    }else if(flag){
                        //fetchDailyUserAttendance();
                        bt_In_att.setText("Check-In Time \n "+datetime);
                        bt_In_att.setEnabled(false);
                        bt_In_att.setClickable(false);
                        Global.setMarkAttendance(flag);

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




        private void checkAttendanceMarked(){

        if(ajaxList != null){
            if (ajaxList.get(0).getTime()!=null && !ajaxList.get(0).getTime().equalsIgnoreCase("")){
                bt_In_att.setText(ajaxList.get(0).getTime());
                tv_att_details.setText(ajaxList.get(0).getLocation());
            }
        }



        }

}
