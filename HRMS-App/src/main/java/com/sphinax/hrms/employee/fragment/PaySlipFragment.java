package com.sphinax.hrms.employee.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.model.PaymentData;
import com.sphinax.hrms.servicehandler.DownloadTask;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.RequestPermissionHandler;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.PaySlipListAdapter;

import java.util.ArrayList;
import java.util.Collections;
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
    private PaymentData paySlipData = new PaymentData();
    private HashMap<Integer, String> spinnerMonthMap = new HashMap<>();
    private String payslipYear;
    private String payslipMonth;
    private LinearLayout ll_payment, ll_deduction;
    private FragmentManager fragmentManager;
    private RequestPermissionHandler mRequestPermissionHandler;

    public PaySlipFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void handleButtonClicked() {
        mRequestPermissionHandler.requestPermission(getActivity(), new String[]{
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                //  Toast.makeText(getActivity(), "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(getActivity(), "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestPermissionHandler.requestPermission(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        }, 124, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                //   Toast.makeText(getActivity(), "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(getActivity(), "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        return inflater.inflate(R.layout.fragment_pay_slip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        mRequestPermissionHandler = new RequestPermissionHandler();
        handleButtonClicked();

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
                if(!tv_toatal.getText().toString().equalsIgnoreCase("")){
                    handleButtonClicked();
                    fetchPaySlipDownload();
                }else{
                    Utility.showCustomToast(context, mView, "Unable to download");

                }

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
               // fetchPaymentDeatail();
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void loadyearSpinner(ArrayList<Ajax> yearList) {
        Collections.reverse(yearList);

        String[] spinnerArray = new String[yearList.size()];
        HashMap<Integer, String> spinnerMap = new HashMap<>();
        for (int i = 0; i < yearList.size(); i++) {
            spinnerMap.put(i, String.valueOf(yearList.get(i).getYear()));
            spinnerArray[i] = String.valueOf(yearList.get(i).getYear());
        }


        @SuppressWarnings("unchecked") ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_year.setAdapter(arrayAdapter);

    }

    private void loadmonthSpinner(ArrayList<Ajax> monthList) {
        Collections.reverse(monthList);
        String[] spinnerArray = new String[monthList.size()];
        spinnerMonthMap = new HashMap<>();
        for (int i = 0; i < monthList.size(); i++) {
            spinnerMonthMap.put(i, monthList.get(i).getMonthValue().toString());
            spinnerArray[i] = monthList.get(i).getMonth();
        }


        @SuppressWarnings("unchecked") ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_month.setAdapter(arrayAdapter);

    }

    private void loadDatainView() {
        tv_payment.setText(String.valueOf(paySlipData.getAjax().getTotal().get(0).getEarnings()));
        tv_deduction.setText(String.valueOf(paySlipData.getAjax().getTotal().get(0).getDeductions()));
        tv_netpay.setText(String.valueOf(paySlipData.getAjax().getTotal().get(0).getNetpay()));
        loadListPaySlip(0);
    }

    private void loadListPaySlip(int i) {

        PaySlipListAdapter paySlipListAdapter;
        if (i == 0) {
            tv_heading.setText("Payments");
            if( paySlipData.getAjax() != null &&  paySlipData.getAjax().getTotal() != null && paySlipData.getAjax().getTotal().get(0).getEarnings() != null) {
                tv_toatal.setText(String.valueOf(paySlipData.getAjax().getTotal().get(0).getEarnings()));
                paySlipListAdapter = new PaySlipListAdapter(getActivity(), paySlipData, 0);
                lv_amt.setAdapter(paySlipListAdapter);
            }
        } else if (i == 1) {
            tv_heading.setText("Deductions");
            if( paySlipData.getAjax() != null &&  paySlipData.getAjax().getTotal() != null && paySlipData.getAjax().getTotal().get(0).getDeductions() != null) {
                tv_toatal.setText(String.valueOf(paySlipData.getAjax().getTotal().get(0).getDeductions()));
                paySlipListAdapter = new PaySlipListAdapter(getActivity(), paySlipData, 1);
                lv_amt.setAdapter(paySlipListAdapter);
            }
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
            HashMap<String, String> requestMap = new HashMap<>();
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
                    ArrayList<Ajax> yearList;
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
            HashMap<String, String> requestMap = new HashMap<>();
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
                    ArrayList<Ajax> yearList;
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
            HashMap<String, String> requestMap = new HashMap<>();
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

                    if (!flag){
                        tv_payment.setText("");
                        tv_deduction.setText("");
                        tv_netpay.setText("");
                        tv_toatal.setText("");
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    if(obj !=null){
                        PaymentData companyData = (PaymentData) obj;

                        paySlipData = companyData;
                        loadDatainView();
                    }else {
                        paySlipData = new PaymentData();
//                        paySlipListAdapter = new PaySlipListAdapter(getActivity(), paySlipData, 0);
//                        lv_amt.setAdapter(paySlipListAdapter);
                        lv_amt.setAdapter(null);
                        tv_payment.setText("");
                        tv_deduction.setText("");
                        tv_netpay.setText("");
                        tv_toatal.setText("");

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

    private void fetchPaySlipDownload() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showToastMessage(context, getResources().getString(R.string.invalidInternetConnection));
            return;
        }

        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("year", payslipYear);
            requestMap.put("month", payslipMonth);
            new DownloadTask(getActivity(), requestMap, String.valueOf(payslipYear + "-" + payslipMonth + ".pdf"));

//            webServiceHandler.getPaySlipDownload(getActivity(), context, requestMap, new ServiceCallback() {
//
//                @Override
//                public void onSuccess(boolean flag) {
//                }
//
//                @Override
//                public void onReturnObject(Object obj) {
//                }
//
//                @Override
//                public void onParseError() {
//                }
//
//                @Override
//                public void onNetworkError() {
//                }
//
//                @Override
//                public void unAuthorized() {
//                }
//
//            });
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
