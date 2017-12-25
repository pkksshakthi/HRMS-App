package com.sphinax.hrms.servicehandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class PostMethodHandler extends AsyncTask<Void, Void, JSONObject> {

    private static final String TAG = "PostMethodHandler-";

    private AsyncResponse delegate = null;
    private Context context;
    private URL url;
    private Activity activity;

    private String jsonString;

    public PostMethodHandler() {
    }

    public PostMethodHandler(Activity activity, Context context, String url, String jsonString, AsyncResponse response) throws MalformedURLException {
        this.context = context;
        Log.d(TAG + "URL-", url);
        this.url = new URL(url);
        this.activity = activity;
        this.delegate = response;
        this.jsonString = jsonString;
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
                throw new RuntimeException();
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(60000); //set timeout to 60 seconds
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String input = jsonString;

            Log.d(TAG + "Request-", input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.close();
            conn.connect();
            //os.flush();
            String response = "";
            int status = conn.getResponseCode();
            Log.d(TAG + "Response Code-", status + "");
            Scanner inStream = null;
            if (status == HttpsURLConnection.HTTP_BAD_REQUEST) {
//start listening to the stream
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
                    JSONObject json = new JSONObject(response);
                    jsonObject = json.getJSONObject("meta");
                }
            } else {
                if (!response.isEmpty()) {
                    JSONObject json = new JSONObject(response);
                    jsonObject = json.getJSONObject("data");
                    Log.d(TAG + "currentDateTime -", response);
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