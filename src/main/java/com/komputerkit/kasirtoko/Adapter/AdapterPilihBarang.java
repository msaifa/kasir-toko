package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Transaksi.TPilihPenjualan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 02/03/2018.
 */

public class AdapterPilihBarang extends RecyclerView.Adapter<AdapterPilihBarang.ViewHolder>{

    ArrayList<QProduk> data ;
    Utilitas utilitas ;
    TPilihPenjualan fragment;

    public AdapterPilihBarang(TPilihPenjualan fragment, ArrayList<QProduk> data) {
        this.data = data;
        this.fragment = fragment;

        utilitas = new Utilitas(fragment.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pilih_produk,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        ArrayList array = new ArrayList() ;
        String[] stok = data.get(position).getStokbesar().split("sisa") ;

        array.add(data.get(position).getSatuankecil()) ;
        array.add(data.get(position).getSatuanbesar()) ;

        ArrayAdapter adapter = new ArrayAdapter(fragment.getActivity(),android.R.layout.simple_spinner_item,array) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spSatuan.setAdapter(adapter);

        holder.spSatuan.setSelection(data.get(position).getSatuan());

        holder.tvProduk.setText(data.get(position).getProduk()) ;
        holder.tvHarga.setText(utilitas.removeE(data.get(position).getHargakecil()) + " /" + data.get(position).getSatuankecil() +"\n"+
                               utilitas.removeE(data.get(position).getHargabesar()) + " /" + data.get(position).getSatuanbesar()) ;
        holder.tvJumlah.setText(utilitas.intToStr(data.get(position).getJumlah()));
        holder.tvStok.setText(stok[0] + " " + data.get(position).getSatuanbesar() + "\n" + stok[1] + " " + data.get(position).getSatuankecil());

        if (data.get(position).getJumlah() < 1){
            holder.btnMinus.setVisibility(View.INVISIBLE) ;
        } else {
            holder.btnMinus.setVisibility(View.VISIBLE) ;
        }

        if (data.get(position).getFlagAktif() == 0){
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        } else {
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.plus(position);
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.minus(position);
            }
        });

        holder.spSatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                fragment.change(position,pos) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.etCatatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragment.setKeterangan(position,s.toString()) ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvHarga, tvJumlah,tvStok ;
        EditText etCatatan ;
        ConstraintLayout btnPlus,btnMinus ;
        public Spinner spSatuan ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvStok = itemView.findViewById(R.id.tvStok) ;
            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            tvJumlah = itemView.findViewById(R.id.tvJumlah) ;
            etCatatan = itemView.findViewById(R.id.etCatatan) ;
            btnPlus = itemView.findViewById(R.id.btnTambah) ;
            btnMinus = itemView.findViewById(R.id.btnKurang) ;
            spSatuan = itemView.findViewById(R.id.spSatuan) ;
        }
    }
}