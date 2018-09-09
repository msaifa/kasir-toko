package com.komputerkit.kasirtoko.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 06/03/2018.
 */

public class AdapterLapProduk extends RecyclerView.Adapter<AdapterLapProduk.ViewHolder>{

    ArrayList<QProduk> data ;
    Utilitas utilitas ;

    public AdapterLapProduk(ArrayList<QProduk> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lap_produk,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String[] stok = data.get(position).getStokbesar().split("sisa") ;

        holder.tvProduk.setText(data.get(position).getProduk());
        holder.tvKategori.setText(data.get(position).getKategori());
        holder.tvStok.setText(stok[1] + " " + data.get(position).getSatuankecil() + "\n" +
                              stok[0] + " " + data.get(position).getSatuanbesar());
        holder.tvHarga.setText( utilitas.removeE(data.get(position).getHargakecil()) + "/ " + (data.get(position).getSatuankecil()) + "\n" +
                                utilitas.removeE(data.get(position).getHargabesar()) + "/ " + (data.get(position).getSatuanbesar())
                                );

    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk, tvStok, tvHarga, tvKategori;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvStok = itemView.findViewById(R.id.tvStok) ;
            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            tvKategori = itemView.findViewById(R.id.tvKategori) ;
        }
    }
}