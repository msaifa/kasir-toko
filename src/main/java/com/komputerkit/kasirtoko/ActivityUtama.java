package com.komputerkit.kasirtoko;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.komputerkit.kasirtoko.Fragment.About;
import com.komputerkit.kasirtoko.Fragment.Beranda;
import com.komputerkit.kasirtoko.Fragment.Laporan.LBayarHutang;
import com.komputerkit.kasirtoko.Fragment.Laporan.LDetailPenjualan;
import com.komputerkit.kasirtoko.Fragment.Laporan.LHutang;
import com.komputerkit.kasirtoko.Fragment.Laporan.LPegawai;
import com.komputerkit.kasirtoko.Fragment.Laporan.LPelanggan;
import com.komputerkit.kasirtoko.Fragment.Laporan.LPenjualan;
import com.komputerkit.kasirtoko.Fragment.Laporan.LProduk;
import com.komputerkit.kasirtoko.Fragment.Laporan.LTopup;
import com.komputerkit.kasirtoko.Fragment.Master.MKategori;
import com.komputerkit.kasirtoko.Fragment.Master.MPegawai;
import com.komputerkit.kasirtoko.Fragment.Master.MPelanggan;
import com.komputerkit.kasirtoko.Fragment.Master.MProduk;
import com.komputerkit.kasirtoko.Fragment.Master.MSatuan;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihHutang;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihPenjualan;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TTambahStok;
import com.komputerkit.kasirtoko.Fragment.Transaksi.TTopup;
import com.komputerkit.kasirtoko.Fragment.Utilitas.UBackupDB;
import com.komputerkit.kasirtoko.Fragment.Utilitas.UBeliFitur;
import com.komputerkit.kasirtoko.Fragment.Utilitas.UIdentitas;
import com.komputerkit.kasirtoko.Fragment.Utilitas.UResetData;
import com.komputerkit.kasirtoko.Fragment.Utilitas.URestoreDB;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.Other.DrawerActivityUtama;
import com.komputerkit.kasirtoko.Utilitas.Config;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.MyApp;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

public class ActivityUtama extends AppCompatActivity
        implements BillingProcessor.IBillingHandler{

    NavigationView navigationView ;
    DrawerActivityUtama menu ;
    boolean posisi,isPay ;
    FragmentTransaction fragmentTransaction ;
    String type = "" ;
    BillingProcessor bp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        init() ;
        pindah(getString(R.string.mnBeranda));
        permissionStorage();
    }

    @Override
    public void onBackPressed() {

//        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();

        if (posisi){
            if (type.equals("")){
                pindah(getString(R.string.mnBeranda));
                posisi = false ;
            } else if (type.equals("penjualan")){
                Utilitas utilitas = new Utilitas(this) ;
                utilitas.showDialog(getString(R.string.iKeluar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pindah(getString(R.string.mnBeranda));
                        posisi = false ;
                    }
                });
            } else {
                pindah(type) ;
            }
        } else {
            Utilitas utilitas = new Utilitas(this) ;
            utilitas.showDialog(getString(R.string.iKeluar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    } else {
                        finish();
                    }
                }
            });
        }

    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return false;
