package com.sphinax.hrms.servicehandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sphinax.hrms.model.ServiceRequest;
import com.sphinax.hrms.utils.HRMSNetworkCheck;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class GetMethodHandler extends AsyncTask<Void, Void, JSONObject> {

    private static final String TAG = "GetMethodHandler-";
    private AsyncResponse delegate = null;
    private Context context;
    private URL url;
    private Activity activity = null;
    private Boolean isData;
    private String jsonString;
    private ServiceRequest serviceRequest;

    public GetMethodHandler() {
    }

    public GetMethodHandler(Activity activity, Context context, String url, boolean isData, ServiceRequest  serviceRequest, AsyncResponse response) throws MalformedURLException {
        this.context = context;
        this.url = new URL(url);
        this.serviceRequest = new ServiceRequest();
        this.delegate = response;
        this.isData = isData;
        this.activity = activity;
        this.serviceRequest = serviceRequest;
        Log.d(TAG + "URL-", url);

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @SuppressLint("LongLogTag")
    @Override
    protected JSONObject doInBackground(Void... arg) {
        JSONObject jsonObject = null;
        try {
            // check internet connectivity

            if (!HRMSNetworkCheck.checkInternetConnection(context)) {
                return null;
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000); //set timeout to 60 seconds
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if(serviceRequest.getUserId() != null) {
                conn.setRequestProperty("userId", serviceRequest.getUserId());
            }
            if(serviceRequest.getUserpwd() != null){
            conn.setRequestProperty("userpwd", serviceRequest.getUserpwd());
            }
            if(serviceRequest.getCompId() != null){
            conn.setRequestProperty("compId", serviceRequest.getCompId());
            }
            if(serviceRequest.getUrl() != null){
            conn.setRequestProperty("url", serviceRequest.getUrl());
            }
            if(serviceRequest.getCompanyId() != null){
            conn.setRequestProperty("companyId", serviceRequest.getCompanyId());
            }
            if(serviceRequest.getEmployeeId() != null){
            conn.setRequestProperty("employeeId", serviceRequest.getEmployeeId());
            }
            if(serviceRequest.getMonth() != null){
            conn.setRequestProperty("month", serviceRequest.getMonth());
            }
            if(serviceRequest.getYear() != null){
            conn.setRequestProperty("year", serviceRequest.getYear());
            }
            if(serviceRequest.getBranch() != null){
            conn.setRequestProperty("branch", serviceRequest.getBranch());
            }
            if(serviceRequest.getEmpId() != null){
            conn.setRequestProperty("empId", serviceRequest.getEmpId());
            }
            if(serviceRequest.getDeptId() != null){
            conn.setRequestProperty("deptId", serviceRequest.getDeptId());
            }
            if(serviceRequest.getRefId() != null){
            conn.setRequestProperty("refId", serviceRequest.getRefId());
            }
            if(serviceRequest.getLocationId() != null){
            conn.setRequestProperty("locationId", serviceRequest.getLocationId());
            }
            if(serviceRequest.getEmpimageDesc() != null){
            conn.setRequestProperty("empimageDesc", serviceRequest.getEmpimageDesc());
            }
            if(serviceRequest.getClkInTime() != null){
            conn.setRequestProperty("clkInTime", serviceRequest.getClkInTime());
            }
            if(serviceRequest.getInDate() != null){
            conn.setRequestProperty("InDate", serviceRequest.getInDate());
            }
            if(serviceRequest.getDate1() != null){
            conn.setRequestProperty("date1", serviceRequest.getDate1());
            }
            if(serviceRequest.getLeavestatus() != null){
            conn.setRequestProperty("leavestatus", serviceRequest.getLeavestatus());
            }
            if(serviceRequest.getRequestTo() != null){
            conn.setRequestProperty("requestTo", serviceRequest.getRequestTo());
            }
            if(serviceRequest.getRequestType() != null){
            conn.setRequestProperty("requestType", serviceRequest.getRequestType());
            }
            if(serviceRequest.getDescription() != null){
            conn.setRequestProperty("description", serviceRequest.getDescription());
            }
            if(serviceRequest.getCompanyId() != null){
            conn.setRequestProperty("companyID", serviceRequest.getCompanyId());
            }
            if(serviceRequest.getEmpID() != null){
            conn.setRequestProperty("empID", serviceRequest.getEmpID());
            }
            if(serviceRequest.getActivityDate() != null){
            conn.setRequestProperty("activityDate", serviceRequest.getActivityDate());
            }
            if(serviceRequest.getActivityDesc() != null){
            conn.setRequestProperty("activityDesc", serviceRequest.getActivityDesc());
            }
            if(serviceRequest.getActivityTypeId() != null){
            conn.setRequestProperty("activityTypeId", serviceRequest.getActivityTypeId());
            }
            if(serviceRequest.getAnnTitle() != null){
            conn.setRequestProperty("annTitle", serviceRequest.getAnnTitle());
            }
            if(serviceRequest.getDeptId() != null){
            conn.setRequestProperty("DeptId", serviceRequest.getDeptId());
            }
            if(serviceRequest.getBranchId() != null){
            conn.setRequestProperty("branchId", serviceRequest.getBranchId());
            }
            if(serviceRequest.getReqStatus() != null){
            conn.setRequestProperty("reqStatus", serviceRequest.getReqStatus());
            }
            if(serviceRequest.getReqSeqId() != null){
            conn.setRequestProperty("reqSeqId", serviceRequest.getReqSeqId());
            }
            if(serviceRequest.getHrmsg() != null){
            conn.setRequestProperty("hrmsg", serviceRequest.getHrmsg());
            }
            if(serviceRequest.getHrStatus() != null){
            conn.setRequestProperty("hrStatus", serviceRequest.getHrStatus());
            }
            if(serviceRequest.getGlobEmpId() != null){
            conn.setRequestProperty("globEmpId", serviceRequest.getGlobEmpId());
            }
            if(serviceRequest.getLeaveTypeId() != null){
            conn.setRequestProperty("leaveTypeId", serviceRequest.getLeaveTypeId());
            }
            if(serviceRequest.getFromDate() != null){
            conn.setRequestProperty("fromDate", serviceRequest.getFromDate());
            }
            if(serviceRequest.getToDate() != null){
            conn.setRequestProperty("toDate", serviceRequest.getToDate());
            }
            if(serviceRequest.getLeaveId() != null){
            conn.setRequestProperty("leaveId", serviceRequest.getLeaveId());
            }
            if(serviceRequest.getLeaveStatusNew() != null){
            conn.setRequestProperty("leaveStatusNew", serviceRequest.getLeaveStatusNew());
            }
            if(serviceRequest.getLeaveStatusOld() != null){
            conn.setRequestProperty("leaveStatusOld", serviceRequest.getLeaveStatusOld());
            }
            if(serviceRequest.getLeaveMonth() != null){
            conn.setRequestProperty("leaveMonth", serviceRequest.getLeaveMonth());
            }
            if(serviceRequest.getLeaveYear() != null){
            conn.setRequestProperty("leaveYear", serviceRequest.getLeaveYear());
            }
            if(serviceRequest.getReason() != null){
            conn.setRequestProperty("reason", serviceRequest.getReason());
            }
            if(serviceRequest.getSession() != null){
            conn.setRequestProperty("session", serviceRequest.getSession());
            }
            if(serviceRequest.getNoofdays() != null){
            conn.setRequestProperty("noofdays", serviceRequest.getNoofdays());
            }

            conn.connect();
            String response = "";
            int status = conn.getResponseCode();
            Scanner inStream = null;
            Log.d(TAG + "Response Code-", status + "");
            if (status == HttpsURLConnection.HTTP_BAD_REQUEST) {
                inStream = new Scanner(conn.getErrorStream());
            }
            if (status == HttpsURLConnection.HTTP_OK) {
                inStream = new Scanner(conn.getInputStream());
            }
            if (status == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                inStream = new Scanner(conn.getErrorStream());
            }
            if (inStream != null) {
                //process the stream and store it in StringBuilder
                while (inStream.hasNextLine())
                    response += (inStream.nextLine());
            }
            conn.disconnect();
            Log.d(TAG + "Response-", response);
            if (status == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                if (!response.isEmpty()) {
                    Log.d(TAG + "SC_UNAUTHORIZED-", response);
                    JSONObject json = new JSONObject(response);
                    jsonObject = json.getJSONObject("meta");
                }
            } else {
                if (!response.isEmpty()) {
                     jsonObject = new JSONObject(response);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            delegate.processFinish(this.context, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
