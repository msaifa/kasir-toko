package com.komputerkit.kasirtoko.Fragment.Laporan;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirtoko.Adapter.AdapterLapDeposit;
import com.komputerkit.kasirtoko.Adapter.AdapterLapDetailPenjualan;
import com.komputerkit.kasirtoko.DialogFragment.DDialogExport;
import com.komputerkit.kasirtoko.Model.QDeposit;
import com.komputerkit.kasirtoko.Model.QDetailOrder;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 13/02/2018.
 */

public class LDetailPenjualan extends Fragment {

    View v ;
    RecyclerView recyclerView ;
    EditText etCari,etDari,etSampai ;
    ConstraintLayout lDari, lSampai ;
    Utilitas utilitas ;
    Database db ;
    String sql ;

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        load() ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_laporan,container, false) ;
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
        etDari = v.findViewById(R.id.etDari) ;
        etSampai = v.findViewById(R.id.etSampai) ;
        lDari = v.findViewById(R.id.lDari) ;
        lSampai = v.findViewById(R.id.lSampai) ;
        recyclerView = v.findViewById(R.id.recLaporan) ;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        etDari.setText(utilitas.getDate(getString(R.string.typeDate)));
        etSampai.setText(utilitas.getDate(getString(R.string.typeDate)));
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
                load();
            }
        });

        etDari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                load();
            }
        });

        etSampai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                load();
            }
        });

        lDari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showDateDialog(etDari);
            }
        });

        lSampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showDateDialog(etSampai);
            }
        });

        ConstraintLayout wExport = v.findViewById(R.id.wExport) ;
        wExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDialogExport dialogExport = new DDialogExport(getActivity(),"ldetailpenjualan",sql) ;

                FragmentManager fm = getFragmentManager() ;
                dialogExport.show(fm,"UbahAkun");
            }
        });
    }

    public void load(){
        MyApp myApp = new MyApp(utilitas,db) ;
        ArrayList<QDetailOrder> list = new ArrayList<>() ;
        AdapterLapDetailPenjualan adapter = new AdapterLapDetailPenjualan(list) ;
        recyclerView.setAdapter(adapter) ;


        sql = "select * from qdetailorder where produk like '%"+etCari.getText().toString()+"%' " +
                "and "+myApp.sqlBetweenDate(etDari.getText().toString(),etSampai.getText().toString())+" order by tglorder desc"  ;
        Cursor c = db.select(sql) ;

        int jumlah = 0 ;
        if (db.getCount(sql) > 0){
            while(c.moveToNext()){
                list.add(new QDetailOrder(
                        db.getString(c,"idproduk"),
                        db.getString(c,"kategori"),
                        db.getString(c,"produk"),
                        db.getString(c,"batchnumber"),
                        db.getString(c,"satuanbesar"),
                        db.getString(c,"hargabesar"),
                        db.getString(c,"satuankecil"),
                        db.getString(c,"hargakecil"),
                        db.getString(c,"stokbesar"),
                        db.getString(c,"flagproduk"),
                        db.getString(c,"ketkategori"),
                        db.getString(c,"idpegawai"),
                        db.getString(c,"pegawai"),
                        db.getString(c,"alamatpegawai"),
                        db.getString(c,"nohppegawai"),
                        db.getString(c,"idpelanggan"),
                        db.getString(c,"pelanggan"),
                        db.getString(c,"alamat"),
                        db.getString(c,"nohp"),
                        db.getString(c,"saldodeposit"),
                        db.getString(c,"hutang"),
                        db.getString(c,"idorder"),
                        db.getString(c,"faktur"),
                        db.getString(c,"tglorder"),
                        db.getString(c,"totalorder"),
                        db.getString(c,"bayar"),
                        db.getString(c,"kembali"),
                        db.getString(c,"ketorder"),
                        db.getString(c,"tglmix"),
                        db.getString(c,"iddetailorder"),
                        db.getString(c,"hargaorder"),
                        db.getString(c,"jumlahorder"),
                        db.getString(c,"satuan"),
                        db.getString(c,"ketdetailorder")
                )) ;
                jumlah += utilitas.strToInt(utilitas.perkalian(db.getString(c,"hargaorder"),db.getString(c,"jumlahorder"))) ;
            }
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData)) ;
        }

        utilitas.setText(v,R.id.tvSaldo,"Rp. " + utilitas.removeE(utilitas.intToStr(jumlah))) ;
        adapter.notifyDataSetChanged() ;

    }

}
