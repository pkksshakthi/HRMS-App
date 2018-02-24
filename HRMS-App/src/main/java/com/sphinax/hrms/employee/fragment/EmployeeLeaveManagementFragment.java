package com.sphinax.hrms.employee.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.sphinax.hrms.view.EmployeeLeaveViewPagerAdapter;


public class EmployeeLeaveManagementFragment extends Fragment {

    private static final String TAG = "ApplyLeaveFragment-";
    private static Context context;
    private View mView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    public EmployeeLeaveManagementFragment() {
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
        return inflater.inflate(R.layout.fragment_employee_leave_manage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        loadComponent();

        EmployeeLeaveViewPagerAdapter adapter = new EmployeeLeaveViewPagerAdapter(getActivity().getSupportFragmentManager());
        // Add Fragments to adapter one by one
        adapter.addFragment(new LeaveApproveListFragment(), "Approve");
        adapter.addFragment(new LeavePendingListFragment(), "Pending");
        adapter.addFragment(new LeaveRejectedListFragment(), "Rejected");
        viewPager.setAdapter(adapter);

//        viewPager.setOffscreenPageLimit(2);


        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadComponent() {
        viewPager = mView.findViewById(R.id.pager);
        tabLayout = mView.findViewById(R.id.tabs);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }
    }
}
