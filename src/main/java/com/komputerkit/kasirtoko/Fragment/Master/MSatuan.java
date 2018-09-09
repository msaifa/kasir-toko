package com.komputerkit.kasirtoko.Fragment.Master;

import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.komputerkit.kasirtoko.ActivityUtama;
import com.komputerkit.kasirtoko.Adapter.AdapterSatuan;
import com.komputerkit.kasirtoko.DialogFragment.DSatuan;
import com.komputerkit.kasirtoko.Model.TblSatuan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 13/02/2018.
 */

public class MSatuan extends Fragment {

    View v ;
    Database db;
    Utilitas utilitas ;
    EditText etCari ;
    RecyclerView recyclerView ;
    Button FAB ;

    @Override
    public void onStart() {
        super.onStart();

        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        load();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        etCari = v.findViewById(R.id.etCari) ;
        recyclerView = v.findViewById(R.id.recMaster) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );
        FAB = v.findViewById(R.id.FABTambah) ;
    }

    public void event(){
        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                load() ;
            }
        });

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp myApp = new MyApp(utilitas,db) ;
                if (myApp.cekRow("tblsatuan")){
                    ((ActivityUtama)getActivity()).bayar();
                } else {
                    DSatuan dSatuan = new DSatuan(MSatuan.this) ;
                    FragmentManager fm = getFragmentManager() ;
                    dSatuan.show(fm,"") ;
                }

            }
        });
    }

    public void load(){
        ArrayList<TblSatuan> list = new ArrayList() ;
        AdapterSatuan adapter = new AdapterSatuan(this,list) ;
        recyclerView.setAdapter(adapter);
        String q = "select * from tblsatuan where satuanbesar like '%"+ etCari.getText().toString() +"%' order by satuanbesar" ;

        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while (c.moveToNext()){
                list.add(new TblSatuan(
                        db.getString(c,"satuanbesar"),
                        db.getString(c,"nilaibesar"),
                        db.getString(c,"satuankecil"),
                        db.getString(c,"nilaikecil")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }


    public void hapus(final String satuanbesar) {
        utilitas.showDialog(getString(R.string.iHapusItem), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String q = "delete from tblsatuan where satuanbesar='"+satuanbesar+"'" ;
                String sql = "select * from tblproduk where satuanbesar='"+satuanbesar+"'" ;

                if (db.getCount(sql) > 0){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailHapusTrans));
                } else if (db.execution(q)){
                    load();
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusSuccess));
                } else {
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusSuccess));
                }
            }
        });
    }

    public void ubah(final String satuanbesar) {
        DSatuan dSatuan = new DSatuan(MSatuan.this,satuanbesar) ;
        FragmentManager fm = getFragmentManager() ;
        dSatuan.show(fm,"") ;
    }
}
