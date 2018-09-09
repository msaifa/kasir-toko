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

import com.komputerkit.kasirtoko.Fragment.Master.MPegawai;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by msaifa on 20/01/2018.
 */

@SuppressLint("ValidFragment")
public class DPegawai extends DialogFragment {

    View v ;
    String id ;
    MPegawai mPegawai;
    Utilitas utilitas ;
    Database db ;
    boolean tambah ;
    ConstraintLayout wSimpan, wBack ;
    EditText etPelanggan, etAlamat, etTelp ;
    TextView tvTitle ;
    DCartPilih dCartPilih ;
    String type = "" ;
    DPilihPegawai dPilihPegawai ;

    public DPegawai(DPilihPegawai dPilihPegawai) {
        this.dPilihPegawai = dPilihPegawai;

        utilitas = new Utilitas(dPilihPegawai.getActivity()) ;
        db = new Database(dPilihPegawai.getActivity()) ;
        tambah = true ;
        type = "penjualan" ;
    }

    @SuppressLint("ValidFragment")
    public DPegawai(MPegawai mPegawai,String id ) {
        this.id = id;
        this.mPegawai = mPegawai;

        utilitas = new Utilitas(mPegawai.getActivity()) ;
        db = new Database(mPegawai.getActivity()) ;
        tambah = false ;
    }

    @SuppressLint("ValidFragment")
    public DPegawai(MPegawai mPegawai) {
        this.mPegawai = mPegawai;

        utilitas = new Utilitas(mPegawai.getActivity()) ;
        db = new Database(mPegawai.getActivity()) ;
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
        return inflater.inflate(R.layout.dialog_pegawai,container,false) ;
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
        etPelanggan = v.findViewById(R.id.etPelanggan) ;
        etAlamat = v.findViewById(R.id.etAlamat) ;
        etTelp = v.findViewById(R.id.etTelp) ;
        tvTitle = v.findViewById(R.id.tvTitle) ;
    }

    public void event(){
        wSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etPelanggan.getText().equals("") || etAlamat.getText().equals("") || etTelp.getText().equals("")){
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
        
        etPelanggan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                utilitas.setText(v,R.id.tvPegawai,utilitas.intToStr(s.toString().length()) + "/20") ;
            }
        });
        
        etAlamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                utilitas.setText(v,R.id.tvAlamat,utilitas.intToStr(s.toString().length()) + "/30") ;
            }
        });
        
        etTelp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                utilitas.setText(v,R.id.tvTelp,utilitas.intToStr(s.toString().length()) + "/15") ;
            }
        });
    }
    public void setText(){
        String sql = "select * from tblpegawai where idpegawai='"+id+"'" ;

        etPelanggan.setText(db.getValue(sql,"pegawai"));
        etAlamat.setText(db.getValue(sql,"alamatpegawai"));
        etTelp.setText(db.getValue(sql,"nohppegawai"));
    }

    public void tambah(){
        String pelanggan = etPelanggan.getText().toString() ;
        String alamat = etAlamat.getText().toString() ;
        String telp = etTelp.getText().toString() ;

        String sql = "insert into tblpegawai (pegawai,alamatpegawai,nohppegawai) values " +
                "('"+pelanggan+"','"+alamat+"','"+telp+"')" ;

        if (db.execution(sql)){
            if (type.equals("")){
                mPegawai.loadData("");
            } else {
                dPilihPegawai.loadData("") ;
            }
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddFail));
        }
    }

    public void ubah(){
        String pelanggan = etPelanggan.getText().toString() ;
        String alamat = etAlamat.getText().toString() ;
        String telp = etTelp.getText().toString() ;

        String sql = "update tblpegawai set pegawai='"+pelanggan+"', alamatpegawai='"+alamat+"',nohppegawai='"+telp+"'" +
                " where idpegawai='"+id+"'" ;

        if (db.execution(sql)){
            mPegawai.loadData("");
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahFail));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DPegawai.this).commit() ;
    }
    
}
