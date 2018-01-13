package com.sphinax.hrms.employee.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sphinax.hrms.R;
import com.sphinax.hrms.utils.Utility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserMainMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserMainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserMainMenuFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static Context context;
    private View mView;
    private OnFragmentInteractionListener mListener;
    private  FragmentManager fragmentManager;
    public UserMainMenuFragment() {
        // Required empty public constructor
    }
    private LinearLayout ll_mark_attendance,ll_attendance_report,ll_leave_app,ll_leave_mana,ll_info,ll_announcement,ll_payslip,ll_helpdesk;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserMainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserMainMenuFragment newInstance(String param1, String param2) {
        UserMainMenuFragment fragment = new UserMainMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private static UserMainMenuFragment instance;
    public static UserMainMenuFragment getInstance() {
        if (instance == null)
            instance = new UserMainMenuFragment();
        return instance;
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
        return inflater.inflate(R.layout.fragment_user_main_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        ll_mark_attendance = mView.findViewById(R.id.ll_mark_attendance);
        ll_attendance_report = mView.findViewById(R.id.ll_attendance_report);
        ll_leave_app = mView.findViewById(R.id.ll_leave_app);
        ll_leave_mana = mView.findViewById(R.id.ll_leave_mana);
        ll_info = mView.findViewById(R.id.ll_info);
        ll_announcement = mView.findViewById(R.id.ll_announcement);
        ll_payslip = mView.findViewById(R.id.ll_payslip);
        ll_helpdesk = mView.findViewById(R.id.ll_helpdesk);


         fragmentManager = getActivity().getSupportFragmentManager();

        ll_mark_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.addFragment((Activity) context, R.id.content_frame,fragmentManager, new AttendanceEnterFragment(), true, null);

            }
        });
        ll_attendance_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Utility.addFragment( (Activity) context, R.id.content_frame,fragmentManager, new AnnouncementListFragment(), true, null);

            }
        });
        ll_leave_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.addFragment((Activity) context, R.id.content_frame, fragmentManager,new ApplyLeaveFragment(), true, null);

            }
        });
        ll_leave_mana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.addFragment( (Activity) context, R.id.content_frame,fragmentManager, new EmployeeLeaveManagementFragment(), true, null);

            }
        });
        ll_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.addFragment((Activity) context, R.id.content_frame,fragmentManager, new UserProfileFragment(), true, null);

            }
        });
        ll_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.addFragment( (Activity) context, R.id.content_frame,fragmentManager, new AnnouncementListFragment(), true, null);

            }
        });
        ll_payslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.addFragment((Activity) context, R.id.content_frame, fragmentManager,new PaySlipFragment(), true, null);

            }
        });
        ll_helpdesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.addFragment((Activity) context, R.id.content_frame,fragmentManager, new EnterHRHelpdeskFragment(), true, null);

            }
        });




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
}
