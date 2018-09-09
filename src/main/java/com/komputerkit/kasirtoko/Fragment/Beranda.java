package com.komputerkit.kasirtoko.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.ActivityUtama;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Database;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

/**
 * Created by msaifa on 13/02/2018.
 */

public class Beranda extends Fragment {

    Utilitas utilitas ;
    Database db ;
    View v ;
    TextView tvNama, tvAlamat, tvTelp;
    ConstraintLayout btnSatu,btnDua,btnTiga, btnEmpat, btnLima, btnEnam,btnTujuh, wback ;

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        setText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beranda,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event();
    }

    public void init(){
        tvNama = v.findViewById(R.id.tvHari) ;
        tvAlamat = v.findViewById(R.id.tPelanggan) ;
        tvTelp = v.findViewById(R.id.tProduk) ;
        btnSatu = v.findViewById(R.id.lPetunjuk) ;
        btnDua = v.findViewById(R.id.lIdentitas) ;
        btnTiga = v.findViewById(R.id.lMaster) ;
        btnEmpat = v.findViewById(R.id.lTransaksi) ;
        btnLima = v.findViewById(R.id.lLaporan);
        btnEnam = v.findViewById(R.id.lUtilitas);
        btnTujuh = v.findViewById(R.id.lTentangKami);

//        wback = v.findViewById(R.id.wBack);
//        wback.setVisibility(View.VISIBLE);
    }

    public void event(){
        btnSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnGuide));
            }
        });

        btnDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnIdentitas));
            }
        });

        btnTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnMaster));
            }
        });
        btnEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnTransaksi)) ;
            }
        });
        btnLima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnLaporan)) ;
            }
        });
        btnEnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnUtilitas)) ;
            }
        });
        btnTujuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnAbout)) ;
            }
        });
    }

    public void setText(){
        String sql = "select * from tblidentitas" ;
        tvNama.setText(db.getValue(sql,"nama") );
        tvAlamat.setText (db.getValue(sql,"alamat"));
        tvTelp.setText (db.getValue(sql,"telp"));
    }

}
