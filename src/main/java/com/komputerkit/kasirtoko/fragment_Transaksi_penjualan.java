package com.komputerkit.kasirtoko;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Adapter.AdapterOptionProduk;
import com.komputerkit.kasirtoko.Adapter.AdapterPilihBarang;
import com.komputerkit.kasirtoko.Adapter.AdapterTransaksiPenjualan;
import com.komputerkit.kasirtoko.DialogFragment.DCartProduk;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihPenjualan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.Model.TblSatuan;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;
import com.komputerkit.kasirtoko.Utilitas.MyApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.komputerkit.kasirtoko.R.id.etSatuanBesar;
import static com.komputerkit.kasirtoko.R.id.tvTotal;

/**
 * Created by SAIF on 23/12/2017.
 */

public class fragment_Transaksi_penjualan extends Fragment {
    View v ;
    ArrayList<QProduk> arrayList = new ArrayList<>();
    EditText etFaktur,etNamaPelanggan,etNamaBarang,etHargaProduk,etJumlah ;
    TextView btnSimpan, btnSelesai, total ;
    ImageView cariBarang, cariPelanggan, btnPlus, btnMinus, cariTanggal;
    ConstraintLayout wBack;
    Database db ;
    QProduk qProduk;
    TblPelanggan tblPelanggan;
    Utilitas utilitas ;
    RecyclerView recyclerView;
    String faktur, totalbayar;
    public Spinner spSatuan;
    int satuan;
    TPilihPenjualan fragment;

    ArrayList<TblSatuan> tblSatuans = new ArrayList<>() ;
    ArrayList listSatuan = new ArrayList() ;

    ArrayList<QProduk> data = new ArrayList<>();
    String totalBayar = "0" ;
    ArrayList<QProduk> ayam;


    @Override
    public void onStart() {
        super.onStart();

        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        loadData();
        setText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaksi_penjualan,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event();
    }

    public void init(){
        etFaktur = v.findViewById(R.id.etFaktur) ;
        etNamaPelanggan = v.findViewById(R.id.etNamaPelanggan) ;
        etNamaBarang = v.findViewById(R.id.etNamaBarang) ;
        etHargaProduk = v.findViewById(R.id.etHargaProduk) ;
        etJumlah = v.findViewById(R.id.etJumlah) ;
        btnSimpan = v.findViewById(R.id.tvSimpan) ;
        btnSelesai = v.findViewById(R.id.tvBayar) ;
        btnPlus = v.findViewById(R.id.imgPlus);
        btnMinus = v.findViewById(R.id.imgMin);
        total = v.findViewById(tvTotal);
        cariBarang = v.findViewById(R.id.cariBarang);
        cariTanggal = v.findViewById(R.id.cariTanggal);
        spSatuan = v.findViewById(R.id.spSatuan);
        wBack = v.findViewById(R.id.wBack);

        recyclerView = v.findViewById(R.id.recPenjualan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        etFaktur.setFocusable(false);
    }

    public void loadSatuan(){
        String q = "select * from tblproduk" ;
        Cursor c = db.select(q) ;
        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                data.add(new QProduk( db.getString(c,"idproduk"),
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
                        "",0,0,1));
                listSatuan.add(db.getString(c,"satuanbesar")) ;
                listSatuan.add(db.getString(c,"satuankecil")) ;

            }
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,listSatuan) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSatuan.setAdapter(adapter);
    }

    public void event(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();

            }
        });

