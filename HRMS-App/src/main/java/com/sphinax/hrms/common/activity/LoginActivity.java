package com.sphinax.hrms.common.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.Login_Fragment;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static FragmentManager fragmentManager;
    private Context context;
    //private ImageView bt_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        context = this.getApplicationContext();
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            Utility.addFragment(this, R.id.frameContainer, fragmentManager, new Login_Fragment(), true, null, Constants.FRAMENT_LOGIN);
        }

        loadComponent();
        setListeners();
    }

    private void loadComponent() {
        //bt_close = findViewById(R.id.close_activity);
    }

    private void setListeners() {
        //bt_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_activity:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
         if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }  else if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!HRMSNetworkCheck.checkInternetConnection(getApplicationContext())) {
            Utility.callErrorScreen(this, R.id.frameContainer, fragmentManager, new SomeProblemFragment());
        }
    }
}
