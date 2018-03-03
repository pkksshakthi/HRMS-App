package com.sphinax.hrms.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.sphinax.hrms.employee.fragment.LeaveApproveListFragment;
import com.sphinax.hrms.employee.fragment.LeavePendingListFragment;
import com.sphinax.hrms.employee.fragment.LeaveRejectedListFragment;
import com.sphinax.hrms.global.Global;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganesaka on 1/14/2018.
 */

public class EmployeeLeaveViewPagerAdapter extends FragmentStatePagerAdapter {
 //   private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public EmployeeLeaveViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("sdsds", "getItem: " + position);
        final Fragment tab;
        //return mFragmentList.get(position);
        switch (position) {
            case 0:
                tab = new LeaveApproveListFragment();
                return tab;
            case 1:
                tab = new LeavePendingListFragment();
                return tab;
            case 2:
                tab = new LeaveRejectedListFragment();
                return tab;
            default:
                Log.d("s", "getItem: " + Global.getTabPosition());
                if (Global.getTabPosition() == 0){
                    tab = new LeaveApproveListFragment();
                    return tab;
                }else if(Global.getTabPosition() == 1){
                    tab = new LeavePendingListFragment();
                    return tab;
                }else if(Global.getTabPosition() == 2){
                    tab= new LeaveRejectedListFragment();
                    return tab;
                }
                return null;
        }

    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }

    public void addFragment(String title) {
       // mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }



}

