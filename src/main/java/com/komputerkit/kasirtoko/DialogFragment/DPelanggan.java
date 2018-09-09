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

import com.komputerkit.kasirtoko.Fragment.Master.MPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by msaifa on 20/01/2018.
 */

@SuppressLint("ValidFragment")
public class DPelanggan extends DialogFragment{

    View v ;
    String id ;
    MPelanggan mPelanggan;
    Utilitas utilitas ;
    Database db ;
    boolean tambah ;
    ConstraintLayout wSimpan, wBack ;
    EditText etPelanggan, etAlamat, etTelp ;
    TextView tvTitle ;
    String type = "" ;
    DCartPilih dCartPilih ;
    DPilihPelanggan dPilihPelanggan ;

    public DPelanggan(DPilihPelanggan dPilihPelanggan) {
        this.dPilihPelanggan = dPilihPelanggan;

        utilitas = new Utilitas(dPilihPelanggan.getActivity()) ;
        db = new Database(dPilihPelanggan.getActivity()) ;
        tambah = true ;
        type = "deposit" ;
    }

    @SuppressLint("ValidFragment")
    public DPelanggan(MPelanggan mPelanggan,String id ) {
        this.id = id;
        this.mPelanggan = mPelanggan;

        utilitas = new Utilitas(mPelanggan.getActivity()) ;
        db = new Database(mPelanggan.getActivity()) ;
        tambah = false ;
    }

    @SuppressLint("ValidFragment")
    public DPelanggan(MPelanggan mPelanggan) {
        this.mPelanggan = mPelanggan;

        utilitas = new Utilitas(mPelanggan.getActivity()) ;
        db = new Database(mPelanggan.getActivity()) ;
        tambah = true ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if(!tambah){
            setText();
            tvTitle.setText(getString(R.string.cUbahPelanggan));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_pelanggan,container,false) ;
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

                if (utilitas.isEmpty(v,R.id.etPelanggan) || utilitas.isEmpty(v,R.id.etAlamat) || utilitas.isEmpty(v,R.id.etTelp)){
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
                utilitas.setText(v,R.id.tvPelanggan,utilitas.intToStr(s.toString().length()) + "/20") ;
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
        String sql = "select * from tblpelanggan where idpelanggan='"+id+"'" ;

        etPelanggan.setText(db.getValue(sql,"pelanggan"));
        etAlamat.setText(db.getValue(sql,"alamat"));
        etTelp.setText(db.getValue(sql,"nohp"));
    }

    public void tambah(){
        String pelanggan = etPelanggan.getText().toString() ;
        String alamat = etAlamat.getText().toString() ;
        String telp = etTelp.getText().toString() ;

        String sql = "insert into tblpelanggan (pelanggan,alamat,nohp,saldodeposit) values " +
                "('"+pelanggan+"','"+alamat+"','"+telp+"','0')" ;

        if (db.execution(sql)){
            if (type.equals("")){
                mPelanggan.loadData("");
            } else {
                dPilihPelanggan.loadData("");
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

        String sql = "update tblpelanggan set pelanggan='"+pelanggan+"', alamat='"+alamat+"',nohp='"+telp+"'" +
                " where idpelanggan='"+id+"'" ;

        if (db.execution(sql)){
            mPelanggan.loadData("");
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahFail));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DPelanggan.this).commit() ;
    }

}
