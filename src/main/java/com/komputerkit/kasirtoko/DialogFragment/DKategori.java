package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MKategori;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by msaifa on 18/02/2018.
 */

@SuppressLint("ValidFragment")
public class DKategori extends DialogFragment {

    View v ;
    String id ;
    MKategori MKategori;
    Utilitas utilitas ;
    Database db ;
    boolean tambah ;
    ConstraintLayout wSimpan, wBack ;
    TextView tvTitle ;
    DCartPilih dCartPilih ;
    String type = "" ;

    @SuppressLint("ValidFragment")
    public DKategori(MKategori MKategori,String id ) {
        this.id = id;
        this.MKategori = MKategori;

        utilitas = new Utilitas(MKategori.getActivity()) ;
        db = new Database(MKategori.getActivity()) ;
        tambah = false ;
    }

    @SuppressLint("ValidFragment")
    public DKategori(MKategori MKategori) {
        this.MKategori = MKategori;

        utilitas = new Utilitas(MKategori.getActivity()) ;
        db = new Database(MKategori.getActivity()) ;
        tambah = true ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if(!tambah){
            setText();
            tvTitle.setText(getString(R.string.cUbahPegawai));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_kategori,container,false) ;
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
    }

    public void event(){
        wSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (utilitas.isEmpty(v,R.id.etKategori) || utilitas.isEmpty(v,R.id.etKeterangan)){
                    utilitas.getSnackBar(v,R.id.wadah,"Harap isi dengan benar.");
                } else {
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

        utilitas.sumChar(v,R.id.etKategori,R.id.tvKategori,"20");
        utilitas.sumChar(v,R.id.etKeterangan,R.id.tvKeterangan,"30");
    }
    public void setText(){
        String sql = "select * from tblkategori where kategori='"+id+"'" ;

        utilitas.setText(v,R.id.etKategori,db.getValue(sql,"kategori"));
        utilitas.setText(v,R.id.etKeterangan,db.getValue(sql,"ketkategori"));
    }

    public void tambah(){
        String pelanggan = utilitas.getText(v,R.id.etKategori) ;
        String alamat = utilitas.getText(v,R.id.etKeterangan) ;

        String sql = "insert into tblkategori (kategori,ketkategori) values " +
                "('"+pelanggan+"','"+alamat+"')" ;

        if (db.execution(sql)){
            if (type.equals("")){
                MKategori.loadData("");
            } else {
//                dCartPilih.loadData2("");
            }
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddFail));
        }
    }

    public void ubah(){
        String pelanggan = utilitas.getText(v,R.id.etKategori) ;
        String alamat = utilitas.getText(v,R.id.etKeterangan) ;

        String sql = "update tblkategori set kategori='"+pelanggan+"', ketkategori='"+alamat+"' " +
                " where kategori='"+id+"'" ;
        String sql1 = "update tblproduk set kategori='"+pelanggan+"' where kategori='"+id+"'" ;

        if (db.execution(sql) && db.execution(sql1)){
            MKategori.loadData("");
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahFail));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DKategori.this).commit() ;
    }

}
