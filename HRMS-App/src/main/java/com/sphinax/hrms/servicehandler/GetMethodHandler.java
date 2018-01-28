package com.sphinax.hrms.servicehandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sphinax.hrms.model.ServiceRequest;
import com.sphinax.hrms.utils.HRMSNetworkCheck;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
    private HashMap<String, String>  requestMap;

    public GetMethodHandler() {
    }

    public GetMethodHandler(Activity activity, Context context, String url, boolean isData, HashMap<String, String> requestMap, AsyncResponse response) throws MalformedURLException {
        this.context = context;
        this.url = new URL(url);
        this.delegate = response;
        this.isData = isData;
        this.activity = activity;
        this.requestMap = requestMap;
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

            if (requestMap.size() != 0) {
                Iterator it = requestMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    Log.d(TAG, " " + pair.getKey() + " = " + pair.getValue());
                    conn.setRequestProperty(pair.getKey().toString(), pair.getValue().toString());
                    it.remove();
                }
            }

            conn.connect();
            String response = "";
            int status = conn.getResponseCode();
            Scanner inStream = null;
            Log.d(TAG + "Response Code-", status + "");
            if(status == 500){
                delegate.processFinish(this.context, jsonObject);
            }
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
                    try {
                        jsonObject = new JSONObject(response);
                    }catch (Exception e){
                        delegate.processFinish(this.context, jsonObject);

                    }
                }
            }


        } catch (Exception e) {
            try {
                delegate.processFinish(this.context, jsonObject);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
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
