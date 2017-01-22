package com.ednerdaza.nubetecnologica.fuentededatos.mvc.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ednerdaza.nubetecnologica.fuentededatos.R;
import com.ednerdaza.nubetecnologica.fuentededatos.classes.helpers.GlobalConfig;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.Interfaces.ItemModelInterface;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.base.VolleyQueue;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities.EntityResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by administrador on 9/06/16.
 */
public class EntityModel {


    public static void getItems(final Context context, final ItemModelInterface itemModelInterface){

        String url = GlobalConfig.BASE_URL_SERVICE_ENTRIES;

        final Gson gson = new Gson();
        Log.v(GlobalConfig.APP_TAG, " <ItemModel> URL --> " + url);

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response==null){
                    Log.v(GlobalConfig.APP_TAG, "Response --> Cadena nula "+response);
                }else{
                    Log.v(GlobalConfig.APP_TAG, "Response --> Cadena buena "+response);
                }

                EntityResponse res = gson.fromJson(response.toString(), EntityResponse.class);
                Log.v(GlobalConfig.APP_TAG, "Res --> "+res);

                if (res != null){
                    itemModelInterface.completeSuccess(res);
                }else {
                    itemModelInterface.completeFail(context.getResources().getString(R.string.error_response_null));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(GlobalConfig.APP_TAG, "ERROR --> "+error);
                itemModelInterface.completeFail(context.getResources().getString(R.string.error_listener));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        VolleyQueue.addToRequestQueue(request, "Entity");
    }

    public static void getItemsAssets(final Context context, final ItemModelInterface itemModelInterface){

        final Gson gson = new Gson();
        String json = null;
        try {
            InputStream is = context.getAssets().open( GlobalConfig.BASE_JSON);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            JSONObject obj = new JSONObject(json);

            if (obj==null){
                Log.v(GlobalConfig.APP_TAG, "Response --> Cadena nula "+obj);
            }else{
                Log.v(GlobalConfig.APP_TAG, "Response --> Cadena buena "+obj);
            }

            EntityResponse res = gson.fromJson(obj.toString(), EntityResponse.class);
            Log.v(GlobalConfig.APP_TAG, "Res --> "+res);

            if (res != null){
                itemModelInterface.completeSuccess(res);
            }else {
                itemModelInterface.completeFail(context.getResources().getString(R.string.error_response_null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            itemModelInterface.completeFail(context.getResources().getString(R.string.error_response_null));
        }

    }

}
