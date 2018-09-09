package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihHutang;
import com.komputerkit.kasirtoko.Model.QHutang;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import static com.komputerkit.kasirtoko.Utilitas.Utilitas.getStrip;
import static com.komputerkit.kasirtoko.Utilitas.Utilitas.numberFormat;

/**
 * Created by msaifa on 06/03/2018.
 */

@SuppressLint("ValidFragment")
public class DBayarHutang extends DialogFragment {

    QHutang qHutang ;
    TPilihHutang fragment ;
    View v ;
    Utilitas utilitas ;
    Database db ;
    CardView btnSimpan ;
    EditText etBayar ;
    ConstraintLayout wBack ;

    @SuppressLint("ValidFragment")
    public DBayarHutang(QHutang qHutang, TPilihHutang fragment) {
        this.qHutang = qHutang;
        this.fragment = fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        setText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_bayar_hutang,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
    }

    public void init(){
        btnSimpan = v.findViewById(R.id.btnSimpan) ;
        etBayar = v.findViewById(R.id.etBayar) ;
        wBack = v.findViewById(R.id.wBack) ;

        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });

        etBayar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etBayar.removeTextChangedListener(this);
                try{
                    String temp = s.toString().replace(".","") ;
                    String nf = numberFormat(temp) ;
                    etBayar.setText(nf) ;
                    etBayar.setSelection(nf.length());
                }catch (Exception e){

                }
                etBayar.addTextChangedListener(this);

                String kembali = utilitas.intToStr(utilitas.strToInt(s.toString().replace(".","")) - utilitas.strToInt(qHutang.getHutang())) ;
                utilitas.setText(v,R.id.tvKembali,utilitas.removeE(kembali)) ;
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (utilitas.isEmpty(v,R.id.etBayar)){
                    utilitas.getSnackBar(v,R.id.wadah,"Harap Masukkan jumlah uang");
                } else {
                    simpan() ;
                }
            }
        });
    }

    public void setText(){

        utilitas.setText(v,R.id.tvTanggal,qHutang.getTglhutang()) ;
        utilitas.setText(v,R.id.tvPelanggan,qHutang.getPelanggan()) ;
        utilitas.setText(v,R.id.tvKembali,"-"+utilitas.removeE(qHutang.getHutang())) ;
        utilitas.setText(v,R.id.tvJumlah,utilitas.removeE(qHutang.getHutang())) ;

    }

    public void simpan(){
        String tgl = utilitas.getText(v,R.id.tvTanggal) ;
        String tglmix = utilitas.getYear(tgl)+ utilitas.getMonth(tgl) + utilitas.getDay(tgl) ;
        String flag ;
        String bayar = etBayar.getText().toString().replace(".","") ;
        String sisa ;
        String jumlahbayar ;

        if (utilitas.strToInt(bayar) < utilitas.strToInt(qHutang.getHutang())){
            flag = "0" ;
            sisa = utilitas.intToStr( utilitas.strToInt(qHutang.getHutang()) - utilitas.strToInt(bayar)) ;
            jumlahbayar = bayar ;
        } else {
            flag = "1" ;
            sisa = "0" ;
            jumlahbayar = qHutang.getHutang() ;
        }

        String q1 = "insert into tblbayarhutang (idhutang,tglbayar,jumlahbayar,saldohutang,tglmix) values (" +
                "'"+qHutang.getIdhutang()+"'," +
                "'"+tgl+"'," +
                "'"+jumlahbayar+"'," +
                "'0'," +
                "'"+tglmix+"'" +
                ")" ;

        String q2 = "update tblhutang set flaghutang='"+flag+"',hutang='"+sisa+"' where idhutang='"+qHutang.getIdhutang()+"'" ;
        String q3 = "update tblpelanggan set hutang=tblpelanggan.hutang - "+jumlahbayar+" where idpelanggan='"+qHutang.getIdpelanggan()+"'" ;

        if (db.execution(q1) && db.execution(q2) && db.execution(q3)){
            utilitas.toast(getString(R.string.iSuccessTrans));
            fragment.load();
            keluar();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailTrans));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit() ;
    }
}
