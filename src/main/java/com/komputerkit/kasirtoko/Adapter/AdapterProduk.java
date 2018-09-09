package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MProduk;
import com.komputerkit.kasirtoko.Model.QProduk;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 25/02/2018.
 */

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.ViewHolder>{

    ArrayList<QProduk> data ;
    Utilitas utilitas ;
    MProduk fragment;

    public AdapterProduk(MProduk fragment, ArrayList<QProduk> data) {
        this.data = data;
        this.fragment = fragment ;

        utilitas = new Utilitas(fragment.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk());
        holder.tvKategori.setText(data.get(position).getKategori());
        holder.tvHargaBesar.setText(utilitas.removeE(data.get(position).getHargabesar()) + "/ " + (data.get(position).getSatuanbesar()));
        holder.tvHargaKecil.setText(utilitas.removeE(data.get(position).getHargakecil()) + "/ " + (data.get(position).getSatuankecil()));

        holder.wOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showPopUp(v,R.menu.menu_opsi).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId() ;

                        if (id == R.id.iHapus){
                            fragment.hapus(data.get(position).getIdproduk());
                        } else if (id == R.id.iUbah){
                            fragment.ubah(data.get(position).getIdproduk());
                        }

                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvKategori,tvHargaBesar,tvHargaKecil ;
        ConstraintLayout wOpsi ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvKategori = itemView.findViewById(R.id.tvKategori) ;
            tvHargaBesar = itemView.findViewById(R.id.tvHargaBesar) ;
            tvHargaKecil = itemView.findViewById(R.id.tvHargaKecil) ;
            wOpsi = itemView.findViewById(R.id.wSampah) ;
        }
    }
}