package com.komputerkit.kasirtoko.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirtoko.Fragment.Master.MSatuan;
import com.komputerkit.kasirtoko.Model.TblSatuan;
import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 25/02/2018.
 */

public class AdapterSatuan extends RecyclerView.Adapter<AdapterSatuan.ViewHolder>{

    ArrayList<TblSatuan> data ;
    Utilitas utilitas ;
    MSatuan fragment;

    public AdapterSatuan(MSatuan fragment, ArrayList<TblSatuan> data) {
        this.data = data;
        this.fragment = fragment ;

        utilitas = new Utilitas(fragment.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_satuan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvBesar.setText(data.get(position).getSatuanbesar());
        holder.tvKecil.setText(data.get(position).getNilaikecil() + " " + data.get(position).getSatuankecil());

        holder.wOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showPopUp(v,R.menu.menu_opsi).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId() ;

                        if (id == R.id.iHapus){
                            fragment.hapus(data.get(position).getSatuanbesar());
                        } else if (id == R.id.iUbah){
                            fragment.ubah(data.get(position).getSatuanbesar());
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

        TextView tvBesar,tvKecil ;
        ConstraintLayout wOpsi ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvKecil = itemView.findViewById(R.id.tvSatuanKecil) ;
            tvBesar= itemView.findViewById(R.id.tvSatuanBesar) ;
            wOpsi = itemView.findViewById(R.id.wSampah) ;
        }
    }
}