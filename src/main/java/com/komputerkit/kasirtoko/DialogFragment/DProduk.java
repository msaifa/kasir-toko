package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MProduk;
import com.komputerkit.kasirtoko.Model.TblKategori;
import com.komputerkit.kasirtoko.Model.TblSatuan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 25/02/2018.
 */

@SuppressLint("ValidFragment")
public class DProduk extends DialogFragment {

    View v ;
    String id ;
    MProduk fragment;
    DCartProduk fragmentBaru;
    Utilitas utilitas ;
    Database db ;
    boolean tambah ;
    ConstraintLayout wSimpan, wBack ;
    TextView tvTitle ;
    DCartPilih dCartPilih ;
    String type = "" ;
    ArrayList tblKategori = new ArrayList<>() ;
    ArrayList<TblSatuan> tblSatuans = new ArrayList<>() ;
    ArrayList listSatuan = new ArrayList() ;
    Spinner spKategori,spSatuan ;

    @SuppressLint("ValidFragment")
    public DProduk(MProduk fragment,String id ) {
        this.id = id;
        this.fragment = fragment;

        utilitas = new Utilitas(fragment.getActivity()) ;
        db = new Database(fragment.getActivity()) ;
        tambah = false ;
    }

    @SuppressLint("ValidFragment")
    public DProduk(MProduk fragment) {
        this.fragment = fragment;

        utilitas = new Utilitas(fragment.getActivity()) ;
        db = new Database(fragment.getActivity()) ;
        tambah = true ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        loadkategori();
        loadSatuan();

        if(!tambah){
            setText();
            tvTitle.setText(getString(R.string.cUbahSatuan));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_produk,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;
        init() ;
        event() ;
    }

    public void init(){
        wSimpan = v.findViewById(R.id.wSimpan) ;
        wBack = v.findViewById(R.id.wBack) ;
        tvTitle = v.findViewById(R.id.tvTitle) ;
        spKategori = v.findViewById(R.id.spKategori) ;
        spSatuan = v.findViewById(R.id.spSatuan) ;
    }

    public void event(){
        wSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (utilitas.isEmpty(v,R.id.etProduk) || utilitas.isEmpty(v,R.id.etSatuanBesar) || utilitas.isEmpty(v,R.id.etSatuanKecil)){
                    utilitas.getSnackBar(v,R.id.wadah,"Harap isi dengan benar.");
                } else if (listSatuan.size() == 0){
                    utilitas.getSnackBar(v,R.id.wadah,"Harap tambah Satuan di menu Master -> Satuan.");
                }else {
                    if (tambah){
                        tambah() ;
                    } else {
                        ubah() ;
                    }
                }
            }
        });

        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });

        utilitas.sumChar(v,R.id.etProduk,R.id.tvProduk,"20");
        utilitas.sumChar(v,R.id.etSatuanBesar,R.id.tvSatuanBesar,"12");
        utilitas.sumChar(v,R.id.etSatuanKecil,R.id.tvSatuanKecil,"12");
        utilitas.autoNumber(v,R.id.etSatuanKecil) ;
        utilitas.autoNumber(v,R.id.etSatuanBesar) ;
    }
    public void setText(){
        String sql = "select * from tblproduk where idproduk='"+id+"'" ;

        utilitas.setText(v,R.id.etSatuanBesar,db.getValue(sql,"hargabesar"));
        utilitas.setText(v,R.id.etSatuanKecil,db.getValue(sql,"hargakecil"));
        utilitas.setText(v,R.id.etProduk,db.getValue(sql,"produk"));
    }

    public void loadkategori(){

        String q = "select * from tblkategori" ;
        Cursor c = db.select(q) ;
        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                tblKategori.add(db.getString(c,"kategori")) ;
            }
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,tblKategori) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategori.setAdapter(adapter);
    }

    public void loadSatuan(){

        String q = "select * from tblsatuan" ;
        Cursor c = db.select(q) ;
        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                tblSatuans.add(new TblSatuan(db.getString(c,"satuanbesar"),db.getString(c,"nilaibesar"),db.getString(c,"satuankecil"),db.getString(c,"nilaikecil"))) ;
                listSatuan.add("1 " + db.getString(c,"satuanbesar") + " = " + db.getString(c,"nilaikecil") + " " + db.getString(c,"satuankecil")) ;
            }
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,listSatuan) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSatuan.setAdapter(adapter);
    }

    public void tambah(){

        String kategori = tblKategori.get(spKategori.getSelectedItemPosition()).toString() ;
        String satuanBesar = tblSatuans.get(spSatuan.getSelectedItemPosition()).getSatuanbesar() ;
        String satuanKecil = tblSatuans.get(spSatuan.getSelectedItemPosition()).getSatuankecil() ;
        String sProduk = utilitas.getText(v,R.id.etProduk) ;
        String sBesar = utilitas.getText(v,R.id.etSatuanBesar).replace(".","") ;
        String sKecil = utilitas.getText(v,R.id.etSatuanKecil).replace(".","") ;

        String sql = "insert into tblproduk (kategori,produk,satuanbesar,hargabesar,satuankecil,hargakecil,stokbesar,flagproduk) values (" +
                "'"+kategori+"'," +
                "'"+sProduk+"'," +
                "'"+satuanBesar+"'," +
                "'"+sBesar+"'," +
                "'"+satuanKecil+"'," +
                "'"+sKecil+"'," +
                "'0sisa0'," +
                "'1'" +
                ")" ;

        if (db.execution(sql)){
            if (type.equals("")){
                fragment.load();
            } else {
//                dCartPilih.loadData2("");
            }
            keluar();// 0838 4604 7220
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddFail));
        }
    }

    public void ubah(){
        String kategori = tblKategori.get(spKategori.getSelectedItemPosition()).toString() ;
        String satuanBesar = tblSatuans.get(spSatuan.getSelectedItemPosition()).getSatuanbesar() ;
        String satuanKecil = tblSatuans.get(spSatuan.getSelectedItemPosition()).getSatuankecil() ;
        String sProduk = utilitas.getText(v,R.id.etProduk) ;
        String sBesar = utilitas.getText(v,R.id.etSatuanBesar).replace(".","") ;
        String sKecil = utilitas.getText(v,R.id.etSatuanKecil).replace(".","") ;

        String sql = "update tblproduk set produk=='"+sProduk+"', kategori='"+kategori+"',satuanbesar='"+satuanBesar+"',satuankecil='"+satuanKecil+"'," +
                " hargabesar='"+sBesar+"',hargakecil='"+sKecil+"'" +
                " where idproduk='"+id+"'" ;

        if (db.execution(sql)){
            fragment.load();
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahFail));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit() ;
    }

}
