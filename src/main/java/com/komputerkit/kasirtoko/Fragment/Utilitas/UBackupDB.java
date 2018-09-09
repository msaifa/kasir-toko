package com.komputerkit.kasirtoko.Fragment.Utilitas;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Config;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.Arrays;

/**
 * Created by SAIF on 22/12/2017.
 */

public class UBackupDB extends Fragment {

    Button btnBackup ;
    View v ;
    Utilitas utilitas ;
    EditText etFileName ;

    @Override
    public void onStart() {
        super.onStart();
        utilitas = new Utilitas(getActivity()) ;

        etFileName.setText(utilitas.getDate("dd-MM-yyyy"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_backup,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event();
    }

    public void init(){
        btnBackup = v.findViewById(R.id.btnBackup) ;
        etFileName = v.findViewById(R.id.etFileName) ;
    }

    public void event(){
        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilitas.showDialog(getString(R.string.iHapusItem), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String file = etFileName.getText().toString() ;
                        String[] temp = file.split("") ;
                        if (!Arrays.asList(temp).contains("/")){
                            backup();
                        } else {
                            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailBackupDB));
                        }
                    }
                });
            }
        });
    }

    public void backup(){
        if (utilitas.copyFile("/data/data/com.komputerkit."+getString(R.string.appName)+"/databases/",
                Config.getDefaultPath(), Config.getAppName())){
            if (utilitas.renameFile(Config.getDefaultPath(), Config.getAppName(),
                    Config.getAppName()+" - "+etFileName.getText().toString()+".sql")){
                utilitas.getSnackBar(v,R.id.wadah,"Success");
            }
        } else {
            utilitas.getSnackBar(v,R.id.wadah,"Failed. " + getString(R.string.iCekIzin));
        }
    }
}
