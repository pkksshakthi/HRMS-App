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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.LeaveTypeListAdapter;
import com.sphinax.hrms.view.LeaveTypeSpinnerAdapter;
import com.sphinax.hrms.view.QueryListAdapter;
import com.sphinax.hrms.view.QuerySpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnterHRHelpdeskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnterHRHelpdeskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterHRHelpdeskFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {
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
    private Spinner sp_query_type;
    private EditText ed_query;
    private ListView lv_query;
    private Button bt_submit;
    private  int queryTypePosition;
    private ProgressDialog pdia;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> queryTypeList;
    private ArrayList<Ajax> queryList;
    private QuerySpinnerAdapter querySpinnerAdapter;
    private QueryListAdapter queryListAdapter;


    public EnterHRHelpdeskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnterHRHelpdeskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnterHRHelpdeskFragment newInstance(String param1, String param2) {
        EnterHRHelpdeskFragment fragment = new EnterHRHelpdeskFragment();
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
        return inflater.inflate(R.layout.fragment_enter_hrhelpdesk, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        sp_query_type = (Spinner) mView.findViewById(R.id.sp_hr_query_type);
        ed_query = (EditText) mView.findViewById(R.id.ed_query);
        bt_submit = (Button) mView.findViewById(R.id.bt_submit);
        lv_query = (ListView) mView.findViewById(R.id.lv_hr_help_desk_data);



        setListeners();
        fetchQueryTypeList();

    }

    private void setListeners() {
        sp_query_type.setOnItemSelectedListener(this);
        bt_submit.setOnClickListener(this);
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
            case R.id.bt_submit:
               // getDate("start");

                if(ed_query.getText()!=null && !ed_query.getText().toString().equalsIgnoreCase("") ){
                    saveQuery();
                }else {
                    Utility.showToastMessage(context," Please Enter Your Query ");

                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_hr_query_type:
                actionTypeSelector(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /**
     * Its works on Company Spinner Click Action
     **/
    private void actionTypeSelector(int position) {
        Ajax ajax = new Ajax();
        if (queryTypeList != null) {
            ajax = queryTypeList.get(position);
            queryTypePosition = ajax.getReqStatusId();
        }




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

    private void fetchQueryTypeList() {
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

            webServiceHandler.getQueryTypeList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    queryTypeList = new ArrayList<>();
                    queryTypeList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + queryTypeList.size());

                    querySpinnerAdapter = new QuerySpinnerAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item, queryTypeList);
                    querySpinnerAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_query_type.setAdapter(querySpinnerAdapter);

                    fetchQueryList();

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

    private void fetchQueryList() {
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
          //  requestMap.put("empId",Utility.getPreference(getActivity()).getString(Constants.PREFS_USER_ID, "") );
            requestMap.put("empId","10002" );
            requestMap.put("reqSeqId","20");

            webServiceHandler.getEMPQueryList(getActivity(), context, requestMap, new ServiceCallback() {

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
                    queryList = new ArrayList<>();
                    queryList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + queryList.size());


                    queryListAdapter = new QueryListAdapter(getActivity(), queryList);
                    lv_query.setAdapter(queryListAdapter);

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


    private void saveQuery() {
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
            requestMap.put("companyId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("empId",Utility.getPreference(getActivity()).getString(Constants.PREFS_USER_ID, "") );
            requestMap.put("requestTo","10000");
            requestMap.put("requestType",String.valueOf(queryTypePosition) );
            requestMap.put("description", ed_query.getText().toString());

            webServiceHandler.saveUserQuery(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    if(flag){
                        Utility.showToastMessage(context, "Query successfully saved");
                        fetchQueryList();
                        querySpinnerAdapter = new QuerySpinnerAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, android.R.layout.simple_spinner_dropdown_item, queryTypeList);
                        querySpinnerAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_query_type.setAdapter(querySpinnerAdapter);

                        ed_query.setText("");
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
