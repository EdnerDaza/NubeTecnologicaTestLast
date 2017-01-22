package com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.ednerdaza.nubetecnologica.fuentededatos.R;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.base.VolleyQueue;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {



    private long mSplashDelay =  3000; //3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Se crea la cola de peticiones
        VolleyQueue.createQueue(getApplicationContext());


        //TAREA PARA IR AL HOME
        TimerTask taskHome = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
                //overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        };

        Timer timer = new Timer();
        timer.schedule(taskHome, mSplashDelay);


    }
}
