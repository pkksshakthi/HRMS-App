package com.sphinax.hrms.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sphinax.hrms.employee.fragment.LeaveApproveListFragment;
import com.sphinax.hrms.employee.fragment.LeavePendingListFragment;
import com.sphinax.hrms.employee.fragment.LeaveRejectedListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganesaka on 1/14/2018.
 */

public class EmployeeLeaveViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public EmployeeLeaveViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        //return mFragmentList.get(position);
        switch (position) {
            case 0:
                LeaveApproveListFragment tab1 = new LeaveApproveListFragment();
                return tab1;
            case 1:
                LeavePendingListFragment tab2 = new LeavePendingListFragment();
                return tab2;
            case 2:
                LeaveRejectedListFragment tab3 = new LeaveRejectedListFragment();
                return tab3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}

