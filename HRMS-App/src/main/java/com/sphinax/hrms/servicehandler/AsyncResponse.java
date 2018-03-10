package com.sphinax.hrms.servicehandler;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ganesaka on 12/24/2017.
 */

interface AsyncResponse {

    void processFinish(Context context, JSONObject output);

}