//    }

    public void pencucian(ArrayList<QProduk> data){
        FragmentManager fm = getFragmentManager() ;
        TPembayaran mProduk = new TPembayaran(data) ;
        mProduk.show(fm,"Coba") ;
        setJudul(getString(R.string.mnBayar));
    }

    public void init(){
//        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        menu = new DrawerActivityUtama(this,navigationView) ;

//        menu.getExpListView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                if (menu.getExpListView().isGroupExpanded(groupPosition)) {
//                    menu.getExpListView().collapseGroupWithAnimation(groupPosition);
//                } else {
//                    menu.getExpListView().expandGroupWithAnimation(groupPosition);
//                }
//                TextView item = v.findViewById(R.id.textTitle) ;
//                pindah(item.getText().toString()) ;
//                return true;
//            }

//        });
//
//        menu.getExpListView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                TextView item = view.findViewById(R.id.textTitle) ;
//                pindah(item.getText().toString()) ;
//                return true;
//            }
//        });

        bp = new BillingProcessor(this, Config.getBASE64ENCODE(),this) ;

        notifPay();
    }

    private void notifPay() {
        Utilitas utilitas = new Utilitas(this) ;
        if (!utilitas.getSession(Config.getIDFullVersion(),false)){
            utilitas.showAlert(getString(R.string.iNotifPay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
    }

    public boolean permissionStorage(){
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE ;
        int requestCode = 0x3 ;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
            return false ;
        } else {
            return true ;
        }
    }

    public void setJudul(String judul){
        Utilitas.setText(this.findViewById(android.R.id.content),R.id.tvTitle,judul) ;
    }

    public void openDrawer(View v){
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.openDrawer(GravityCompat.START);
        onBackPressed();
    }

    public void pindah(String tujuan){
        boolean select = false ;
        posisi = true ;
        type = "" ;
        fragmentTransaction = getFragmentManager().beginTransaction() ;

        MyApp myApp = new MyApp(new Utilitas(this), new Database(this)) ;
        if (myApp.cekRow("tblorder") || myApp.cekRow("tblproduk") || myApp.cekRow("tblpelanggan")){
            tujuan = getString(R.string.mnBeranda) ;
            bayar();
        }

        if (tujuan.equals(getString(R.string.mnProduk))){

            MProduk mProduk = new MProduk() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnMaster))){

            fragment_main_menu MMenu = new fragment_main_menu() ;
            fragmentTransaction.replace(R.id.wFragment,MMenu).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnTransaksi))){

            fragment_main_transaksi MMtransaksi = new fragment_main_transaksi() ;
            fragmentTransaction.replace(R.id.wFragment,MMtransaksi).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnPenjualan))){

            fragment_Transaksi_penjualan TTpenjualan = new fragment_Transaksi_penjualan() ;
            fragmentTransaction.replace(R.id.wFragment,TTpenjualan).commit() ;
            setJudul(tujuan);
            type = "penjualan" ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLaporan))){

            fragment_main_laporan MMlaporan = new fragment_main_laporan() ;
            fragmentTransaction.replace(R.id.wFragment, MMlaporan).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnUtilitas))){

            fragment_main_utilitas MMutilitas = new fragment_main_utilitas() ;
            fragmentTransaction.replace(R.id.wFragment, MMutilitas).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnPelanggan))){

            MPelanggan mProduk = new MPelanggan() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnPegawai))){

            MPegawai mProduk = new MPegawai() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnKategori))){

            MKategori mProduk = new MKategori() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnSatuan))){

            MSatuan mProduk = new MSatuan() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnTopup))){

            TTopup mProduk = new TTopup() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnPenjualan))){

            TPilihPenjualan mProduk = new TPilihPenjualan() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnBayarUtang))){

            TPilihHutang mProduk = new TPilihHutang() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapProduk))){

            LProduk mProduk = new LProduk() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapPelanggan))){

            LPelanggan mProduk = new LPelanggan() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapTopup))){

            LTopup mProduk = new LTopup() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapPegawai))){

            LPegawai mProduk = new LPegawai() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapPenjualan))){

            LPenjualan mProduk = new LPenjualan() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapDetail))){

            LDetailPenjualan mProduk = new LDetailPenjualan() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapHutang))){

            LHutang mProduk = new LHutang() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapBayarHutang))){

            LBayarHutang mProduk = new LBayarHutang() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnAbout))){

            About mProduk = new About() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnBackup))){

            UBackupDB mProduk = new UBackupDB() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnRestore))){

            URestoreDB mProduk = new URestoreDB() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnReset))){

            UResetData mProduk = new UResetData() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnIdentitas))){

            UIdentitas mProduk = new UIdentitas() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnBeliFitur))){

            UBeliFitur mProduk = new UBeliFitur() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnTambahStok))){

            TTambahStok mProduk = new TTambahStok() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnBeranda))){

            Beranda mProduk = new Beranda() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        }
    }

    public void bayar(){
        bp.purchase(this, Config.getIDFullVersion()) ;
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Utilitas utilitas = new Utilitas(this) ;
        utilitas.setSession(Config.getIDFullVersion(),true) ;
        utilitas.toast(getString(R.string.iPaySuccess)) ;
    }

    @Override
    public void onPurchaseHistoryRestored() {
        Utilitas utilitas = new Utilitas(this) ;
        utilitas.setSession(Config.getIDFullVersion(),false) ;
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Utilitas utilitas = new Utilitas(this) ;
        utilitas.setSession(Config.getIDFullVersion(),false) ;
    }

    @Override
    public void onBillingInitialized() {

    }
}
