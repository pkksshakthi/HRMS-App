package com.sphinax.hrms.employee.fragment;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.sphinax.hrms.view.LeaveTypeListAdapter;
import com.sphinax.hrms.view.LeaveTypeSpinnerAdapter;
import com.sphinax.hrms.view.PaySlipListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaySlipFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaySlipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaySlipFragment extends Fragment  implements AdapterView.OnItemSelectedListener, View.OnClickListener{
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
    private Spinner sp_year,sp_month;
    private Button bt_display,bt_download;
    private TextView tv_payment,tv_netpay,tv_deduction,tv_toatal,tv_heading;
    ListView lv_amt;
    private ProgressDialog pdia;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> ajaxPaySlipList;
    private ArrayList<Ajax> ajaxEarningList;
    private ArrayList<Ajax> ajaxDeductionList;
    private HashMap<Integer,String>  spinnerMonthMap  = new HashMap<Integer, String>();
    private String payslipYear;
    private String payslipMonth;
    private PaySlipListAdapter paySlipListAdapter;
    private LinearLayout ll_payment,ll_deduction;




    public PaySlipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaySlipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaySlipFragment newInstance(String param1, String param2) {
        PaySlipFragment fragment = new PaySlipFragment();
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
        return inflater.inflate(R.layout.fragment_pay_slip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        bt_display = (Button) mView.findViewById(R.id.bt_display);
        bt_download = (Button) mView.findViewById(R.id.bt_download_payslip);
        tv_heading= (TextView) mView.findViewById(R.id.tv_heading);
        tv_payment= (TextView) mView.findViewById(R.id.tv_payment_amt);
       tv_deduction  = (TextView) mView.findViewById(R.id.tv_deduction_amt);
      tv_netpay    = (TextView) mView.findViewById(R.id.tv_netpay);
      tv_toatal   = (TextView) mView.findViewById(R.id.tv_total_amt);
        sp_year  = (Spinner) mView.findViewById(R.id.sp_year);
        sp_month = (Spinner) mView.findViewById(R.id.sp_month);
       lv_amt  = (ListView) mView.findViewById(R.id.lv_detail_amt);
        ll_payment = (LinearLayout)mView.findViewById(R.id.ll_payment);
        ll_deduction = (LinearLayout)mView.findViewById(R.id.ll_deduction);
        setListeners();
        fetchYearList();
    }

    private void setListeners() {
        sp_month.setOnItemSelectedListener(this);
        sp_year.setOnItemSelectedListener(this);
        bt_display.setOnClickListener(this);
        ll_deduction.setOnClickListener(this);
        ll_payment.setOnClickListener(this);
        tv_payment.setOnClickListener(this);
        tv_deduction.setOnClickListener(this);
        bt_download.setOnClickListener(this);

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
            case R.id.bt_display:
                fetchPaymentDeatail();
                break;

            case R.id.bt_download_payslip:
                fetchPaySlipDownload();
                break;

            case R.id.ll_payment:
                loadListPaySlip(0);
                break;

            case R.id.ll_deduction:
                loadListPaySlip(1);
                break;
            case R.id.tv_payment_amt:
                loadListPaySlip(0);
                break;

            case R.id.tv_deduction_amt:
                loadListPaySlip(1);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_year:
                payslipYear = sp_year.getSelectedItem().toString();
                Log.d("Year" ,payslipYear );
                fetchMonthList();

                break;
            case R.id.sp_month:
                //month = position;
                String monthValue = sp_month.getSelectedItem().toString();
                Log.d("month" ,monthValue );
                payslipMonth = spinnerMonthMap.get(position);
                Log.d("month" , " " + payslipMonth  );
                fetchPaymentDeatail();

                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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


    private void fetchYearList() {
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
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );

            webServiceHandler.getYearList(getActivity(), context, requestMap, new ServiceCallback() {

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
                     ArrayList<Ajax>  yearList = new ArrayList<>();
                    yearList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + yearList.size());


                    loadyearSpinner(yearList);
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

private void loadyearSpinner(ArrayList<Ajax>  yearList){

    String[] spinnerArray = new String[yearList.size()];
    HashMap<Integer,String>  spinnerMap = new HashMap<Integer, String>();
    for (int i = 0; i < yearList.size(); i++)
    {
        spinnerMap.put(i,String.valueOf(yearList.get(i).getYear()));
        spinnerArray[i] = String.valueOf(yearList.get(i).getYear());
    }


    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spinnerArray);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    //Setting the ArrayAdapter data on the Spinner
    sp_year.setAdapter(arrayAdapter);

}


    private void fetchMonthList() {
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
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("year",payslipYear );

            webServiceHandler.getMonthList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    ArrayList<Ajax>  yearList = new ArrayList<>();
                    yearList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + yearList.size());


                    loadmonthSpinner(yearList);
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


    private void loadmonthSpinner(ArrayList<Ajax>  monthList){

        String[] spinnerArray = new String[monthList.size()];
       spinnerMonthMap = new HashMap<Integer, String>();
        for (int i = 0; i < monthList.size(); i++)
        {
            spinnerMonthMap.put(i,monthList.get(i).getMonthValue().toString());
            spinnerArray[i] = monthList.get(i).getMonth();
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_month.setAdapter(arrayAdapter);

    }

    private void fetchPaymentDeatail() {
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
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("empId", Global.getLoginInfoData().getUserId() );
            requestMap.put("year",payslipYear );
            requestMap.put("month",payslipMonth);

            webServiceHandler.getDisplayPaySlip(getActivity(), context, requestMap, new ServiceCallback() {

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
                   // ajaxPaySlipList = new ArrayList<>();
                    ajaxPaySlipList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size aaa --> " + ajaxPaySlipList.size());
                    Log.d("ajaxList", "earning --> " + ajaxPaySlipList.get(0).getEarnings());
                    tv_payment.setText(String.valueOf(ajaxPaySlipList.get(0).getEarnings()));
                    tv_deduction.setText(String.valueOf(ajaxPaySlipList.get(0).getDeductions()));
                    tv_netpay.setText(String.valueOf(ajaxPaySlipList.get(0).getNetpay()));

                    fetchEarningDeatail();

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

    private void fetchEarningDeatail() {
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
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("empId",Global.getLoginInfoData().getUserId() );
            requestMap.put("year",payslipYear );
            requestMap.put("month",payslipMonth);

            webServiceHandler.getPaySlipEarnings(getActivity(), context, requestMap, new ServiceCallback() {

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
                    ajaxEarningList= new ArrayList<>();
                    ajaxEarningList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + ajaxEarningList.size());


                    fetchDeductionDetail();
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


    private void fetchDeductionDetail() {
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
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("empId",Global.getLoginInfoData().getUserId() );
            requestMap.put("year",payslipYear );
            requestMap.put("month",payslipMonth);

            webServiceHandler.getPaySlipDeductions(getActivity(), context, requestMap, new ServiceCallback() {

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
                    ajaxDeductionList= new ArrayList<>();
                    ajaxDeductionList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + ajaxDeductionList.size());
                    loadListPaySlip(0);



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



private void loadListPaySlip(int i){

        if(i == 0){
            tv_heading.setText("Payments");
            tv_toatal.setText(String.valueOf(ajaxPaySlipList.get(0).getEarnings()));
            paySlipListAdapter = new PaySlipListAdapter(getActivity(), ajaxEarningList,0);
            lv_amt.setAdapter(paySlipListAdapter);
        }else if(i==1){
            tv_heading.setText("Deductions");
            tv_toatal.setText(String.valueOf(ajaxPaySlipList.get(0).getDeductions()));
            paySlipListAdapter = new PaySlipListAdapter(getActivity(), ajaxDeductionList,1);
            lv_amt.setAdapter(paySlipListAdapter);
        }

}

    private void fetchPaySlipDownload() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showToastMessage(context, getResources().getString(R.string.invalidInternetConnection));
            return;
        }

        try {
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("empId",Global.getLoginInfoData().getUserId() );
            requestMap.put("year",payslipYear );
            requestMap.put("month",payslipMonth);

            webServiceHandler.getPaySlipDownload(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {  }

                @Override
                public void onReturnObject(Object obj) {}

                @Override
                public void onParseError() { }

                @Override
                public void onNetworkError() {}

                @Override
                public void unAuthorized() { }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
