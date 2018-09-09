package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Adapter.AdapterPenjualan;
import com.komputerkit.kasirtoko.Fragment.Master.MSatuan;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihPenjualan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Model.TblPegawai;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.TPembayaran;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

import static com.komputerkit.kasirtoko.Utilitas.Utilitas.numberFormat;

/**
 * Created by msaifa on 03/03/2018.
 */

@SuppressLint("ValidFragment")
public class DPenjualan extends DialogFragment {

    View v ;
    Utilitas utilitas ;
    Database db ;
    ConstraintLayout wSimpan, wBack,wPelanggan,wPegawai ;
    Spinner spMetode ;
    ArrayList<QProduk> list ;
    TPilihPenjualan tPilihPenjualan ;
    TPembayaran tPembayaran ;
    EditText etBayar ;
    RecyclerView recyclerView ;
    CardView btnSelesai ;
    ArrayList<QProduk> temp ;
    String faktur ;
    TblPelanggan tblPelanggan ;
    TblPegawai tblPegawai ;
    int metode,total ;

    @SuppressLint("ValidFragment")
    public DPenjualan(TPilihPenjualan tPilihPenjualan, ArrayList<QProduk> list) {
        this.list = list ;
        this.tPilihPenjualan = tPilihPenjualan ;
    }

    @SuppressLint("ValidFragment")
    public DPenjualan(TPembayaran tPembayaran, ArrayList<QProduk> list) {
        this.list = list ;
        this.tPembayaran = tPembayaran ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        utilitas = new Utilitas(tPilihPenjualan.getActivity()) ;
        db = new Database(tPilihPenjualan.getActivity()) ;
//        load() ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_penjualan,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event() ;
    }

    public void init(){
        wBack = v.findViewById(R.id.wBack) ;
        wPegawai = v.findViewById(R.id.wPegawai) ;
        wPelanggan = v.findViewById(R.id.wPelanggan) ;
        recyclerView = v.findViewById(R.id.recProduk) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(tPilihPenjualan.getActivity()));
        btnSelesai = v.findViewById(R.id.btnSelesai) ;
        spMetode = v.findViewById(R.id.spMetode) ;
        etBayar = v.findViewById(R.id.etBayar) ;
    }

    public void event(){
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

                if (metode == 0){
                    int kembali = utilitas.strToInt(s.toString().replace(".","")) - total ;
                    utilitas.setText(v,R.id.tvKembali,utilitas.removeE(utilitas.intToStr(kembali))) ;
                }
            }
        });


        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });
