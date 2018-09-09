package com.komputerkit.kasirtoko;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.komputerkit.kasirtoko.Adapter.AdapterPilihPelanggan;
import com.komputerkit.kasirtoko.DialogFragment.DCartProduk;
import com.komputerkit.kasirtoko.DialogFragment.DPenjualan;
import com.komputerkit.kasirtoko.DialogFragment.DPrintPreView;
import com.komputerkit.kasirtoko.DialogFragment.DcartPelanggan;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihPenjualan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Model.TblPegawai;
import com.komputerkit.kasirtoko.Model.TblPelanggan;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Query;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by SAIF on 23/12/2017.
 */
@SuppressLint("ValidFragment")
public class TPembayaran extends DialogFragment {
    View v ;
    Button button ;
    Database db ;
    Utilitas utilitas ;
    DCartProduk dCartProduk;
    TPilihPenjualan tPilihPenjualan ;
    DcartPelanggan dcartPelanggan;
    ConstraintLayout btnbayar;

    Query query;
    AdapterPilihProduk adapter;
    ArrayList<QProduk> temp = new ArrayList<>();
    AdapterPilihPelanggan adapterpelanggan;
    ArrayList<QProduk> arrayList = new ArrayList<>() ;
    ArrayList<QProduk> data;
    ArrayList<QProduk> list = new ArrayList()  ;
    TblPelanggan tblPelanggan;
    TblPegawai tblPegawai;
    int metode;

    String faktur;
    String totalBayar = "0";
    String saldo = "0";
    String totalsaldo = "0";
    String uang = "0";
    double sisa = 0;
    double cashback,pay;
    boolean tunai,sekarang,saldoDeposit;

    ImageView cariPelanggan, cariPegawai;
    TextInputEditText etFaktur,etNamaPel,etTanggal,etTotalBayar,etJumlahBayar,etKembali,etKeterangan, etPegawai;
    Spinner met;
    ConstraintLayout wadahButton, bayarTunai, btnSelesai, wBack;

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        setText();
    }

    public TPembayaran(ArrayList<QProduk> data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaksi_pembayaran,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event();
    }

    public void init(){
        wBack = v.findViewById(R.id.wBack) ;

        etFaktur = v.findViewById(R.id.etFaktur) ;
        etTanggal = v.findViewById(R.id.etTanggal);
        etNamaPel = v.findViewById(R.id.etNamaPel) ;
        etTotalBayar = v.findViewById(R.id.etTotalBayar);
        etJumlahBayar = v.findViewById(R.id.etJumlahBayar);
        etKembali = v.findViewById(R.id.etKembali);
        etPegawai = v.findViewById(R.id.etNamaPegawai);
        met = v.findViewById(R.id.spMetodeBayar);
        button = v.findViewById(R.id.button) ;
        btnSelesai = v.findViewById(R.id.btnBayar);
        bayarTunai = v.findViewById(R.id.pnbyr);
        cariPelanggan = v.findViewById(R.id.cariBarang);
        cariPegawai = v.findViewById(R.id.cariPegawai);

        etKembali.setFocusable(false);
    }

    public void event() {
        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(TPembayaran.this).commit() ;
            }
        });

        met.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String pilih = met.getSelectedItem().toString() ;
                metode = position ;

