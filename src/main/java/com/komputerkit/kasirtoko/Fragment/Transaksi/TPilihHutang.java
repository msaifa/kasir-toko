package com.komputerkit.kasirtoko.Fragment.Transaksi;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirtoko.Adapter.AdapterPilihHutang;
import com.komputerkit.kasirtoko.DialogFragment.DBayarHutang;
import com.komputerkit.kasirtoko.Model.QHutang;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 06/03/2018.
 */

public class TPilihHutang extends DialogFragment {

    View v ;
    RecyclerView recyclerView ;
    AdapterPilihHutang adapter ;
    Database db ;
    Utilitas utilitas ;
    EditText etCari ;

    @Override
    public void onStart() {
        super.onStart();

        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        load() ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pilih_hutang,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event() ;
    }

    public void init(){
        recyclerView = v.findViewById(R.id.recHutang) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;
        etCari = v.findViewById(R.id.etCari) ;
    }

    public void event(){
        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                load() ;
            }
        });
    }

    public void load(){
        ArrayList<QHutang> list = new ArrayList() ;
        String sql = "select * from qhutang where pelanggan like '%"+etCari.getText().toString()+"%' and flaghutang='0' order by tglhutang desc" ;
        adapter = new AdapterPilihHutang(this,list) ;
        recyclerView.setAdapter(adapter) ;

        Cursor c = db.select(sql) ;
        if (db.getCount(sql) > 0){
            while(c.moveToNext()){
                list.add(new QHutang(
                        db.getString(c,"idhutang"),
                        db.getString(c,"hutang"),
                        db.getString(c,"tglhutang"),
                        db.getString(c,"flaghutang"),
                        db.getString(c,"idpelanggan"),
                        db.getString(c,"pelanggan"),
                        db.getString(c,"alamat"),
                        db.getString(c,"nohp"),
                        db.getString(c,"saldodeposit"),
                        db.getString(c,"saldoHutang")
                )) ;
            }
            adapter.notifyDataSetChanged() ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void pilih(QHutang qHutang){

        DBayarHutang bayar = new DBayarHutang(qHutang,this) ;
        FragmentManager fm = getFragmentManager() ;
        bayar.show(fm,"adsf") ;

    }
}
