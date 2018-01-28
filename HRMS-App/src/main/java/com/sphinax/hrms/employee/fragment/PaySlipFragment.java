package com.sphinax.hrms.employee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.sphinax.hrms.view.PaySlipListAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class PaySlipFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "PaySlipFragment-";
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private View mView;
    private Spinner sp_year, sp_month;
    private Button bt_display, bt_download;
    private TextView tv_payment, tv_netpay, tv_deduction, tv_toatal, tv_heading;
    private ListView lv_amt;
    private ProgressDialog pdia;
    private CompanyData paySlipData = new CompanyData();
    private HashMap<Integer, String> spinnerMonthMap = new HashMap<Integer, String>();
    private String payslipYear;
    private String payslipMonth;
    private PaySlipListAdapter paySlipListAdapter;
    private LinearLayout ll_payment, ll_deduction;


    public PaySlipFragment() {
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
        return inflater.inflate(R.layout.fragment_pay_slip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        loadComponent();
        setListeners();
        fetchYearList();
    }

    private void loadComponent() {
        bt_display = mView.findViewById(R.id.bt_display);
        bt_download = mView.findViewById(R.id.bt_download_payslip);
        tv_heading = mView.findViewById(R.id.tv_heading);
        tv_payment = mView.findViewById(R.id.tv_payment_amt);
        tv_deduction = mView.findViewById(R.id.tv_deduction_amt);
        tv_netpay = mView.findViewById(R.id.tv_netpay);
        tv_toatal = mView.findViewById(R.id.tv_total_amt);
        sp_year = mView.findViewById(R.id.sp_year);
        sp_month = mView.findViewById(R.id.sp_month);
        lv_amt = mView.findViewById(R.id.lv_detail_amt);
        ll_payment = mView.findViewById(R.id.ll_payment);
        ll_deduction = mView.findViewById(R.id.ll_deduction);
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
                Log.d(TAG, payslipYear);
                fetchMonthList();
                break;
            case R.id.sp_month:
                String monthValue = sp_month.getSelectedItem().toString();
                Log.d(TAG, monthValue);
                payslipMonth = spinnerMonthMap.get(position);
                Log.d(TAG, " " + payslipMonth);
                fetchPaymentDeatail();
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void loadyearSpinner(ArrayList<Ajax> yearList) {

        String[] spinnerArray = new String[yearList.size()];
        HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
        for (int i = 0; i < yearList.size(); i++) {
            spinnerMap.put(i, String.valueOf(yearList.get(i).getYear()));
            spinnerArray[i] = String.valueOf(yearList.get(i).getYear());
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_year.setAdapter(arrayAdapter);

    }

    private void loadmonthSpinner(ArrayList<Ajax> monthList) {

        String[] spinnerArray = new String[monthList.size()];
        spinnerMonthMap = new HashMap<Integer, String>();
        for (int i = 0; i < monthList.size(); i++) {
            spinnerMonthMap.put(i, monthList.get(i).getMonthValue().toString());
            spinnerArray[i] = monthList.get(i).getMonth();
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_month.setAdapter(arrayAdapter);

    }

    private void loadDatainView() {
        tv_payment.setText(String.valueOf(paySlipData.getAjax().get(0).getEarnings()));
        tv_deduction.setText(String.valueOf(paySlipData.getAjax().get(0).getDeductions()));
        tv_netpay.setText(String.valueOf(paySlipData.getAjax().get(0).getNetpay()));
        loadListPaySlip(0);
    }

    private void loadListPaySlip(int i) {

        if (i == 0) {
            tv_heading.setText("Payments");
            tv_toatal.setText(String.valueOf(paySlipData.getAjax().get(0).getEarnings()));
            paySlipListAdapter = new PaySlipListAdapter(getActivity(), paySlipData, 0);
            lv_amt.setAdapter(paySlipListAdapter);
        } else if (i == 1) {
            tv_heading.setText("Deductions");
            tv_toatal.setText(String.valueOf(paySlipData.getAjax().get(0).getDeductions()));
            paySlipListAdapter = new PaySlipListAdapter(getActivity(), paySlipData, 1);
            lv_amt.setAdapter(paySlipListAdapter);
        }

    }

    private void fetchYearList() {
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
                    ArrayList<Ajax> yearList = new ArrayList<>();
                    yearList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + yearList.size());

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

    private void fetchMonthList() {
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
            requestMap.put("year", payslipYear);

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
                    ArrayList<Ajax> yearList = new ArrayList<>();
                    yearList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + yearList.size());


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


    private void fetchPaymentDeatail() {
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
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("year", payslipYear);
            requestMap.put("month", payslipMonth);

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
                    paySlipData = companyData;
                    loadDatainView();
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

    private void fetchPaySlipDownload() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showToastMessage(context, getResources().getString(R.string.invalidInternetConnection));
            return;
        }

        try {
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("year", payslipYear);
            requestMap.put("month", payslipMonth);

            webServiceHandler.getPaySlipDownload(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {
                }

                @Override
                public void onReturnObject(Object obj) {
                }

                @Override
                public void onParseError() {
                }

                @Override
                public void onNetworkError() {
                }

                @Override
                public void unAuthorized() {
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
