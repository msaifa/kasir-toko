package com.komputerkit.kasirtoko.Fragment.Transaksi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirtoko.ActivityUtama;
import com.komputerkit.kasirtoko.DialogFragment.DPilihPelanggan;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by msaifa on 13/02/2018.
 */

public class TTopup extends Fragment {

    View v ;
    ConstraintLayout wSimpan,btnTanggal, wPelanggan ;
    public TblPelanggan tblPelanggan ;
    Utilitas utilitas ;
    Database db ;

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;
        showPelanggan() ;
        utilitas.setText(v,R.id.etTanggal,utilitas.getDate(getString(R.string.typeDate))) ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topup,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        wSimpan = v.findViewById(R.id.wSimpan);
        wPelanggan = v.findViewById(R.id.wPelanggan);
        btnTanggal = v.findViewById(R.id.btnTanggal) ;
    }

    public void event(){
        wSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp myApp = new MyApp(utilitas,db) ;
                if (myApp.cekRow("tbldeposit")){
                    ((ActivityUtama)getActivity()).bayar();
                } else {
                    simpan();
                }
            }
        });

        btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilitas.showDateDialog(v,R.id.etTanggal) ;
            }
        });
        utilitas.autoNumber(v,R.id.etJumlah) ;

        wPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPelanggan();
            }
        });
    }

    public void showPelanggan(){
        FragmentManager fm = getFragmentManager() ;
        DPilihPelanggan p = new DPilihPelanggan(this) ;
        p.show(fm,"asdf") ;
    }

    public void setText(){
        utilitas.setText(v,R.id.tvKet,utilitas.removeE(tblPelanggan.getSaldodeposit())) ;
        utilitas.setText(v,R.id.tvPelanggan,tblPelanggan.getPelanggan()) ;
    }

    public void simpan(){
        String deposit = utilitas.getText(v,R.id.etJumlah).replace(".","") ;
        String tgl = utilitas.getText(v,R.id.etTanggal) ;
        String ket = (utilitas.getText(v,R.id.etKeterangan).equals(""))?"-":utilitas.getText(v,R.id.etKeterangan) ;
        String tglmix = utilitas.getYear(tgl)+utilitas.getMonth(tgl)+utilitas.getDay(tgl) ;

        String sql = "insert into tbldeposit (idpelanggan,deposit,tgldeposit,ketdeposit,tglmix) values " +
                "('"+tblPelanggan.getIdpelanggan()+"'," +
                "'"+deposit+"'," +
                "'"+tgl+"'," +
                "'"+ket+"'," +
                "'"+tglmix+"'" +
                ")" ;

        if (db.execution(sql)){
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iSuccessTrans));
            utilitas.setText(v,R.id.etJumlah,"0") ;
            utilitas.setText(v,R.id.etKeterangan,"") ;
            String jum = utilitas.penjumlahan(tblPelanggan.getSaldodeposit(),deposit) ;
            utilitas.setText(v,R.id.tvKet,utilitas.removeE(jum)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailTrans));
        }
    }
}
