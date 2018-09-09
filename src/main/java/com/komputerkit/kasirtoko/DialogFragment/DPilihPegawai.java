package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
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
import com.komputerkit.kasirtoko.Adapter.AdapterPilhPegawai;
import com.komputerkit.kasirtoko.Adapter.AdapterPilihPelanggan;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TTopup;
import com.komputerkit.kasirtoko.Model.TblPegawai;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 04/03/2018.
 */

@SuppressLint("ValidFragment")
public class DPilihPegawai extends DialogFragment {

    View v ;
    Button FABTambah ;
    RecyclerView recyclerView ;
    EditText etCari ;
    Utilitas utilitas ;
    Database db ;
    DPenjualan dPenjualan ;
    ConstraintLayout wBack ;

    @SuppressLint("ValidFragment")
    public DPilihPegawai(DPenjualan dPenjualan) {
        this.dPenjualan = dPenjualan;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;
        utilitas.setText(v,R.id.tvtitle,"Pilih Pegawai") ;

        loadData("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_pilih_master,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        FABTambah = v.findViewById(R.id.FABTambah) ;
        recyclerView = v.findViewById(R.id.recMaster) ;
        etCari = v.findViewById(R.id.etCari) ;
        wBack = v.findViewById(R.id.wBack) ;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void event(){
        FABTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp myApp = new MyApp(utilitas,db) ;

                if (myApp.cekRow("tblpelanggan")){
                    ((ActivityUtama)getActivity()).bayar();
                } else {
                    DPegawai dPegawai = new DPegawai(DPilihPegawai.this) ;
                    FragmentManager fm = getFragmentManager() ;
                    dPegawai.show(fm,"adsf") ;
                }
            }
        });

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadData(s.toString());
            }
        });

        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });
    }

    public void loadData(String cari){
        ArrayList<TblPegawai> array = new ArrayList() ;
        AdapterPilhPegawai adapter = new AdapterPilhPegawai(this,array) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblpegawai where pegawai like '%"+cari+"%' order by pegawai asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new TblPegawai(
                        db.getString(c,"idpegawai"),
                        db.getString(c,"pegawai"),
                        db.getString(c,"alamatpegawai"),
                        db.getString(c,"nohppegawai")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void pilih(TblPegawai tblPegawai){
        dPenjualan.set2(tblPegawai) ;
        keluar();
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit() ;
    }

}
