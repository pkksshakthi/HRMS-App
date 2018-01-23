package com.sphinax.hrms.employee.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.activity.LoginActivity;
import com.sphinax.hrms.employee.fragment.AnnouncementListFragment;
import com.sphinax.hrms.employee.fragment.ApplyLeaveFragment;
import com.sphinax.hrms.employee.fragment.AttendanceEnterFragment;
import com.sphinax.hrms.employee.fragment.EmployeeAttendanceFragment;
import com.sphinax.hrms.employee.fragment.EmployeeLeaveManagementFragment;
import com.sphinax.hrms.employee.fragment.EnterHRHelpdeskFragment;
import com.sphinax.hrms.employee.fragment.PaySlipFragment;
import com.sphinax.hrms.employee.fragment.UserMainMenuFragment;
import com.sphinax.hrms.employee.fragment.UserProfileFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.utils.Utility;

public class UserMenuActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mainmenu);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //this.setSupportActionBar(toolbar);


         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        ImageView iv_drawer_open = (ImageView)findViewById(R.id.iv_drawer_open);
        iv_drawer_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
            }
        });

        TextView tv_username = (TextView) headerView.findViewById(R.id.tv_username);
        TextView tv_companyname = (TextView) headerView.findViewById(R.id.tv_company_name);

        tv_username.setText(Utility.getPreference(this).getString(Constants.PREFS_USER_NAME, ""));
        tv_companyname.setText(Utility.getPreference(this).getString(Constants.PREFS_COMPANY_NAME, ""));


        fragmentManager = getSupportFragmentManager();//Get Fragment Manager


        Utility.addFragment(this, R.id.content_frame,fragmentManager,new UserMainMenuFragment(), true, null);




    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_mark_attendance) {
           //  startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
            Utility.addFragment( this, R.id.content_frame,fragmentManager, new AttendanceEnterFragment(), true, null);

        } else if (id == R.id.nav_attendance_report) {
          //   Utility.addFragment( this, R.id.content_frame,fragmentManager, new AnnouncementListFragment(), true, null);

        }  else if (id == R.id.nav_leave_application) {
             Utility.addFragment( this, R.id.content_frame,fragmentManager, new ApplyLeaveFragment(), true, null);

        } else if (id == R.id.nav_leave_management) {

             Utility.addFragment( this, R.id.content_frame,fragmentManager, new EmployeeLeaveManagementFragment(), true, null);

         } else if (id == R.id.nav_my_info) {
             Utility.addFragment( this, R.id.content_frame,fragmentManager, new UserProfileFragment(), true, null);

        } else if (id == R.id.nav_announcement) {
             Utility.addFragment(  this, R.id.content_frame,fragmentManager, new AnnouncementListFragment(), true, null);

        } else if (id == R.id.nav_payslip) {
             Utility.addFragment( this, R.id.content_frame, fragmentManager,new PaySlipFragment(), true, null);

        } else if (id == R.id.nav_help_desk) {
             Utility.addFragment( this, R.id.content_frame, fragmentManager,new EnterHRHelpdeskFragment(), true, null);

        } else if (id == R.id.nav_logout) {
             Intent intent=new Intent(UserMenuActivity.this,LoginActivity.class);
             startActivity(intent);
             finish();
        }

       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
