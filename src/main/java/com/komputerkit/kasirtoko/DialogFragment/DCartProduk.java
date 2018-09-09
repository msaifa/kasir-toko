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
import com.komputerkit.kasirtoko.Adapter.AdapterPilihPelanggan;
import com.komputerkit.kasirtoko.Adapter.AdapterProduk;
import com.komputerkit.kasirtoko.Fragment.Master.MProduk;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.TPembayaran;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.fragment_Transaksi_penjualan;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class DCartProduk extends DialogFragment {

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
    public DCartProduk(fragment_Transaksi_penjualan fPencuciann, String type) {
        this.fPencuciann = fPencuciann ;
        this.type = type ;

        utilitas = new Utilitas(fPencuciann.getActivity()) ;
        db = new Database(fPencuciann.getActivity()) ;
    }

    @SuppressLint("ValidFragment")
    public DCartProduk(TPembayaran fragmentbayar, String type) {
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

        utilitas.setText(v,R.id.tvTitle,"Pilih Produk") ;
        loadData("");
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
                        loadData(s.toString());
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

    public void loadData(String cari) {
        ArrayList<QProduk> array = new ArrayList() ;
        AdapterOptionProduk adapter = new AdapterOptionProduk(this,array) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblproduk where produk like '%"+cari+"%' order by produk asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new QProduk(
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
                        "",0,0,1
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }



    public void pilih(TblPelanggan tblPelanggan) {
        fPencuciann.setProduk(tblPelanggan);
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
        getActivity().getFragmentManager().beginTransaction().remove(DCartProduk.this).commit() ;
    }

}