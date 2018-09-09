package com.komputerkit.kasirtoko;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.komputerkit.kasirtoko.Utilitas.Config;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ActivitySplash extends AppCompatActivity {

    Utilitas utilitas ;
    boolean isFirst = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        permissionStorage() ;
        utilitas = new Utilitas(this) ;
        new Database(this) ;

        setSplash();
    }

    private void setSplash() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                isFirst = utilitas.getSession("isFirst",true) ;
                if (isFirst){
                    utilitas.setSession("isFirst",createTable()) ;
//                    Intent i = new Intent(ActivitySplash.this, ActivityUtama.class);
//                    startActivity(i);
                    startActivity(new Intent(ActivitySplash.this,ActivityIntro.class));
                } else {
                    Intent i = new Intent(ActivitySplash.this, ActivityUtama.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, 5000);
    }

    public boolean createTable(){

        InputStream is = null;
        OutputStream os = null ;
        try {
            is = getAssets().open(Config.getAppName());
            os = new FileOutputStream(getDatabasePath(Config.getAppName()));

            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }

            os.flush();
            os.close();
            is.close();

            return false ;
        } catch (IOException e) {
            utilitas.toast("Failed Set Application\n"+ e.toString());

            return true ;
        }

    }

    public boolean permissionStorage(){
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE ;
        int requestCode = 0x3 ;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
            return false ;
        } else {
            return true ;
        }
    }
}
