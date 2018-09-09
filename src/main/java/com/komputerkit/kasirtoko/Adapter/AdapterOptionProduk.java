package com.komputerkit.kasirtoko.Adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.komputerkit.kasirtoko.DialogFragment.DCartProduk;
import com.komputerkit.kasirtoko.DialogFragment.DcartPelanggan;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

public class AdapterOptionProduk extends RecyclerView.Adapter<AdapterOptionProduk.ViewHolder> {
    ArrayList<QProduk> data ;
    Utilitas utilitas ;
    DCartProduk fragmentBaru;

    public AdapterOptionProduk(DCartProduk fragment, ArrayList<QProduk> data) {
        this.data = data;
        this.fragmentBaru = fragment ;

        utilitas = new Utilitas(fragment.getActivity()) ;
    }

    @Override
    public AdapterOptionProduk.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk,parent,false) ;
        return new AdapterOptionProduk.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk());
        holder.tvKategori.setText(data.get(position).getKategori());
        holder.tvHargaBesar.setText(utilitas.removeE(data.get(position).getHargabesar()) + "/ " + (data.get(position).getSatuanbesar()));
        holder.tvHargaKecil.setText(utilitas.removeE(data.get(position).getHargakecil()) + "/ " + (data.get(position).getSatuankecil()));
        holder.wOpsi.setVisibility(View.GONE);

        holder.BarangClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentBaru.pilih(data.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvKategori,tvHargaBesar,tvHargaKecil ;
        ConstraintLayout wOpsi,BarangClick ;

        public ViewHolder(View itemView) {
            super(itemView);

            BarangClick = itemView.findViewById(R.id.BarangClicked);
            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvKategori = itemView.findViewById(R.id.tvKategori) ;
            tvHargaBesar = itemView.findViewById(R.id.tvHargaBesar) ;
            tvHargaKecil = itemView.findViewById(R.id.tvHargaKecil) ;
            wOpsi = itemView.findViewById(R.id.wSampah) ;
        }
    }
}
