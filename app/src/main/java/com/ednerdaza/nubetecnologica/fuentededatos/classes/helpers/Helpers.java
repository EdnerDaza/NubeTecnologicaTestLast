package com.ednerdaza.nubetecnologica.fuentededatos.classes.helpers;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.ednerdaza.nubetecnologica.fuentededatos.R;

/**
 * Created by administrador on 10/06/16.
 */
public class Helpers {


    public static Activity activity;
    public static Activity getActivity() {
        return activity;
    }
    public static void setActivity(Activity activity) {
        Helpers.activity = activity;
    }

    public static Context _contexto;
    public static Context getContexto() {
        return _contexto;
    }
    public static void setContexto(Context _contexto) {
        Helpers._contexto = _contexto;
    }

    private static ProgressDialog progressDialog;
    public static ProgressDialog getProgressDialog() {
        return progressDialog;
    }
    public static void setProgressDialog(ProgressDialog progressDialog) {
        Helpers.progressDialog = progressDialog;
    }

    private static Intent intent;

    /**
     * METODO QUE REVISA SI HAY CONEXION A INTERNET Y SI NO DEVUELVE UN DIALOG
     */
    public  static boolean testConectionInternet(Context context) {
        if(Helpers.isNetworkAvailable(context)){
            //Toast.makeText(getApplicationContext(), "con conexion", Toast.LENGTH_SHORT).show();
            Log.v(GlobalConfig.APP_TAG, "<< CON CONEXION >> "+Helpers.isNetworkAvailable(context));
            return true;
        }else{
            //Toast.makeText(getApplicationContext(), "SIN CONEXION", Toast.LENGTH_SHORT).show();
            Log.v(GlobalConfig.APP_TAG, "<< SIN CONEXION >> "+Helpers.isNetworkAvailable(context));
            //MUESTRA UN DIALOG
            Helpers.customDialogConnection(String.format(context.getResources().getString(R.string.error_conexion),
                    context.getResources().getString(R.string.app_name))).show();
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static AlertDialog customDialogConnection(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(resources.getString(R.string.store));
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(getContexto().getResources().getString(R.string.accept_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });

        builder.setNeutralButton(getContexto().getResources().getString(R.string.network_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        dialog.cancel();
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        getActivity().startActivity(intent);
                    }
                });

        final AlertDialog dialog = builder.create();
        return dialog;
    }

    public static AlertDialog customDialogMessage(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(getContexto().getResources().getString(R.string.accept_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.create();
        TextView msg = new TextView(getActivity());
        msg.setGravity(Gravity.CENTER);
        return dialog;

    } // FIN DEL METODO

    public static ProgressDialog customProgressDialog(){
        if (progressDialog != null){
            customProgressDialogClose();
        }
        progressDialog = new ProgressDialog(getActivity());
        return progressDialog;

    }

    public static void customProgressDialogClose() {
        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;
    }

    /**
     * METODO QUE MUESTRA UN LOADING
     */
    public static void progressDialogLoadingShow(String message){
        // SI EL LOADING ES DIFERENTE DE NULO, LO CERRAMOS
        if (progressDialog != null){
            progressDialogClose();
        }
        // Y CREAMOS UNO NUEVO
        progressDialog = customProgressDialog().show(getContexto(), "", message, true, false);
    }

    /**
     * METODO QUE OCULTA UN LOADING
     */
    public static void progressDialogClose() {
        // SI EL LOADING ES DIFERENTE DE NULO Y SE ESTA MOSTRANDO LO CERRAMOS
        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        // LO CONVERTIMOS EN NULO
        progressDialog = null;
    }

    public static boolean haveStoragePermission()
    {
        Log.v(GlobalConfig.APP_TAG, "// haveStoragePermission() //\n"+"HELPERS.CLASS");
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContexto().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(GlobalConfig.APP_TAG, "--  TIENES PERMISO \n"+"HELPERS.CLASS");
                return true;
            } else {
                Log.v(GlobalConfig.APP_TAG, "--  PREGUNTAS POR PERMISO \n"+"HELPERS.CLASS\n...");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.v(GlobalConfig.APP_TAG, "--  YA TENIAS PERMISO DESDE EL MANIFEST\n"+"HELPERS.CLASS\n...");
            return true;
        }
    }


}
