package com.komputerkit.kasirtoko.Fragment.Laporan;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirtoko.Adapter.AdapterLapPelanggan;
import com.komputerkit.kasirtoko.Adapter.AdapterLapProduk;
import com.komputerkit.kasirtoko.Adapter.AdapterProduk;
import com.komputerkit.kasirtoko.DialogFragment.DDialogExport;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 13/02/2018.
 */

public class LProduk extends Fragment {

    View v ;
    RecyclerView recyclerView ;
    EditText etCari ;
    Utilitas utilitas ;
    Database db ;
    ConstraintLayout wTanggal ;
    String q ;

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
        return inflater.inflate(R.layout.fragment_laporan,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        recyclerView = v.findViewById(R.id.recLaporan) ;
        etCari = v.findViewById(R.id.etCari) ;
        wTanggal = v.findViewById(R.id.wTanggal) ;

        wTanggal.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                loadData(s.toString());
            }
        });

        ConstraintLayout wExport = v.findViewById(R.id.wExport) ;
        wExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDialogExport dialogExport = new DDialogExport(getActivity(),"lproduk",q) ;

                FragmentManager fm = getFragmentManager() ;
                dialogExport.show(fm,"UbahAkun");
            }
        });
    }

    public void loadData(String cari){
        ArrayList<QProduk> list = new ArrayList() ;
        AdapterLapProduk adapter = new AdapterLapProduk(list) ;
        recyclerView.setAdapter(adapter);
        q = "select * from qproduk where produk like '%"+ etCari.getText().toString() +"%' or " +
                "kategori like '%"+ etCari.getText().toString() +"%' order by produk asc" ;

        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while (c.moveToNext()){
                list.add(new QProduk(
                        db.getString(c,"idproduk"),
                        db.getString(c,"kategori"),
                        db.getString(c,"produk"),
                        db.getString(c,"batchnumber"),
                        db.getString(c,"satuanbesar"),
                        db.getString(c,"hargabesar"),
                        db.getString(c,"satuankecil"),
                        db.getString(c,"hargakecil"),
                        db.getString(c,"stokbesar"),
                        db.getString(c,"flagproduk"),
                        db.getString(c,"ketkategori"),
                        "",
                        0,0,1
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
        utilitas.setText(v,R.id.tvSaldo,utilitas.intToStr(db.getCount(q)) + " Data") ;
    }

}