//        wBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                utilitas.toast("terhapus");
//            }
//        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bayar();
            }
        });

       spSatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                satuan = position ;
                ubahMetode();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        cariBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DCartProduk dCartProduk = new DCartProduk(fragment_Transaksi_penjualan.this,"produk") ;
                FragmentManager fm = getFragmentManager() ;
                dCartProduk.show(fm,"coba");
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jumlah = utilitas.getText(v,R.id.etJumlah) ;
                int jum = utilitas.strToInt(jumlah) ;
                jum++ ;
                utilitas.setText(v,R.id.etJumlah,utilitas.intToStr(jum));

                hitung();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jumlah = utilitas.getText(v,R.id.etJumlah) ;
                int jum = utilitas.strToInt(jumlah) ;
                if (jum > 1){
                    jum--;
                    utilitas.setText(v, R.id.etJumlah, utilitas.intToStr(jum));

                    hitung();
                }
            }
        });
    }

    private void ubahMetode() {
//        etHargaProduk.setEnabled(false); // etHargaProduk Tidak boleh mati
//        String satuans = utilitas.getString(satuan);
        if (qProduk != null){
            qProduk.setSatuan(satuan) ;
            if (satuan == 0){
                utilitas.setText(v,R.id.etHargaProduk,qProduk.getHargabesar());
//                utilitas.setText(v, tvTotal,"Rp. " + utilitas.removeE(qProduk.getHargabesar()));
            } else if (satuan == 1){
                utilitas.setText(v,R.id.etHargaProduk,qProduk.getHargakecil());
//                utilitas.setText(v, tvTotal,"Rp. " + utilitas.removeE(qProduk.getHargakecil()));
            }
        }
    }

    public void loadData() {
        ArrayList<QProduk> array = new ArrayList() ;

        String q = "select * from qproduk order by produk asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new QProduk(
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
                        "",0,0,1
                )) ;
            }
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void loadListDua() {
        ArrayList<QProduk> data = new ArrayList<>();

        AdapterTransaksiPenjualan adapter = new AdapterTransaksiPenjualan(this, data);
        recyclerView.setAdapter(adapter);

        for(int i=0; i<arrayList.size(); i++) {
            double jumlah = Double.parseDouble(String.valueOf(arrayList.get(i).getJumlah()));
            if (jumlah > 0) {
                data.add(arrayList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void Bayar(){
        if(totalBayar == "0") {
            utilitas.toast("Mohon masukkan data dengan benar");
        } else{
            ((ActivityUtama) getActivity()).pencucian(arrayList);
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit() ;
    }

    public void simpan() {
        String faktur = etFaktur.getText().toString();
        String produk = etNamaBarang.getText().toString();
        String harga = etHargaProduk.getText().toString();
        String jumlah = etJumlah.getText().toString();

        if(TextUtils.isEmpty(faktur) || TextUtils.isEmpty(produk) || TextUtils.isEmpty(harga) || utilitas.strToDouble(jumlah)==0){
            utilitas.toast("Mohon masukkan dengan Benar");
        } else {
//            int hargabesar = Integer.parseInt(qProduk.getHargabesar());
//            int hargakecil = Integer.parseInt(qProduk.getHargakecil());
                if(satuan == 0){
                    qProduk.setHargabesar(harga);
                }else{
                    qProduk.setHargakecil(harga);
                }
            qProduk.setJumlah(Integer.parseInt(String.valueOf(jumlah)));
            arrayList.add(qProduk);
            loadListDua();

            utilitas.setText(v,R.id.etNamaBarang,"");
            utilitas.setText(v,R.id.etHargaProduk,"");
            utilitas.setText(v,R.id.etJumlah,"");
            utilitas.setText(v, tvTotal,"");
        }
        hitungTotal();
        loadData();
    }

    public void hapus(final int position){
        utilitas.showDialog(getString(R.string.iConfirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayList.remove(position);
                loadListDua();
                hitungTotal();
            }
        });
    }

    public void hitung(){
        String jumlahbarang = utilitas.getText(v,R.id.etJumlah) ;
        String harga = utilitas.getText(v,R.id.etHargaProduk) ;

        if (!TextUtils.isEmpty(harga)){
            double total = utilitas.strToDouble(totalbayar) + (utilitas.strToDouble(jumlahbarang) * utilitas.strToDouble(harga)) ;
            String hasil = utilitas.doubleToStr(total) ;
            utilitas.setText(v, tvTotal,"Rp. " + utilitas.removeE(hasil)) ;
        }
    }

    public void setText(){
        MyApp myApp = new MyApp(utilitas,db) ;
        faktur = myApp.getFaktur() ;
        utilitas.setText(v,R.id.etFaktur,faktur);

    }

    public void setProduk(QProduk tblproduk){

        this.qProduk = tblproduk;
        utilitas.setText(v,R.id.etNamaBarang,qProduk.getProduk());
        utilitas.setText(v,R.id.etJumlah,"1");

        String hasil = qProduk.getHargakecil();
        String idpro = tblproduk.getIdproduk();

        String q = "select * from tblproduk" ;

        if(idpro == tblproduk.getIdproduk().toString()){
            ArrayList array = new ArrayList();
            array.add(tblproduk.getSatuanbesar());
            array.add(tblproduk.getSatuankecil());
            ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,array) ;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSatuan.setAdapter(adapter);

//            String pilih = spSatuan.getSelectedItem().toString() ;
//            if(pilih == tblproduk.getSatuankecil()){
//                utilitas.setText(v,R.id.etHargaProduk,tblproduk.getHargakecil());
//                utilitas.setText(v,R.id.tvTotal,qProduk.getHargakecil());
//                utilitas.setText(v, tvTotal,"Rp. " + utilitas.removeE(tblproduk.getHargakecil()));
//            }else if(pilih == tblproduk.getSatuanbesar()){
//                utilitas.setText(v,R.id.etHargaProduk,tblproduk.getHargabesar());
//                utilitas.setText(v, tvTotal,"Rp. " + utilitas.removeE(tblproduk.getHargabesar()));
//            }

        }
    }

    public void setProduk(TblPelanggan tblPelanggan) {
        this.tblPelanggan = tblPelanggan;
        utilitas.setText(v,R.id.etNamaPelanggan,tblPelanggan.getPelanggan());
    }

    public void hitungTotal() {
        double total = 0 ;
        for (int i = 0 ; i < arrayList.size() ; i++){
//            total += utilitas.strToDouble(arrayList.get(i).getHargabesar()) *utilitas.strToDouble(String.valueOf(arrayList.get(i).getJumlah())) ;
            if(arrayList.get(i).getSatuan() == 0){
                total += utilitas.strToDouble(arrayList.get(i).getHargabesar()) *utilitas.strToDouble(String.valueOf(arrayList.get(i).getJumlah())) ;
            }else{
                total += utilitas.strToDouble(arrayList.get(i).getHargakecil()) *utilitas.strToDouble(String.valueOf(arrayList.get(i).getJumlah())) ;
            }


            if(!String.valueOf(arrayList.get(i).getJumlah()).equals("0")){
                data.add(arrayList.get(i)) ;
            }
        }
        totalBayar = utilitas.removeE(total) ;
        utilitas.setText(v, tvTotal,"Rp. "+totalBayar) ;
    }

}
