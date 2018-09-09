package com.komputerkit.kasirtoko.Fragment.Transaksi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Adapter.AdapterPilihBarang;
import com.komputerkit.kasirtoko.Adapter.AdapterTambahStok;
import com.komputerkit.kasirtoko.DialogFragment.DPenjualan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 06/03/2018.
 */

public class TTambahStok extends Fragment {

    View v ;
    Database db;
    Utilitas utilitas ;
    EditText etCari ;
    TextView tvJumlah ;
    RecyclerView recyclerView ;
    Button FAB ;
    ArrayList<QProduk> list = new ArrayList()  ;
    AdapterTambahStok adapter ;
    ConstraintLayout btnSimpan ;
    int jumlah ;

    @Override
    public void onStart() {
        super.onStart();

        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        load();
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
        etCari = v.findViewById(R.id.etCari) ;
        recyclerView = v.findViewById(R.id.recMaster) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );
        FAB = v.findViewById(R.id.FABTambah) ;
        btnSimpan = v.findViewById(R.id.btnSimpan) ;
        tvJumlah = v.findViewById(R.id.tvJumlah) ;
        btnSimpan.setVisibility(View.VISIBLE);

        tvJumlah.setVisibility(View.GONE);
        ConstraintLayout wIcon = v.findViewById(R.id.wIcon) ;
        wIcon.setVisibility(View.GONE) ;
        TextView tSel = v.findViewById(R.id.tSelanjutnya) ;
        tSel.setVisibility(View.GONE);
        TextView tTambah = v.findViewById(R.id.tTambahStok) ;
        tTambah.setVisibility(View.VISIBLE);
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
                search(s.toString()); ;
            }
        });

        FAB.setVisibility(View.GONE);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlah > 0){
                    utilitas.showDialog("Stok akan ditambah sesuai yang dipilih. Apakah anda yakin ? ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            simpan();
                        }
                    });
                } else {
                    utilitas.toast("Tidak ada produk yang dipilih");
                }
            }
        });
    }

    public void load(){
        list.clear() ;
        adapter = new AdapterTambahStok(this,list) ;
        recyclerView.setAdapter(adapter);
        String q = "select * from qproduk where produk like '%"+ etCari.getText().toString() +"%' or " +
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
                        0,1,1
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void load2(){

        jumlah = 0;
        for (int i = 0 ; i < list.size() ; i++){
            if (list.get(i).getSatuan() == 0){
                jumlah += utilitas.strToInt(list.get(i).getHargakecil()) * list.get(i).getJumlah() ;
            } else {
                jumlah += utilitas.strToInt(list.get(i).getHargabesar()) * list.get(i).getJumlah() ;
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void search(String value){

        for (int i = 0 ; i < list.size() ; i++){
            if (list.get(i).getProduk().toLowerCase().contains(value.toLowerCase())){
                list.get(i).setFlagAktif(1);
            } else if (value.equals("")){
                list.get(i).setFlagAktif(1);
            }else {
                list.get(i).setFlagAktif(0);
            }
        }
        adapter.notifyDataSetChanged() ;

    }

    public void plus(int position){
        int jum = list.get(position).getJumlah() + 1 ;
        list.get(position).setJumlah(jum);

        load2();
    }

    public void minus(int position){
        int jum = list.get(position).getJumlah() - 1 ;
        list.get(position).setJumlah(jum);

        load2();
    }

    public void change(int position, int value){
        list.get(position).setSatuan(value);
    }

    public void setKeterangan(int position, String value){
        list.get(position).setKetorder(value) ;
    }

    public void simpan(){
        String sql = "" ;

        for(int i = 0 ; i < list.size() ; i++){
            if (list.get(i).getJumlah() > 0){
                if (list.get(i).getSatuan() == 0){
                    String[] stok = list.get(i).getStokbesar().split("sisa") ;
                    int sisa = utilitas.strToInt(stok[1]) + list.get(i).getJumlah() ;
                    String baru = stok[0]+"sisa"+utilitas.intToStr(sisa) ;
                    sql = "update tblproduk set stokbesar='"+baru+"' where idproduk='"+list.get(i).getIdproduk()+"'" ;
                } else {
                    String[] stok = list.get(i).getStokbesar().split("sisa") ;
                    int sisa = utilitas.strToInt(stok[0]) + list.get(i).getJumlah() ;
                    String baru = utilitas.intToStr(sisa)+"sisa"+stok[1] ;
                    sql = "update tblproduk set stokbesar='"+baru+"' where idproduk='"+list.get(i).getIdproduk()+"'" ;
                }
                db.execution(sql) ;
            }
        }

        list.clear();
        load();
        utilitas.toast(getString(R.string.iSuccessTrans));

    }

    public void ubah(int position, int i) {
        list.get(position).setJumlah(i) ;
    }
}
