package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.komputerkit.kasirtoko.Model.QDetailOrder;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa-pc on 03/02/2018.
 */

public class AdapterPrintPreview extends RecyclerView.Adapter<AdapterPrintPreview.ViewHolder>{

    ArrayList<QDetailOrder> data ;

    public AdapterPrintPreview(ArrayList<QDetailOrder> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_preview,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk()) ;
        holder.tvJumlah.setText(data.get(position).getJumlahorder() + " " + data.get(position).getSatuan() +" x " + Utilitas.removeE(data.get(position).getHargaorder())) ;
        holder.tvKeterangan.setVisibility(View.GONE);

        String harga = Utilitas.doubleToStr(Utilitas.strToDouble(data.get(position).getHargaorder()) * Utilitas.strToDouble(data.get(position).getJumlahorder())) ;
        holder.tvHarga.setText(Utilitas.removeE(harga));
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvHarga,tvJumlah, tvKeterangan ;
        ConstraintLayout wadah ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            tvJumlah= itemView.findViewById(R.id.tvJumlah) ;
            tvKeterangan = itemView.findViewById(R.id.tvKeterangan) ;
        }
    }
}