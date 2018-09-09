package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MProduk;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 04/03/2018.
 */

public class AdapterPenjualan extends RecyclerView.Adapter<AdapterPenjualan.ViewHolder>{

    ArrayList<QProduk> data ;

    public AdapterPenjualan(ArrayList<QProduk> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penjualan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk());

        if (data.get(position).getSatuan() == 0){
            String jumlah = Utilitas.intToStr(data.get(position).getJumlah() * Utilitas.strToInt(data.get(position).getHargakecil())) ;
            holder.tvHarga.setText(Utilitas.removeE(jumlah));
            holder.tvJumlah.setText(data.get(position).getJumlah() + " " + data.get(position).getSatuankecil());
        } else {
            String jumlah = Utilitas.intToStr(data.get(position).getJumlah() * Utilitas.strToInt(data.get(position).getHargabesar())) ;
            holder.tvHarga.setText(Utilitas.removeE(jumlah));
            holder.tvJumlah.setText(data.get(position).getJumlah() + " " + data.get(position).getSatuanbesar());
        }
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvJumlah,tvHarga ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvJumlah = itemView.findViewById(R.id.tvJumlah) ;
            tvHarga= itemView.findViewById(R.id.tvHarga) ;
        }
    }
}