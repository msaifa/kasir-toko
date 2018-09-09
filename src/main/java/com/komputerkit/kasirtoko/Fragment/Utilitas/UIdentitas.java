package com.komputerkit.kasirtoko.Fragment.Utilitas;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by SAIF on 23/12/2017.
 */

public class UIdentitas extends Fragment {
    View v ;
    EditText etToko,etAlamat,etNoTelp, etCaption1,etCaption2,etCaption3 ;
    Button button ;
    Database db ;
    Utilitas utilitas ;

    @Override
    public void onStart() {
        super.onStart();

        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        setText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_identitas,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event();
    }

    public void init(){
        etToko = v.findViewById(R.id.etToko) ;
        etAlamat = v.findViewById(R.id.etAlamat) ;
        etNoTelp = v.findViewById(R.id.etNoTelp) ;
        etCaption1 = v.findViewById(R.id.etCaption1) ;
        etCaption2 = v.findViewById(R.id.etCaption2) ;
        etCaption3 = v.findViewById(R.id.etCaption3) ;
        button = v.findViewById(R.id.button) ;
    }

    public void event(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    public void simpan(){
        String toko = etToko.getText().toString() ;
        String alamat = etAlamat.getText().toString() ;
        String notelp = etNoTelp.getText().toString() ;
        String caption1 = etCaption1.getText().toString() ;
        String caption2 = etCaption2.getText().toString() ;
        String caption3 = etCaption3.getText().toString() ;
        String sql = "update tblidentitas set nama='"+toko+"',alamat='"+alamat+"',telp='"+notelp+"',caption1='"+caption1+"'," +
                "caption2='"+caption2+"',caption3='"+caption3+"' where id=1" ;

        if (db.execution(sql) ){
            utilitas.getSnackBar(v,R.id.wadah,"Success");
        } else {
            utilitas.getSnackBar(v,R.id.wadah,"Failed");
        }
    }

    public void setText(){
        String sql = "select * from tblidentitas" ;
        etToko.setText(db.getValue(sql,"nama"));
        etAlamat.setText(db.getValue(sql,"alamat"));
        etNoTelp.setText(db.getValue(sql,"telp"));
        etCaption1.setText(db.getValue(sql,"caption1"));
        etCaption2.setText(db.getValue(sql,"caption2"));
        etCaption3.setText(db.getValue(sql,"caption3"));
    }
}
