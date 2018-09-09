package com.komputerkit.kasirtoko.Fragment.Master;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;

import com.komputerkit.kasirtoko.ActivityUtama;
import com.komputerkit.kasirtoko.Adapter.AdapterPegawai;
import com.komputerkit.kasirtoko.DialogFragment.DPegawai;
import com.komputerkit.kasirtoko.Model.TblPegawai;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 17/01/2018.
 */

public class MPegawai extends Fragment {

    View v ;
    Button FABTambah ;
    RecyclerView recyclerView ;
    EditText etCari ;
    Utilitas utilitas ;
    Database db ;

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        loadData("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        FABTambah = v.findViewById(R.id.FABTambah) ;
        recyclerView = v.findViewById(R.id.recMaster) ;
        etCari = v.findViewById(R.id.etCari) ;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void event(){
        FABTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp myApp = new MyApp(utilitas,db) ;

                if (myApp.cekRow("tblpegawai")){
                    ((ActivityUtama)getActivity()).bayar();
                } else {
                    FragmentManager fm = getFragmentManager() ;
                    DPegawai dProduk = new DPegawai(MPegawai.this) ;
                    dProduk.show(fm,"TambahData");
                }
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
    }

    public void loadData(String cari){
        ArrayList<TblPegawai> array = new ArrayList() ;
        AdapterPegawai adapter = new AdapterPegawai(this,array) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblpegawai where pegawai like '%"+cari+"%' order by pegawai asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new TblPegawai(
                        db.getString(c,"idpegawai"),
                        db.getString(c,"pegawai"),
                        db.getString(c,"alamatpegawai"),
                        db.getString(c,"nohppegawai")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void ubah(String id){
        FragmentManager fm = getFragmentManager() ;
        DPegawai dProduk = new DPegawai(this,id) ;
        dProduk.show(fm,"TambahData");
    }

    public void hapus(String id){
        final String sql = "select * from tblorder where idpegawai='"+id+"'" ;
        final String q = "delete from tblpegawai where idpegawai='"+id+"'" ;

        utilitas.showDialog(getString(R.string.iConfirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (db.getCount(sql) > 0){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailHapusTrans));
                } else if (db.execution(q)){
                    loadData("");
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusSuccess));
                } else {
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusGagal));
                }
            }
        });
    }

}
