package com.komputerkit.kasirtoko.Fragment.Utilitas;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Config;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.Random;

/**
 * Created by SAIF on 23/12/2017.
 */

public class UResetData extends Fragment{

    View v ;
    Random rand ;
    Utilitas utilitas ;
    TextView tvCaptcha ;
    Button btnReset ;
    String kode ;
    EditText etCaptcha ;

    @Override
    public void onStart() {
        super.onStart();
        utilitas = new Utilitas(getActivity());
        rand = new Random() ;

        kode = utilitas.intToStr(rand.nextInt(100000)) ;
        tvCaptcha.setText(kode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event();
    }

    public void init(){
        tvCaptcha = v.findViewById(R.id.tvCaptcha) ;
        btnReset = v.findViewById(R.id.btnReset) ;
        etCaptcha = v.findViewById(R.id.etCaptcha) ;
    }

    public void event(){
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset() ;
            }
        });
    }

    public void reset(){
        if (etCaptcha.getText().toString().equals(kode)){
            String path = "/data/data/com.komputerkit."+getString(R.string.appName)+"/databases/"+ Config.getAppName() ;
            String path2 = "/data/data/com.komputerkit."+getString(R.string.appName)+"/shared_prefs/"+ Config.getSessionName()+".xml" ;
            if (utilitas.deleteFile(path) && utilitas.deleteFile(path2)){
                utilitas.setSession("isFirst",true);
                utilitas.getSnackBar(v,R.id.wadah,"Success,Silahkan Restart Aplikasi");
            } else {
                utilitas.getSnackBar(v,R.id.wadah,"Failed");
            }
        } else {
            utilitas.getSnackBar(v,R.id.wadah,"Captcha Wrong");
        }
    }
}
