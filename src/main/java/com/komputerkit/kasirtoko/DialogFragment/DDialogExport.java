package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.komputerkit.kasirtoko.ActivityUtama;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Config;
import com.komputerkit.kasirtoko.Utilitas.ExportExcel;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.io.IOException;

import jxl.write.WriteException;

/**
 * Created by SAIF on 03/01/2018.
 */

@SuppressLint("ValidFragment")
public class DDialogExport extends DialogFragment {

    Context context ;
    ConstraintLayout wBack ;
    View v ;
    Utilitas utilitas ;
    Button btnIya,btnTidak ;
    EditText etPath ;

    String type,query ;

    @SuppressLint("ValidFragment")
    public DDialogExport(String type) {
        this.type = type;
    }

    @SuppressLint("ValidFragment")
    public DDialogExport(Context context, String type, String query) {
        this.context = context;
        this.type = type;
        this.query = query;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_export,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;

        init() ;
        event();
    }

    public void init(){
        btnIya = v.findViewById(R.id.btnIya) ;
        btnTidak = v.findViewById(R.id.btnTidak) ;
        etPath = v.findViewById(R.id.etPath) ;

        etPath.setText("Internal Storage/Download/");
    }

    public void event(){
        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });

        btnIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((ActivityUtama)getActivity()).permissionStorage()){
                    if (type.equals("lproduk")){
                        lProduk(context.getString(R.string.mnLapProduk)); ;
                    } else if (type.equals("lpelanggan")){
                        lPelanggan(context.getString(R.string.mnLapPelanggan)); ;
                    } else if (type.equals("lpegawai")){
                        lPegawai(context.getString(R.string.mnLapPegawai)); ;
                    } else if (type.equals("ltopup")){
                        lTopup(context.getString(R.string.mnLapTopup)); ;
                    } else if (type.equals("lpenjualan")){
                        lPenjualan(context.getString(R.string.mnLapPenjualan)); ;
                    } else if (type.equals("ldetailpenjualan")){
                        lDetailPenjualan(context.getString(R.string.mnLapDetail)); ;
                    } else if (type.equals("lhutang")){
                        lHutang(context.getString(R.string.mnLapHutang)); ;
                    } else if (type.equals("lbayarhutang")){
                        lBayarHutang(context.getString(R.string.mnLapBayarHutang)); ;
                    }
                    keluar();
                } else {
                    utilitas.toast(getString(R.string.iCekIzin));
                }
            }
        });
    }

    public void lPegawai(String fileName){
        String[] coba =  {"pegawai","alamatpegawai","nohppegawai"} ;
        String[] coba2 = {"Nama Pegawai","Alamat Pegawai","No. Hp"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lTopup(String fileName){
        String[] coba =  {"tgldeposit","deposit","ketdeposit","pelanggan","saldodeposit"} ;
        String[] coba2 = {"Tanggal Deposit","Jumlah Deposit","Keterangan Deposit","Pelanggan","Saldo Pelanggan"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lProduk(String fileName){
        String[] coba =  {"kategori","produk","satuanbesar","hargabesar","satuankecil","hargakecil","stokbesar__stok"} ;
        String[] coba2 = {"Kategori","Nama Produk","Satuan Besar","Harga Satuan Besar","Satuan Kecil","Harga Satuan Kecil","Sisa Stok"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lPelanggan(String fileName){
        String[] coba =  {"pelanggan","alamat","nohp","saldodeposit__float","hutang__float"} ;
        String[] coba2 = {"Nama Pelanggan","Alamat Pelanggan","No. Hp","Total Saldo","Total Hutang"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lPenjualan(String fileName){
        String[] coba =  {"tglorder","pelanggan","pegawai","totalorder__float","bayar__float","kembali__float","ketorder"} ;
        String[] coba2 = {"Tanggal Penjualan","Nama Pelanggan","Nama Pegawai","Total Pesanan","Bayar","Kembali","Metode Pembayaran"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lDetailPenjualan(String fileName){
        String[] coba =  {"tglorder","pelanggan","pegawai","produk","kategori","hargaorder__float","jumlahorder","satuan","ketdetailorder"} ;
        String[] coba2 = {"Tanggal Penjualan","Nama Pelanggan","Nama Pegawai","Nama Produk","Kategori","Harga Produk","Jumlah Penjualan","Satuan","Keterangan"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lHutang(String fileName){
        String[] coba =  {"tglhutang","pelanggan","hutang__float","saldoHutang__float"} ;
        String[] coba2 = {"Tanggal Hutang","Nama Pelanggan","Hutang","Total Hutang"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lBayarHutang(String fileName){
        String[] coba =  {"tglbayar","pelanggan","jumlahbayar__float","saldoHutang__float"} ;
        String[] coba2 = {"Tanggal Bayar","Nama Pelanggan","Jumlah Bayar","Total Hutang"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }


    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit() ;
    }
}
