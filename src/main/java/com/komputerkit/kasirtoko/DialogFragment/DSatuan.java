package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MSatuan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by msaifa on 25/02/2018.
 */

@SuppressLint("ValidFragment")
public class DSatuan extends DialogFragment {

    View v ;
    String id ;
    MSatuan fragment;
    Utilitas utilitas ;
    Database db ;
    boolean tambah ;
    ConstraintLayout wSimpan, wBack ;
    TextView tvTitle ;
    DCartPilih dCartPilih ;
    String type = "" ;

    @SuppressLint("ValidFragment")
    public DSatuan(MSatuan fragment,String id ) {
        this.id = id;
        this.fragment = fragment;

        utilitas = new Utilitas(fragment.getActivity()) ;
        db = new Database(fragment.getActivity()) ;
        tambah = false ;
    }

    @SuppressLint("ValidFragment")
    public DSatuan(MSatuan fragment) {
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

        if(!tambah){
            setText();
            tvTitle.setText(getString(R.string.cUbahSatuan));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_satuan,container,false) ;
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

                if (utilitas.isEmpty(v,R.id.etNilaikecil) || utilitas.isEmpty(v,R.id.etSatuanBesar) || utilitas.isEmpty(v,R.id.etSatuanKecil)){
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

        utilitas.sumChar(v,R.id.etSatuanBesar,R.id.tvSatuanBesar,"20");
        utilitas.sumChar(v,R.id.etSatuanKecil,R.id.tvSatuanKecil,"20");
        utilitas.sumChar(v,R.id.etNilaikecil,R.id.tvNilaiKecil,"10");
    }
    public void setText(){
        String sql = "select * from tblsatuan where satuanbesar='"+id+"'" ;

        EditText editText = v.findViewById(R.id.etSatuanBesar) ;
        editText.setEnabled(false);

        utilitas.setText(v,R.id.etSatuanBesar,db.getValue(sql,"satuanbesar"));
        utilitas.setText(v,R.id.etSatuanKecil,db.getValue(sql,"satuankecil"));
        utilitas.setText(v,R.id.etNilaikecil,db.getValue(sql,"nilaikecil"));
    }

    public void tambah(){
        String sBesar = utilitas.getText(v,R.id.etSatuanBesar) ;
        String sKecil = utilitas.getText(v,R.id.etSatuanKecil) ;
        String nKecil = utilitas.getText(v,R.id.etNilaikecil) ;

        String sql = "insert into tblsatuan values " +
                "('"+sBesar+"','1','"+sKecil+"','"+nKecil+"')" ;

        if (db.execution(sql)){
            if (type.equals("")){
                fragment.load();
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
        String sKecil = utilitas.getText(v,R.id.etSatuanKecil) ;
        String nKecil = utilitas.getText(v,R.id.etNilaikecil) ;

        String sql = "update tblsatuan set satuankecil='"+sKecil+"', nilaikecil='"+nKecil+"' " +
                " where satuanbesar='"+id+"'" ;

        if (db.execution(sql)){
            fragment.load();
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahFail));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DSatuan.this).commit() ;
    }
    
}
