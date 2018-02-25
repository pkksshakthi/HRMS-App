package com.sphinax.hrms.employee.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.activity.LoginActivity;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
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
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;
import com.squareup.picasso.Picasso;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMenuActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ImageView iv_drawer_open;
    private TextView tv_username, tv_companyname;
    private View headerView;
    private CircleImageView ivPhoto;
    private static final String TAG = "UserMenuActivity-";

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

        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        headerView = navigationView.getHeaderView(0);

        loadComponent();
        setListeners();
        setData();


        iv_drawer_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


        fragmentManager = getSupportFragmentManager();//Get Fragment Manager

        Utility.addFragment(this, R.id.content_frame, fragmentManager, new UserMainMenuFragment(), true, null, Constants.FRAMENT_USER_MENU);
    }

    private void loadComponent() {
        iv_drawer_open = findViewById(R.id.iv_drawer_open);
        tv_username = headerView.findViewById(R.id.tv_username);
        tv_companyname = headerView.findViewById(R.id.tv_company_name);
        ivPhoto = headerView.findViewById(R.id.profile_image);

    }

    private void setListeners() {

    }

    private void setData() {
        tv_username.setText(Global.getLoginInfoData().getEmpName());
        tv_companyname.setText(Utility.getPreference(this).getString(Constants.PREFS_COMPANY_NAME, ""));
        loadBitmap(Global.getLoginInfoData().getEmpImage());
    }

    private void loadBitmap(String urlIV){
        try {
           // URL url = new URL(Constants.IMAGE_URL +urlIV);
            Picasso.with(getApplicationContext())
                    .load(Constants.IMAGE_URL +urlIV)
                    .resize(48,48).into(ivPhoto);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
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

            Utility.addFragment(this, R.id.content_frame, fragmentManager, new AttendanceEnterFragment(), true, null, Constants.FRAMENT_ANTTENDANCE_ENTER);
        } else if (id == R.id.nav_attendance_report) {
            Utility.addFragment(this, R.id.content_frame, fragmentManager, new EmployeeAttendanceFragment(), true, null, Constants.FRAMENT_ANNOUNCEMENT_LIST);

        } else if (id == R.id.nav_leave_application) {
            Utility.addFragment(this, R.id.content_frame, fragmentManager, new ApplyLeaveFragment(), true, null, Constants.FRAMENT_LEAVE_APPLY);

        } else if (id == R.id.nav_leave_management) {

            Utility.addFragment(this, R.id.content_frame, fragmentManager, new EmployeeLeaveManagementFragment(), true, null, Constants.FRAMENT_LEAVE_MANAGEMENT);

        } else if (id == R.id.nav_my_info) {
            Utility.addFragment(this, R.id.content_frame, fragmentManager, new UserProfileFragment(), true, null, Constants.FRAMENT_USER_INFO);

        } else if (id == R.id.nav_announcement) {
            Utility.addFragment(this, R.id.content_frame, fragmentManager, new AnnouncementListFragment(), true, null, Constants.FRAMENT_ANNOUNCEMENT_LIST);

        } else if (id == R.id.nav_payslip) {
            Utility.addFragment(this, R.id.content_frame, fragmentManager, new PaySlipFragment(), true, null, Constants.FRAMENT_PAYSLIP);

        } else if (id == R.id.nav_help_desk) {
            Utility.addFragment(this, R.id.content_frame, fragmentManager, new EnterHRHelpdeskFragment(), true, null, Constants.FRAMENT_HR_HELPDESK_ENTER);

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(UserMenuActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
//        if (!HRMSNetworkCheck.checkInternetConnection(getApplicationContext())) {
//          //  Utility.callErrorScreen(this, R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
//            return;
//        }
    }
}
