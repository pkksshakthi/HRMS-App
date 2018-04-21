package com.sphinax.hrms.admin.fragment;

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

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnnouncementCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnnouncementCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnouncementCreateFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener  {
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
    private ProgressDialog pdia;
    private ArrayList<Ajax> companyList;
    private ArrayList<Ajax> branchList;
    private ArrayList<Ajax> departmentList;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private Spinner spCompany,spBranch,spDepartment;
    private Button btSubmit;
    private TextView btDate;
    private EditText ed_mess,ed_title;
    private CompanySpinnerAdapter companyDataAdapter;
    private CompanySpinnerAdapter branchDataAdapter;
    private CompanySpinnerAdapter departmentDataAdapter;
    private int companyPosition = 0;
    private int branchPosition = 0;
    private int departmentPosition = 0;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dater;
    private FragmentManager fragmentManager;
    private String whatToDO = "";
    private AdminAjax adminAjax;
    private boolean loadOncecomp = false;
    private boolean loadOncebran = false;
    private boolean loadOncedep = false;

    public AnnouncementCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnnouncementCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnouncementCreateFragment newInstance(String param1, String param2) {
        AnnouncementCreateFragment fragment = new AnnouncementCreateFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_announcement_create, container, false);
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
         myCalendar = Calendar.getInstance();
        dater = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatedateLabel();
        };


     if(getArguments() != null) {
         if (getArguments().getSerializable("whatToDO") != null) {
             whatToDO = (String) getArguments().getSerializable("whatToDO");
             btSubmit.setText(whatToDO);
             loadOncecomp = true;
             loadOncebran = true;
             loadOncedep = true;
             if (whatToDO.equalsIgnoreCase("Update") || whatToDO.equalsIgnoreCase("Delete") ) {
                 adminAjax = (AdminAjax) getArguments().getSerializable("UserValidateObject");
                 ed_mess.setText(adminAjax.getActivityDesc());
                 btDate.setText(adminAjax.getActivityDate());
                 ed_title.setText(adminAjax.getAnnouncementList());
             }

             if ( whatToDO.equalsIgnoreCase("Delete") ) {

                 ed_mess.setEnabled(false);
                 ed_title.setEnabled(false);
                 btDate.setEnabled(false);
                 spCompany.setEnabled(false);
                 spBranch.setEnabled(false);
                 spDepartment.setEnabled(false);


             }


         }

     }



    }

    private void loadComponent() {
        spCompany = mView.findViewById(R.id.sp_company);
        spBranch = mView.findViewById(R.id.sp_branch);
        spDepartment = mView.findViewById(R.id.sp_department);
        btDate = mView.findViewById(R.id.bt_date_picker);
        ed_mess = mView.findViewById(R.id.ed_message_box);
        ed_title = mView.findViewById(R.id.ed_title);
        btSubmit = mView.findViewById(R.id.bt_submit);

    }
    private void setListeners() {
        spCompany.setOnItemSelectedListener(this);
        spBranch.setOnItemSelectedListener(this);
        spDepartment.setOnItemSelectedListener(this);
        btSubmit.setOnClickListener(this);
        btDate.setOnClickListener(this);
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

        if((whatToDO.equalsIgnoreCase("Update") || whatToDO.equalsIgnoreCase("Delete") ) && loadOncecomp){
          //  spCompany.setSelection(companyDataAdapter.getItemIndexById(adminAjax.getCompId(),"C"));
           // fetchBranchList(adminAjax.getCompId());
            loadOncecomp = false;
            fetchBranchList(ajax.getCompId());

        }else{
            fetchBranchList(ajax.getCompId());
        }

    }

    private void actionBranchSelector(int position) {
       // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (branchList != null) {
            ajax = branchList.get(position);
        }
        branchPosition = ajax.getBranchId();
        if((whatToDO.equalsIgnoreCase("Update") || whatToDO.equalsIgnoreCase("Delete") ) && loadOncebran){
            loadOncebran = false;
           // spBranch.setSelection(branchDataAdapter.getItemIndexById(adminAjax.getBranchId(),"B"));
            //fetchDepartmentList(adminAjax.getBranchId());
        }else{
            fetchDepartmentList(ajax.getBranchId());
        }
    }

    private void actionDepartmentSelector(int position) {
        // spinnerPosition = position;
        Ajax ajax = new Ajax();
        if (departmentList != null) {
            ajax = departmentList.get(position);
        }
        departmentPosition = ajax.getDeptId();
        if((whatToDO.equalsIgnoreCase("Update") || whatToDO.equalsIgnoreCase("Delete") )&& loadOncedep){
            loadOncedep = false;
          //  spDepartment.setSelection(departmentDataAdapter.getItemIndexById(adminAjax.getDeptId(),"D"));
        }
        //fetchDepartmentList(ajax.getCompId());
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == btSubmit.getId()) {
            Ajax ajax = new Ajax();
//            if (ajaxList != null) {
//               // ajax = ajaxList.get(spinnerPosition);

//            }

            if(whatToDO.equalsIgnoreCase("Delete")) {
                deleteAnnouncement(ed_mess.getText().toString(), btDate.getText().toString(), ed_title.getText().toString());
            }
            if(btDate.getText()!=null && !btDate.getText().toString().equalsIgnoreCase("") && ed_mess.getText()!=null && !ed_mess.getText().toString().equalsIgnoreCase("") && ed_title.getText()!=null && !ed_title.getText().toString().equalsIgnoreCase("")){
                if(whatToDO.equalsIgnoreCase("Update")) {

                    updateAnnouncement(ed_mess.getText().toString(), btDate.getText().toString(), ed_title.getText().toString());
                }else{

                    saveAnnouncement(ed_mess.getText().toString(), btDate.getText().toString(), ed_title.getText().toString());
                }
            }else {
                if(btDate.getText() ==null && btDate.getText().toString().equalsIgnoreCase("")){
                    Utility.showCustomToast(context, mView, "Please Enter the Date");

                }
                if(ed_mess.getText()==null && ed_mess.getText().toString().equalsIgnoreCase("")){
                    Utility.showCustomToast(context, mView, "Please Enter the Message");

                } if(ed_title.getText()==null && ed_title.getText().toString().equalsIgnoreCase("")){
                    Utility.showCustomToast(context, mView, "Please Enter the Title");

                }

            }

        }else   if (v.getId() == btDate.getId()) {
            new DatePickerDialog(getActivity(), dater, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }

    }
    private void updatedateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        btDate.setText(sdf.format(myCalendar.getTime()));
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


    private void saveAnnouncement(String valueText,String dateValue,String title) {
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
            requestMap.put("companyId",String.valueOf(companyPosition) );
            requestMap.put("empID", Global.getLoginInfoData().getUserId());
            requestMap.put("activityDate",dateValue );
            requestMap.put("activityDesc",valueText );
            requestMap.put("activityTypeId",String.valueOf(1) );
            requestMap.put("deptId",String.valueOf(departmentPosition) );
            requestMap.put("branch",String.valueOf(branchPosition) );
            requestMap.put("annTitle",title );



            webServiceHandler.saveAnnouncement(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag){

                       Utility.showCustomToast(context, mView, "Announcement successfully saved");
                        ed_mess.setText("");
                        btDate.setText("");
                        ed_title.setText("");
                    }else {
                        Utility.showCustomToast(context, mView,  "Announcement not send kindly try again");

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
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());

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

  // Update Announc



    private void updateAnnouncement(String valueText,String dateValue,String title) {
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
            requestMap.put("companyId",String.valueOf(companyPosition) );
            requestMap.put("empID", Global.getLoginInfoData().getUserId());
            requestMap.put("activityDate",dateValue );
            requestMap.put("activityDesc",valueText );
            requestMap.put("activityTypeId",String.valueOf(1) );
            requestMap.put("deptId",String.valueOf(departmentPosition) );
            requestMap.put("branch",String.valueOf(branchPosition) );
            requestMap.put("annTitle",title );
            requestMap.put("activityId",String.valueOf(adminAjax.getActivityId()) );



            webServiceHandler.updateAnnouncement(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag){

                        Utility.showCustomToast(context, mView, "Announcement successfully saved");
                        ed_mess.setText("");
                        btDate.setText("");
                        ed_title.setText("");
                    }else {
                        Utility.showCustomToast(context, mView,  "Announcement not send kindly try again");

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
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());

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


// Delete


    private void deleteAnnouncement(String valueText,String dateValue,String title) {
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
            requestMap.put("companyId",String.valueOf(companyPosition) );
            requestMap.put("empID", Global.getLoginInfoData().getUserId());
            requestMap.put("activityDate",dateValue );
            requestMap.put("activityDesc",valueText );
            requestMap.put("activityTypeId",String.valueOf(1) );
            requestMap.put("deptId",String.valueOf(departmentPosition) );
            requestMap.put("branch",String.valueOf(branchPosition) );
            requestMap.put("annTitle",title );
            requestMap.put("activityId",String.valueOf(adminAjax.getActivityId()) );



            webServiceHandler.deleteAnnouncement(getActivity(), context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag){

                        Utility.showCustomToast(context, mView, "Announcement SUCCESSFULLY DELETED");
                        ed_mess.setText("");
                        btDate.setText("");
                        ed_title.setText("");
                        Utility.addFragment((Activity) context, R.id.content_frame, fragmentManager, new AdminAnnouncementListFragment(), false, null, Constants.FRAMENT_ADMIN_ANNOUNCEMENT_LIST);

                    }else {
                        Utility.showCustomToast(context, mView,  "Announcement not send kindly try again");

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
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment());

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
