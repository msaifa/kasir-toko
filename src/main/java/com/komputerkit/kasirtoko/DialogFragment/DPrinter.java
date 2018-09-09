package com.komputerkit.kasirtoko.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Printer;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by msaifa on 01/02/2018.
 */

@SuppressLint("ValidFragment")
public class DPrinter extends DialogFragment{

    public Context context ;
    Utilitas utilitas ;
    Database db ;
    View v ;
    ConstraintLayout wBack,btnCari,btnCetak ;
    String printer,faktur ;
    Printer cprinter ;
    boolean connected ;
    ImageView imgRefresh ;

    String hasil = "" ;

    @SuppressLint("ValidFragment")
    public DPrinter(Context context,String faktur) {
        this.context = context ;
        this.faktur = faktur ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        utilitas = new Utilitas(context) ;
        db = new Database(context) ;

        setPrinter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_printer,container,false) ;
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
        btnCari = v.findViewById(R.id.btnCari) ;
        btnCetak = v.findViewById(R.id.btnCetak) ;
        imgRefresh = v.findViewById(R.id.imgRefresh) ;
    }

    public void event(){
        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager gm = getFragmentManager() ;
                DCariPrinter dCariPrinter = new DCariPrinter(DPrinter.this) ;
                dCariPrinter.show(gm,"");
            }
        });

        btnCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (utilitas.getText(v,R.id.tvPrinter).equals("-")){
                    utilitas.toast("Please select printer");
                } else if (!connected){
                    utilitas.toast(context.getString(R.string.iCekPrinter));
                }else {
                    pembayaran();
                }
            }
        });

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrinter();
            }
        });
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DPrinter.this).commit() ;
    }

    public void setPrinter(){
        printer = utilitas.getSession("printer","-") ;
        utilitas.setText(v,R.id.tvPrinter,printer) ;

        connect();
    }

    public void connect(){
        cprinter = new Printer(context,this) ;
        try {
            cprinter.findBT(printer);

            if (printer.equals("-") || !cprinter.openBT()){
                utilitas.setText(v,R.id.tvStatus,context.getString(R.string.cDisconnect)) ;
                connected = false ;
            } else {
                utilitas.setText(v,R.id.tvStatus,context.getString(R.string.cConnect)) ;
                connected = true ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setHeader(){
        String sql = "select * from tblidentitas" ;

        String toko = db.getValue(sql,"nama") ;
        String alamat = db.getValue(sql,"alamat") ;
        String telp = db.getValue(sql,"telp") ;

        try {
            hasil += cprinter.setCenter(toko) + "\n" +
                     cprinter.setCenter(alamat) + "\n" +
                     cprinter.setCenter(telp) + "\n" +
                     cprinter.setCenter("  ") + "\n" ;

            cprinter.printBoldMediumCenter(toko) ;
            cprinter.printNormalCenter((alamat)) ;
            cprinter.printNormalCenter((telp)) ;
            cprinter.printNormalCenter("  ") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFooter(){
        String sql = "select * from tblidentitas" ;

        String capt1 = db.getValue(sql,"caption1") ;
        String capt2 = db.getValue(sql,"caption2") ;
        String capt3 = db.getValue(sql,"caption3") ;

        try {
            hasil += cprinter.setCenter(" ") + "\n" +
                     cprinter.setCenter(capt1) + "\n" +
                     cprinter.setCenter(capt2) + "\n" +
                     cprinter.setCenter(capt3) + "\n" ;

            cprinter.printNormalCenter("  ") ;
            cprinter.printNormalCenter(capt1) ;
            cprinter.printNormalCenter((capt2)) ;
            cprinter.printNormalCenter((capt3)) ;
            cprinter.printNormalCenter("  ") ;
            cprinter.printNormalCenter("  ") ;
            cprinter.printNormalCenter("  ") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pembayaran(){
        String q = "select * from qorder where faktur='"+faktur+"'" ;

        String fakt = "Faktur : " + faktur ;
        String tgl = "Tanggal : " + db.getValue(q,"tglorder") ;
        String pelanggan = "Pelanggan : " + db.getValue(q,"pelanggan") ;
        String pegawai = "Pegawai : "+db.getValue(q,"pegawai") ;
        String total = "Total : " +utilitas.removeE(db.getValue(q,"totalorder"));
        String bayar = "Bayar : " +utilitas.removeE(db.getValue(q,"bayar"));
        String kembali = "Kembali : " +utilitas.removeE(db.getValue(q,"kembali"));
        String status = "Metode Pembayaran : "+db.getValue(q,"ketorder");
        String strip = cprinter.getStrip() ;

        if (status.equals("Tunai")){
            status = "Tunai" ;
        } else if (status.equals("Hutang")){
            status = "Hutang" ;
        } else {
            status = "Deposit" ;
        }

        try {
            setHeader();

            hasil += fakt + "\n" +
                    tgl+ "\n" +
                    pelanggan+ "\n" +
                    pegawai+ "\n" +
                    status+ "\n" +
                    strip  + "\n";

            cprinter.printNormal(fakt) ;
            cprinter.printNormal(tgl) ;
            cprinter.printNormal(pelanggan) ;
            cprinter.printNormal(pegawai) ;
            cprinter.printNormal(status) ;
            cprinter.printNormal(strip) ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        q = "select * from qdetailorder where faktur='"+faktur+"'" ;
        Cursor c = db.select(q) ;
        while(c.moveToNext()){
            String barang = db.getString(c,"produk") ;
            String jumlah = db.getString(c,"jumlahorder") ;
            String satuan = db.getString(c,"satuan") ;
            String harga = db.getString(c,"hargaorder") ;
            double jumlahharga = Utilitas.strToDouble(jumlah) * Utilitas.strToDouble(harga) ;
            DecimalFormat df = new DecimalFormat("#");
            df.setRoundingMode(RoundingMode.CEILING);
            String jum = cprinter.setRight(df.format(jumlahharga)) ;

            try {
                hasil += barang+ "\n" +
                        jumlah + " " + satuan + " x " + utilitas.removeE(harga) + "\n" +
                        cprinter.setRight(utilitas.removeE(jum)) ;

                cprinter.printNormal(barang) ;
                cprinter.printNormal(jumlah + " " + satuan + " x " + utilitas.removeE(harga)) ;
                cprinter.printNormal(cprinter.setRight(utilitas.removeE(jum))) ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            hasil += strip  + "\n";
            cprinter.printNormal(cprinter.getStrip()) ;
            if (!status.equals("BELUM DIBAYAR")){
                hasil += cprinter.setRight(total) + "\n" +
                         cprinter.setRight(bayar) + "\n" +
                         cprinter.setRight(kembali) + "\n" ;

                cprinter.printNormal(cprinter.setRight(total)) ;
                cprinter.printNormal(cprinter.setRight(bayar)) ;
                cprinter.printNormal(cprinter.setRight(kembali)) ;
            }

            setFooter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        keluar();
    }
}
