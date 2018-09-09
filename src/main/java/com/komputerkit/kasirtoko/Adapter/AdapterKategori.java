package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MKategori;
import com.komputerkit.kasirtoko.Model.TblKategori;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 18/02/2018.
 */

public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.ViewHolder>{

    ArrayList<TblKategori> data ;
    Utilitas utilitas ;
    MKategori mKategori;

    public AdapterKategori(MKategori mKategori, ArrayList<TblKategori> data) {
        this.data = data;
        this.mKategori = mKategori ;

        utilitas = new Utilitas(mKategori.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvKeterangan.setText(data.get(position).getKetkategori()) ;
        holder.tvKategori.setText(data.get(position).getKategori()) ;

        holder.wOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showPopUp(v,R.menu.menu_opsi).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId() ;

                        if (id == R.id.iHapus){
                            mKategori.hapus(data.get(position).getKategori());
                        } else if (id == R.id.iUbah){
                            mKategori.ubah(data.get(position).getKategori());
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

        TextView tvKategori,tvKeterangan;
        ConstraintLayout wOpsi ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvKategori = itemView.findViewById(R.id.tvKategori) ;
            tvKeterangan = itemView.findViewById(R.id.tvKeterangan) ;
            wOpsi = itemView.findViewById(R.id.wSampah) ;
        }
    }
}