//                if(pilih.equals("Tunai")){
//                    bayarTunai.setVisibility(View.VISIBLE);
//                    utilitas.setText(v,R.id.etJumlahBayar,"0");
//                    tunai = true;
//                } else if (pilih.equals("Hutang")){
//                    bayarTunai.setVisibility(View.GONE);
//                    sekarang = true;
//                } else if (pilih.equals("Deposit")) {
//                    bayarTunai.setVisibility(View.VISIBLE);
////                    int terserah = Integer.parseInt(totalBayar.toString());
////                    utilitas.setText(v,R.id.etBayar,utilitas.removeE(utilitas.intToStr(Integer.parseInt(totalsaldo)))) ;
////                    String jum = utilitas.intToStr(utilitas.strToInt(tblPelanggan.getSaldodeposit())) ;
////                    utilitas.setText(v,R.id.tvKembali,utilitas.removeE(jum)) ;
////                    utilitas.setText(v,R.id.tvKembali,tblPelanggan.getSaldodeposit()) ;
////                    String data = tblPelanggan.getSaldodeposit();
//                    saldo = utilitas.removeE(tblPelanggan.getSaldodeposit()) ;
//                    utilitas.setText(v,R.id.etJumlahBayar,saldo);
//                    saldoDeposit = true;
//                }
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
//        btnbayar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                MyApp myApp = new MyApp(utilitas,db) ;
//                if (myApp.cekRow("tblorder")){
//                    ((ActivityUtama)getActivity()).bayar();
//                } else {
//                    DPenjualan dPenjualan = new DPenjualan(TPembayaran.this,list) ;
//                    FragmentManager fm = getFragmentManager() ;
//                    dPenjualan.show(fm,"asdf");
//                }
//
//
//            }
//        });
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tblPelanggan == null){
                    utilitas.getSnackBar(v,R.id.wadah,"Silahkan pilih pelanggan");
                } else {
                    simpan();
                }
            }
        });
        cariPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DcartPelanggan dcartPelanggan = new DcartPelanggan(TPembayaran.this,"pelanggan") ;
                FragmentManager fm = getFragmentManager() ;
                dcartPelanggan.show(fm,"coba");
            }
        });
        cariPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DcartPelanggan dcartPelanggan = new DcartPelanggan(TPembayaran.this,"pegawai") ;
                FragmentManager fm = getFragmentManager() ;
                dcartPelanggan.show(fm,"coba");
            }
        });
        etJumlahBayar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (metode == 0){ //sing tak ganti teko tunai nang metode
                    etJumlahBayar.removeTextChangedListener(this);
                    try{
                        String arrayList = s.toString().replace(".","") ;
                        String nf = utilitas.numberFormat(arrayList) ;
                        etJumlahBayar.setText(nf) ;
                        etJumlahBayar.setSelection(nf.length());
                    }catch (Exception e){

                    }
                    etJumlahBayar.addTextChangedListener(this);

                    sisa = utilitas.strToDouble(s.toString().replace(".","")) - utilitas.strToDouble(totalBayar.replace(".","")) ;
                    utilitas.setText(v,R.id.etKembali,utilitas.removeE(sisa)) ;
                }
                else if(metode == 2) {
                    if (tblPelanggan != null){
                        sisa = utilitas.strToDouble(tblPelanggan.getSaldodeposit()) - utilitas.strToDouble(totalBayar.replace(".","")) ;
                        utilitas.setText(v,R.id.etKembali,utilitas.removeE(sisa)) ;
                    } else {
                        utilitas.setText(v,R.id.etKembali,"0");
                    }
                }
            }
        });
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilitas.showDateDialog(etTanggal);
            }
        });
    }

    private void ubahMetode() {
        etJumlahBayar.setEnabled(false);
        if (metode == 0){
            etJumlahBayar.setEnabled(true) ;
//            utilitas.setText(v,R.id.tKembali,"Kembali") ;
//            utilitas.setText(v,R.id.tBayar,"Bayar") ;
//            utilitas.setText(v,R.id.etBayar,"0") ;

            bayarTunai.setVisibility(View.VISIBLE);
            utilitas.setText(v,R.id.etJumlahBayar,"0");
        } else if (metode == 1){
//            utilitas.setText(v,R.id.tKembali,"Total Hutang") ;
//            utilitas.setText(v,R.id.tBayar,"Hutang") ;
//            utilitas.setText(v,R.id.etBayar,utilitas.removeE(totalBayar)) ;
//            String jum = utilitas.penjumlahan(tblPelanggan.getHutang(),totalBayar) ;
//            utilitas.setText(v,R.id.tvKembali,utilitas.removeE(jum)) ;

            bayarTunai.setVisibility(View.GONE);
        } else if (metode == 2){
//            utilitas.setText(v,R.id.tKembali,"Sisa Deposit") ;
//            utilitas.setText(v,R.id.tBayar,"Bayar") ;
//            utilitas.setText(v,R.id.etBayar,utilitas.removeE(totalBayar));
//            int jumlah = utilitas.strToInt(tblPelanggan.getSaldodeposit());
//            String jum = utilitas.intToStr(jumlah-Utilitas.strToInt(totalBayar)) ;
//            utilitas.setText(v,R.id.tvKembali,utilitas.removeE(jum)) ;

            String depo = utilitas.removeE(tblPelanggan.getSaldodeposit());
            utilitas.setText(v,R.id.etJumlahBayar,depo);
            bayarTunai.setVisibility(View.VISIBLE);
        }
    }

    public void setText(){
        MyApp myApp = new MyApp(utilitas,db) ;
        faktur = myApp.getFaktur() ;
        utilitas.setText(v,R.id.etFaktur,faktur) ;
        utilitas.setText(v,R.id.etTanggal,utilitas.getDate(getString(R.string.typeDate))) ;

        double total = 0;
        for (int i = 0 ; i < data.size() ; i++){
//            total += utilitas.strToDouble(data.get(i).getHargakecil()) *utilitas.strToDouble(String.valueOf(data.get(i).getJumlah())) ;

            if(data.get(i).getSatuan() == 0){
                total += utilitas.strToDouble(data.get(i).getHargabesar()) *utilitas.strToDouble(String.valueOf(data.get(i).getJumlah())) ;
            }else{
                total += utilitas.strToDouble(data.get(i).getHargakecil()) *utilitas.strToDouble(String.valueOf(data.get(i).getJumlah())) ;
            }

            if(!String.valueOf(data.get(i).getJumlah()).equals("0")){
                arrayList.add(data.get(i)) ;
            }
        }
        totalBayar = utilitas.removeE(total) ;
        utilitas.setText(v,R.id.etTotalBayar,totalBayar);
    }

    public void simpan(){

        String sisa = utilitas.getText(v,R.id.etKembali).replace(".","") ; ;
        String kembali = utilitas.getText(v,R.id.etKembali).replace(".","") ; ;
        String ket = "" ;
        String tgl = utilitas.getText(v,R.id.etTanggal) ;
        String tglmix = utilitas.getYear(tgl)+ utilitas.getMonth(tgl) + utilitas.getDay(tgl) ;

        if (metode == 0){
            ket = "Tunai" ;
        } else if (metode == 1){
            kembali = "0" ;
            ket = "Hutang" ;
        } else {
            kembali = utilitas.intToStr(utilitas.strToInt(tblPelanggan.getSaldodeposit())-Utilitas.strToInt(totalBayar.replace(".","")));
            ket = "Deposit" ;
        }

        String sql = "insert into tblorder (idpelanggan,idpegawai,faktur,tglorder,totalorder,bayar,kembali,ketorder,tglmix) values (" +
                "'"+tblPelanggan.getIdpelanggan()+"'," +
                "'"+tblPegawai.getIdpegawai()+"'," +
                "'"+faktur+"'," +
                "'"+utilitas.getText(v,R.id.etTanggal)+"'," +
                "'"+totalBayar.replace(".","")+"'," +
                "'"+utilitas.getText(v,R.id.etJumlahBayar).replace(".","")+"',"+
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
                            "'"+totalBayar.replace(".","")+"'," +
                            "'"+tgl+"'," +
                            "'0'," +
                            "'"+tglmix+"'" +
                            ")" ;
                    db.execution(sql) ;
                    int hutang = utilitas.strToInt(tblPelanggan.getHutang());
                    sisa = utilitas.intToStr(hutang+Utilitas.strToInt(totalBayar.replace(".",""))) ;
                    sql = "update tblpelanggan set hutang='"+sisa+"' where idpelanggan='"+tblPelanggan.getIdpelanggan()+"'" ;
                    db.execution(sql) ;
                } else if (metode == 2){
                    sisa = utilitas.intToStr(utilitas.strToInt(tblPelanggan.getSaldodeposit())-Utilitas.strToInt(totalBayar.replace(".",""))) ;
                    sql = "update tblpelanggan set saldodeposit='"+sisa+"' where idpelanggan='"+tblPelanggan.getIdpelanggan()+"'" ;
                    etJumlahBayar.setFocusable(false);
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
        for (int i = 0 ; i < arrayList.size() ; i++) {
            String sql = "select * from tblsatuan where satuanbesar='" + arrayList.get(i).getSatuanbesar() + "'";
            int nk = utilitas.strToInt(db.getValue(sql,"nilaikecil")) ;

            String[] stok = arrayList.get(i).getStokbesar().split("sisa") ;
            int besar = utilitas.strToInt(stok[0]) ;
            int kecil = utilitas.strToInt(stok[1]) ;
            String hasil = "" ;
            String harga = "" ;
            String satuan = "" ;

            if (arrayList.get(i).getSatuan() == 1){
                int jumlah = besar * nk + kecil ;
                int sisa = jumlah - arrayList.get(i).getJumlah() ;
                int sisaB = sisa / nk ;
                int sisaK = sisa % nk ;

                if (sisa >= 0){
                    hasil = utilitas.intToStr(sisaB) + "sisa" + utilitas.intToStr(sisaK) ;
                    harga = arrayList.get(i).getHargakecil() ;
                    satuan = arrayList.get(i).getSatuankecil() ;
                } else {
                    hasil = "OVER" ;
                }
            } else {
                int sisa = besar - arrayList.get(i).getJumlah() ;
                if (sisa >= 0){
                    hasil = utilitas.intToStr(sisa) + "sisa" + utilitas.intToStr(kecil) ;
                    harga = arrayList.get(i).getHargabesar() ;
                    satuan = arrayList.get(i).getSatuanbesar() ;
                } else {
                    hasil = "OVER" ;
                }
            }

            if (hasil.equals("OVER")){
                utilitas.toast("Salah Satu produk stok tidak mencukupi");
            } else {
                String q1 = "update tblproduk set stokbesar='"+hasil+"' where idproduk='"+arrayList.get(i).getIdproduk()+"'" ;
                String q2 = "insert into tbldetailorder (faktur,idproduk,hargaorder,jumlahorder,satuan,ketdetailorder) values (" +
                        "'"+faktur+"'," +
                        "'"+arrayList.get(i).getIdproduk()+"'," +
                        "'"+harga+"'," +
                        "'"+utilitas.intToStr(arrayList.get(i).getJumlah())+"'," +
                        "'"+satuan+"'," +
                        "'"+arrayList.get(i).getKetorder()+"'" +
                        ")" ;

                if (db.execution(q1) && db.execution(q2)){
                    no++ ;
                }
            }
        }

        if (no == arrayList.size()){
            utilitas.getSnackBar(v,R.id.wadah,"Transaksi berhasil") ;
            print() ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,"Transaksi gagal2") ;
            utilitas.toast(no);
        }
    }

    public void setProduk(TblPelanggan tblPelanggan) {
        this.tblPelanggan = tblPelanggan;
        utilitas.setText(v,R.id.etNamaPel,tblPelanggan.getPelanggan());
    }

    public void setProduk(TblPegawai tblPegawai) {
        this.tblPegawai = tblPegawai;
        utilitas.setText(v,R.id.etNamaPegawai,tblPegawai.getPegawai());
    }

    public void print(){
        DPrintPreView dPrintPreView = new DPrintPreView(this,getActivity(),faktur,false) ;
        FragmentManager fm = getFragmentManager() ;
        dPrintPreView.show(fm,"adsf") ;
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit() ;
    }
}
