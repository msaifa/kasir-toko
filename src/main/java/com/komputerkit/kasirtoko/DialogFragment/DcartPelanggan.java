package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
import com.komputerkit.kasirtoko.Adapter.AdapterOptionPelanggan;
import com.komputerkit.kasirtoko.Adapter.AdapterOptionProduk;
import com.komputerkit.kasirtoko.Adapter.AdapterPelanggan;
import com.komputerkit.kasirtoko.Adapter.AdapterPilhPegawai;
import com.komputerkit.kasirtoko.Adapter.AdapterPilihPelanggan;
import com.komputerkit.kasirtoko.Adapter.AdapterProduk;
import com.komputerkit.kasirtoko.Fragment.Master.MProduk;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Model.TblPegawai;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.TPembayaran;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.fragment_Transaksi_penjualan;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class DcartPelanggan extends DialogFragment {

    View v ;
    Utilitas utilitas ;
    Database db ;
    public fragment_Transaksi_penjualan fPencuciann;
    public TPembayaran fragmentbayar;
    ConstraintLayout wBack ;
    RecyclerView recyclerView ;
    EditText etCari ;
    String type ;
    Button fabTambah ;
    boolean decimal = false ;

    @SuppressLint("ValidFragment")
    public DcartPelanggan(TPembayaran fragmentbayar, String type) {
        this.fragmentbayar = fragmentbayar ;
        this.type = type ;

        utilitas = new Utilitas(fragmentbayar.getActivity()) ;
        db = new Database(fragmentbayar.getActivity()) ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if(type.equals("pelanggan")){
            utilitas.setText(v,R.id.tvTitle,"Pilih Pelanggan") ;
            loadDataPelanggan("");
        }else {
            utilitas.setText(v,R.id.tvTitle,"Pilih Pegawai") ;
            loadDataPegawai("");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_pilih_master,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;
        init() ;
        event() ;
    }

    public void init() {
        etCari = v.findViewById(R.id.etCari) ;
        fabTambah = v.findViewById(R.id.FABTambah) ;
        fabTambah.setVisibility(View.VISIBLE);
        wBack = v.findViewById(R.id.wBack) ;
        recyclerView = v.findViewById(R.id.recMaster) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void event() {
        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
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
                    loadDataPelanggan(s.toString());
            }
        });

        fabTambah.setVisibility(View.GONE);
//        fabTambah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fm = getFragmentManager();
//                DProduk dProduk = new DProduk(DCartProduk.this,"produk") ;c
//                dProduk.show(fm,"TambahData");
//                MyApp myApp = new MyApp(utilitas,db) ;
//                if (myApp.cekRow("tblproduk")){
//                    ((ActivityUtama)getActivity()).bayar();
//                } else {
//                    DProdukDua dSatuan = new DProdukDua(DCartProduk.this,"produk") ;
//                    FragmentManager fm = getFragmentManager() ;
//                    dSatuan.show(fm,"") ;
//                }
//            }
//        });
    }

    public void loadDataPelanggan(String cari) {
        ArrayList<TblPelanggan> array = new ArrayList() ;
        AdapterPilihPelanggan adapter = new AdapterPilihPelanggan(this,array) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblpelanggan where pelanggan like '%"+cari+"%' order by pelanggan asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new TblPelanggan(
                        db.getString(c,"idpelanggan"),
                        db.getString(c,"pelanggan"),
                        db.getString(c,"alamat"),
                        db.getString(c,"nohp"),
                        db.getString(c,"saldodeposit"),
                        db.getString(c,"hutang")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void loadDataPegawai(String cari) {
        ArrayList<TblPegawai> array = new ArrayList() ;
        AdapterPilihPelanggan adapter = new AdapterPilihPelanggan(array,this) ;
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

    public void pilih(TblPelanggan tblPelanggan) {
        fragmentbayar.setProduk(tblPelanggan);
        keluar();
    }

    public void pilih(TblPegawai tblPegawai) {
        fragmentbayar.setProduk(tblPegawai);
        keluar();
    }

    public void pilih(QProduk tblProduk) {
        fPencuciann.setProduk(tblProduk);
        keluar();
    }

//    public void hapus(final String satuanbesar) {
//        utilitas.showDialog(getString(R.string.iHapusItem), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String q = "delete from tblproduk where idproduk='"+satuanbesar+"'" ;
//                String sql = "select * from tbldetailorder where idproduk='"+satuanbesar+"'" ;
//
//                if (db.getCount(sql) > 0){
//                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailHapusTrans));
//                } else if (db.execution(q)){
//                    load();
//                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusSuccess));
//                } else {
//                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusSuccess));
//                }
//            }
//        });
//    }

//    public void ubah(final String satuanbesar) {
//        DProdukDua dSatuan = new DProdukDua(this,satuanbesar) ;
//        FragmentManager fm = getFragmentManager() ;
//        dSatuan.show(fm,"") ;
//    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DcartPelanggan.this).commit() ;
    }

}