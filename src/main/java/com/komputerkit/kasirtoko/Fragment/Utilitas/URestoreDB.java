package com.komputerkit.kasirtoko.Fragment.Utilitas;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Config;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.io.File;

/**
 * Created by SAIF on 22/12/2017.
 */

public class URestoreDB extends Fragment {
    View v ;
    ConstraintLayout wFolder ;
    Button btnRestore ;
    String path ;
    EditText etFolder ;
    File file ;
    Utilitas utilitas ;

    @Override
    public void onStart() {
        super.onStart();
        utilitas = new Utilitas(getActivity() ) ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restore,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        wFolder = v.findViewById(R.id.wFolder) ;
        btnRestore = v.findViewById(R.id.btnRestore) ;
        etFolder = v.findViewById(R.id.etFolder) ;
    }

    public void event(){
        wFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT) ;
                i.setType("*/*") ;
                i.addCategory(Intent.CATEGORY_OPENABLE) ;

                try{
                    startActivityForResult(Intent.createChooser(i,"Pilih File"),0);
                } catch (ActivityNotFoundException ex){
                    Toast.makeText(getActivity(), "Instal File Manager", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   utilitas.showDialog(getString(R.string.iHapusItem), new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           if (etFolder.getText().toString().equals("")){
                               utilitas.getSnackBar(v,R.id.wadah,"Please select file");
                           } else {
                               restore() ;
                           }
                       }
                   });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && data != null){
            Uri uri = data.getData() ;
            path = uri.toString() ;
            file = new File(uri.getPath());
            String baru = file.toString().replace(Environment.getExternalStorageDirectory()+"","") ;
            etFolder.setText(baru);
        }
    }

    public void restore(){
        String[] temp = file.toString().split("/") ;
        String fileName = temp[temp.length-1] ;

        String def = "/data/data/com.komputerkit."+getString(R.string.appName)+"/databases/" ;
        String folder = file.toString().replace(fileName,"") ;
        if (utilitas.copyFile(folder,def,fileName)){
            if (utilitas.renameFile(def,fileName, Config.getAppName())){
                utilitas.toast("Berhasil");
            } else {
                utilitas.toast("gagal");
            }
        } else {
            utilitas.toast("gagal");
        }
    }
}
