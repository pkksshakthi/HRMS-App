package com.sphinax.hrms.employee.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sphinax.hrms.R;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.GeoLocationFinder;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.Utility;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class AttendanceEnterFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {


    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private static final int LOCATION_REQUEST_CODE = 101;
    private static Context context;
    private View mView;
    private Button bt_In_att,bt_out_att;
    private TextView tv_att_details;
    private ProgressDialog pdia;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private ArrayList<Ajax> ajaxList;
    private String currentAddress;
    private static final String TAG = "AttendanceEnterFragment-";

    public AttendanceEnterFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance_enter, null, false);


        return view;
    }
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();

        loadMapView();
        loadComponent();
        setListeners();

        fetchDailyUserAttendance();

        if(Global.isMarkAttendance()){
            bt_In_att.setEnabled(false);
            bt_In_att.setClickable(false);

        }
       // Check-In Time 9:00PM

    }
    private void loadComponent() {
        bt_In_att =  mView.findViewById(R.id.bt_mark_in_att);
        bt_out_att = mView.findViewById(R.id.bt_mark_out_att);
        tv_att_details  = mView.findViewById(R.id.tv_att_details);
    }
    private void setListeners() {
        bt_In_att.setOnClickListener(this);
    }

    private void loadMapView(){
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    @SuppressLint("LongLogTag")
    private void loadMap() {
        if (HRMSNetworkCheck.checkInternetConnection(context)) {

            Location location = GeoLocationFinder.getLocationOnly(context);

            try {
                Log.d(TAG + "latitude-", "-" + location.getLatitude());
                Log.d(TAG + "longitude-", "-" + location.getLongitude());

                Address address = GeoLocationFinder.getMyLocationAddress(context,location);
                currentAddress=""+address.getAddressLine(0)+"-"+address.getPostalCode();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You are Here");

               //Adding the created the marker on the map
                mMap.addMarker(markerOptions);
                //mMap.title("my position");
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            } catch (Exception e) {

                e.printStackTrace();
            }

        }
    }

    private void checkAttendanceMarked(){

        if(ajaxList != null){
            if (ajaxList.get(0).getTime()!=null && !ajaxList.get(0).getTime().equalsIgnoreCase("")){
                bt_In_att.setText(covertDate(ajaxList.get(0).getTime()));
                tv_att_details.setText(ajaxList.get(0).getLocation());
            }
        }
    }


    @SuppressLint("LongLogTag")
    private String covertDate(String dtStart){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        try {
            Date date = format.parse(dtStart);

            DateFormat anthorformat = new SimpleDateFormat("hh:mm:ss aa");
            String timeValue = anthorformat.format(date);
            System.out.println("Converted date is : " + anthorformat.format(date));
            return "Check-In Time \n "+ timeValue ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        loadMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_mark_in_att:
                enterUserAttendance();
                loadMap();
                tv_att_details.setText(""+currentAddress);
                break;
        }
    }


    /***Method used to get already enter checkIn from the OPS Service **/

    @SuppressLint("LongLogTag")
    private void fetchDailyUserAttendance() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showCustomToast(context, mView,getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            String datetime = dateformat.format(c.getTime());
            Log.d(TAG, "onViewCreated: " + datetime);
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("refId", Global.getLoginInfoData().getBiometricId().toString());
            requestMap.put("InDate", datetime);

            webServiceHandler.getDailyUserAttendance((Activity) context, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag){
                        //enterUserAttendance();
                        Global.setMarkAttendance(flag);
                        bt_In_att.setEnabled(false);
                        bt_In_att.setClickable(false);
                    }else {
                        Global.setMarkAttendance(false);
                        bt_In_att.setText("Check-In Time");
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    CompanyData companyData = (CompanyData) obj;
                    ajaxList = new ArrayList<>();
                    ajaxList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d("ajaxList", "size --> " + ajaxList.size());

                    checkAttendanceMarked();



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
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void enterUserAttendance() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showCustomToast(context,mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        final String datetime;
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
             datetime = dateformat.format(c.getTime());
            Log.d("mnb", "onViewCreated: " + datetime);
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId",Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, "") );
            requestMap.put("empId",Global.getLoginInfoData().getUserId() );
            requestMap.put("refId", Global.getLoginInfoData().getBiometricId().toString());
            requestMap.put("locationId", currentAddress);
            requestMap.put("empimageDesc", "");
            requestMap.put("clkInTime", datetime);

            webServiceHandler.markDailyUserAttendance((Activity) context, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (!flag){
                        bt_In_att.setText("Check-In Time");
                        tv_att_details.setText("");
                    }else if(flag){
                        fetchDailyUserAttendance();
                        //bt_In_att.setText("Check-In Time \n "+datetime);
                        bt_In_att.setEnabled(false);
                        bt_In_att.setClickable(false);
                        Global.setMarkAttendance(flag);

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
                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
