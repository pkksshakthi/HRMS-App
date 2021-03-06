package com.sphinax.hrms.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.admin.fragment.AdminMainMenuFragment;
import com.sphinax.hrms.admin.fragment.AnnouncementCreateFragment;
import com.sphinax.hrms.admin.fragment.AdminAnnouncementListFragment;
import com.sphinax.hrms.admin.fragment.AttendanceReportFragment;
import com.sphinax.hrms.admin.fragment.HRHelpdeskFragment;
import com.sphinax.hrms.admin.fragment.LeaveManagementFragment;
import com.sphinax.hrms.common.activity.LoginActivity;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.utils.Utility;

public class AdminMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "AdminMenuActivity";
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mainmenu);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        //this.setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        ImageView iv_drawer_open = findViewById(R.id.iv_drawer_open);
        iv_drawer_open.setOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        TextView tv_username = headerView.findViewById(R.id.tv_username);
        TextView tv_companyname = headerView.findViewById(R.id.tv_company_name);

        tv_username.setText(Global.getLoginInfoData().getEmpName());
        tv_companyname.setText(Utility.getPreference(this).getString(Constants.PREFS_COMPANY_NAME, ""));


        fragmentManager = getSupportFragmentManager();//Get Fragment Manager


        Utility.addFragment(this, R.id.content_frame,fragmentManager,new AdminMainMenuFragment(), true, null,"");




    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Log.d(TAG, "onBackPressed: " + this.getSupportFragmentManager().getBackStackEntryCount());
            getSupportFragmentManager().popBackStack();
        }
        else {
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_attendance_report) {
               Utility.addFragment( this, R.id.content_frame,fragmentManager, new AttendanceReportFragment(), true, null,"");

        }else if (id == R.id.nav_leave_management) {

           Utility.addFragment( this, R.id.content_frame,fragmentManager, new LeaveManagementFragment(), true, null,"");

        } else if (id == R.id.nav_announcement) {
          //Utility.addFragment(  this, R.id.content_frame,fragmentManager, new AnnouncementCreateFragment(), true, null,"");
          Utility.addFragment(  this, R.id.content_frame,fragmentManager, new AdminAnnouncementListFragment(), true, null,Constants.FRAMENT_ADMIN_ANNOUNCEMENT_LIST);

        } else if (id == R.id.nav_help_desk) {
            Utility.addFragment( this, R.id.content_frame, fragmentManager,new HRHelpdeskFragment(), true, null,"");

        } else if (id == R.id.nav_logout) {
            Intent intent=new Intent(AdminMenuActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