//        btnSelesai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(tblPegawai == null){
//                    utilitas.getSnackBar(v,R.id.wadah,"Silahkan pilih pegawai");
//                } else if (tblPelanggan == null){
//                    utilitas.getSnackBar(v,R.id.wadah,"Silahkan pilih pelanggan");
//                } else {
//                    simpan();
//                }
//            }
//        });

        wPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DPilihPegawai dPilihPegawai = new DPilihPegawai(DPenjualan.this) ;
                FragmentManager fm = getFragmentManager() ;
                dPilihPegawai .show(fm,"asdf") ;
            }
        });

        wPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DPilihPelanggan dPilihPelanggan = new DPilihPelanggan(DPenjualan.this) ;
                FragmentManager fm = getFragmentManager() ;
                dPilihPelanggan.show(fm,"ad");
            }
        });

        spMetode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                metode = position ;
                if (metode > 0 && tblPelanggan == null){
                    utilitas.getSnackBar(v,R.id.wadah,"Harap Pilih Pelanggan dahulu");
                } else {
                    ubahMetode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void ubahMetode() {
        etBayar.setEnabled(false);
        if (metode == 0){
            etBayar.setEnabled(true) ;
            utilitas.setText(v,R.id.tKembali,"Kembali") ;
            utilitas.setText(v,R.id.tBayar,"Bayar") ;
            utilitas.setText(v,R.id.etBayar,"0") ;
        } else if (metode == 1){
            utilitas.setText(v,R.id.tKembali,"Total Hutang") ;
            utilitas.setText(v,R.id.tBayar,"Hutang") ;
            utilitas.setText(v,R.id.etBayar,utilitas.removeE(utilitas.intToStr(total))) ;

            String jum = utilitas.penjumlahan(tblPelanggan.getHutang(),utilitas.intToStr(total)) ;
            utilitas.setText(v,R.id.tvKembali,utilitas.removeE(jum)) ;
        } else if (metode == 2){
            utilitas.setText(v,R.id.tKembali,"Sisa Deposit") ;
            utilitas.setText(v,R.id.tBayar,"Bayar") ;
            utilitas.setText(v,R.id.etBayar,utilitas.removeE(utilitas.intToStr(total))) ;

            String jum = utilitas.intToStr(utilitas.strToInt(tblPelanggan.getSaldodeposit()) - total) ;
            utilitas.setText(v,R.id.tvKembali,utilitas.removeE(jum)) ;
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit() ;
    }

    public void load(){
        temp = new ArrayList<>() ;
        AdapterPenjualan adapter = new AdapterPenjualan(temp) ;
        recyclerView.setAdapter(adapter) ;

        for (int i = 0 ; i < list.size() ; i++){
            if (list.get(i).getJumlah() > 0){
                temp.add(list.get(i)) ;
                if (list.get(i).getSatuan() == 0){
                    total += utilitas.strToInt(list.get(i).getHargakecil()) * list.get(i).getJumlah() ;
                } else {
                    total += utilitas.strToInt(list.get(i).getHargabesar()) * list.get(i).getJumlah() ;
                }
            }
        }

        MyApp myApp = new MyApp(utilitas,new Database(tPilihPenjualan.getActivity())) ;
        faktur = myApp.getFaktur() ;
        utilitas.setText(v,R.id.tvTotal,utilitas.removeE(utilitas.intToStr(total))) ;
        utilitas.setText(v,R.id.tvTanggal,utilitas.getDate(tPilihPenjualan.getString(R.string.typeDate))) ;
        utilitas.setText(v,R.id.tvFaktur,faktur) ;

        adapter.notifyDataSetChanged();
    }

    public void simpan(){

        String sisa = utilitas.getText(v,R.id.tvKembali).replace(".","") ; ;
        String kembali = utilitas.getText(v,R.id.tvKembali).replace(".","") ; ;
        String ket = "" ;
        String tgl = utilitas.getText(v,R.id.tvTanggal) ;
        String tglmix = utilitas.getYear(tgl)+ utilitas.getMonth(tgl) + utilitas.getDay(tgl) ;

        if (metode == 0){
            ket = "Tunai" ;
        } else if (metode == 1){
            kembali = "0" ;
            ket = "Hutang" ;
        } else {
            kembali = "0" ;
            ket = "Deposit" ;
        }

        String sql = "insert into tblorder (idpelanggan,idpegawai,faktur,tglorder,totalorder,bayar,kembali,ketorder,tglmix) values (" +
                "'"+tblPelanggan.getIdpelanggan()+"'," +
                "'"+tblPegawai.getIdpegawai()+"'," +
                "'"+faktur+"'," +
                "'"+utilitas.getText(v,R.id.tvTanggal)+"'," +
                "'"+utilitas.intToStr(total)+"'," +
                "'"+utilitas.getText(v,R.id.etBayar).replace(".","")+"'," +
                "'"+kembali+"'," +
                "'"+ket+"'," +
                "'"+tglmix+"'" +
                ")" ;

        if (metode == 0 && utilitas.strToInt(kembali) < 0){
            utilitas.getSnackBar(v,R.id.wadah,"Uang pembayaran kurang");
        } else if (metode == 2 && utilitas.strToInt(sisa) < 0){
            utilitas.getSnackBar(v,R.id.wadah,"Saldo deposit kurang");
        } else {
            if (db.execution(sql)){
                if (metode == 1){
                    sql = "insert into tblhutang (idpelanggan,hutang,tglhutang,flaghutang,tglmix) values (" +
                            "'"+tblPelanggan.getIdpelanggan()+"'," +
                            "'"+utilitas.intToStr(total)+"'," +
                            "'"+tgl+"'," +
                            "'0'," +
                            "'"+tglmix+"'" +
                            ")" ;
                    db.execution(sql) ;
                    sisa = utilitas.intToStr(utilitas.strToInt(tblPelanggan.getHutang())+total) ;
                    sql = "update tblpelanggan set hutang='"+sisa+"' where idpelanggan='"+tblPelanggan.getIdpelanggan()+"'" ;
                    db.execution(sql) ;
                } else if (metode == 2){
                    sisa = utilitas.intToStr(utilitas.strToInt(tblPelanggan.getSaldodeposit())-total) ;
                    sql = "update tblpelanggan set saldodeposit='"+sisa+"' where idpelanggan='"+tblPelanggan.getIdpelanggan()+"'" ;
                    db.execution(sql) ;
                }
                insert() ;
            } else {
                utilitas.getSnackBar(v,R.id.wadah,"Transaksi gagal") ;
            }
        }
    }

    public void insert(){
        int no = 0 ;
        for (int i = 0 ; i < temp.size() ; i++) {
            String sql = "select * from tblsatuan where satuanbesar='" + temp.get(i).getSatuanbesar() + "'";
            int nk = utilitas.strToInt(db.getValue(sql,"nilaikecil")) ;

            String[] stok = temp.get(i).getStokbesar().split("sisa") ;
            int besar = utilitas.strToInt(stok[0]) ;
            int kecil = utilitas.strToInt(stok[1]) ;
            String hasil = "" ;
            String harga = "" ;
            String satuan = "" ;

            if (temp.get(i).getSatuan() == 0){
                int jumlah = besar * nk + kecil ;
                int sisa = jumlah - temp.get(i).getJumlah() ;
                int sisaB = sisa / nk ;
                int sisaK = sisa % nk ;

                if (sisa >= 0){
                    hasil = utilitas.intToStr(sisaB) + "sisa" + utilitas.intToStr(sisaK) ;
                    harga = temp.get(i).getHargakecil() ;
                    satuan = temp.get(i).getSatuankecil() ;
                } else {
                    hasil = "OVER" ;
                }
            } else {
                int sisa = besar - temp.get(i).getJumlah() ;
                if (sisa >= 0){
                    hasil = utilitas.intToStr(sisa) + "sisa" + utilitas.intToStr(kecil) ;
                    harga = temp.get(i).getHargabesar() ;
                    satuan = temp.get(i).getSatuanbesar() ;
                } else {
                    hasil = "OVER" ;
                }
            }

            if (hasil.equals("OVER")){
                utilitas.toast("Salah Satu produk stok tidak mencukupi");
            } else {
                String q1 = "update tblproduk set stokbesar='"+hasil+"' where idproduk='"+temp.get(i).getIdproduk()+"'" ;
                String q2 = "insert into tbldetailorder (faktur,idproduk,hargaorder,jumlahorder,satuan,ketdetailorder) values (" +
                        "'"+faktur+"'," +
                        "'"+temp.get(i).getIdproduk()+"'," +
                        "'"+harga+"'," +
                        "'"+utilitas.intToStr(temp.get(i).getJumlah())+"'," +
                        "'"+satuan+"'," +
                        "'"+temp.get(i).getKetorder()+"'" +
                        ")" ;

                if (db.execution(q1) && db.execution(q2)){
                    no++ ;
                }
            }
        }

        if (no == temp.size()){
            utilitas.getSnackBar(v,R.id.wadah,"Transaksi berhasil") ;
            print() ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,"Transaksi gagal2") ;
            utilitas.toast(no);
        }
    }

    public void set(TblPelanggan tblPelanggan) {
        this.tblPelanggan = tblPelanggan ;
        utilitas.setText(v,R.id.tvPelanggan,tblPelanggan.getPelanggan()) ;
        ubahMetode();
    }

    public void set2(TblPegawai tblPegawai) {
        this.tblPegawai = tblPegawai ;
        utilitas.setText(v,R.id.tvPegawai,tblPegawai.getPegawai()) ;
    }

    public void print(){
        tPilihPenjualan.load() ;
        tPilihPenjualan.load2() ;
        DPrintPreView dPrintPreView = new DPrintPreView(this,getActivity(),faktur,false) ;
        FragmentManager fm = getFragmentManager() ;
        dPrintPreView.show(fm,"adsf") ;
    }
}
