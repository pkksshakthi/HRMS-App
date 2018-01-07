package com.sphinax.hrms.employee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.LeaveTypeSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
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
    private ImageView iv_user_image;
    private TextView tv_username,tv_empid,tv_company_name,tv_branch,tv_department,tv_designation,tv_mobile_no,tv_email_id,tv_address;
    private ProgressDialog pdia;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> userInfoList;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        tv_username = (TextView) mView.findViewById(R.id.tv_username);
        tv_empid = (TextView) mView.findViewById(R.id.tv_empid);
        tv_company_name = (TextView) mView.findViewById(R.id.tv_company_name);
        tv_branch = (TextView) mView.findViewById(R.id.tv_branch);
        tv_department = (TextView) mView.findViewById(R.id.tv_department);
        tv_designation = (TextView) mView.findViewById(R.id.tv_designation);
        tv_mobile_no = (TextView) mView.findViewById(R.id.tv_mobile_no);
        tv_email_id = (TextView) mView.findViewById(R.id.tv_email_id);
        tv_address = (TextView) mView.findViewById(R.id.tv_address);
        iv_user_image = (ImageView) mView.findViewById(R.id.iv_user_image);

        fetchUserInfo();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

    private void fetchUserInfo() {
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
            requestMap.put("empId",Utility.getPreference(getActivity()).getString(Constants.PREFS_USER_ID, "") );

            webServiceHandler.getUserInfo(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    Log.d("ka" , " "+ flag);
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    CompanyData companyData = (CompanyData) obj;
                    userInfoList = new ArrayList<>();
                    userInfoList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + userInfoList.size());
                   setUserInfoData();


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

    private void setUserInfoData(){
        if(userInfoList != null){
            if (userInfoList.get(0).getEmpName()!=null ){
                tv_username.setText(userInfoList.get(0).getEmpName());
            }
            if (userInfoList.get(0).getEmpId()!=null ){
                tv_empid.setText(userInfoList.get(0).getEmpId());
            }
            if (userInfoList.get(0).getCompName()!=null){
                tv_company_name.setText(userInfoList.get(0).getCompName());
            }
            if (userInfoList.get(0).getBranchName()!=null ){
                tv_branch.setText(userInfoList.get(0).getBranchName());
            }
            if (userInfoList.get(0).getEmpDept()!=null ){
                tv_department.setText(userInfoList.get(0).getEmpDept());
            }
            if (userInfoList.get(0).getEmpDesign()!=null ){
                tv_designation.setText(userInfoList.get(0).getEmpDesign());
            }
            if (userInfoList.get(0).getEmpMobile()!=null ){
                tv_mobile_no.setText(String.valueOf(userInfoList.get(0).getEmpMobile()));
            }
//            if (userInfoList.get(0).get!=null){
//                tv_email_id.setText(userInfoList.get(0).get);
//            }
//            if (userInfoList.get(0).get!=null ){
//                tv_address.setText(userInfoList.get(0).get);
//            }
        }

}
}
