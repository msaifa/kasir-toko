package com.komputerkit.kasirtoko.Other;

import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.komputerkit.kasirtoko.R;
import com.komputerkit.kasirtoko.Utilitas.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAIF on 24/12/2017.
 */

public class DrawerActivityUtama {
    Context context ;
    NavigationView navigationView ;

    AnimatedExpandableListView expListView;
    List<GroupItem> listDataHeader;
    private ExampleAdapter adapter;
    String[][] menu ;

    public DrawerActivityUtama(Context context, NavigationView navigationView) {
        this.context = context;
        this.navigationView = navigationView;
        
        init() ;
    }

    public  AnimatedExpandableListView getExpListView(){
        return expListView ;
    }
    
    public void init(){
//        expListView = navigationView.findViewById(R.id.lvExp);
//
//        // preparing list data
//        prepareListData();
//
//        adapter = new ExampleAdapter(context);
//        adapter.setData(listDataHeader);
//
//        // setting list adapter
//        expListView.setAdapter(adapter);
    }
    
    public String gs(int string){
        return context.getString(string) ;
    }

    private void prepareListData() {
        menu = new String[][]{
                {gs(R.string.mnBeranda),gs(R.string.mnMaster),gs(R.string.mnTransaksi)
                        ,gs(R.string.mnLaporan),gs(R.string.mnUtilitas),gs(R.string.mnAbout)},
                {},
                {gs(R.string.mnKategori),gs(R.string.mnSatuan),gs(R.string.mnProduk),gs(R.string.mnPelanggan),gs(R.string.mnPegawai)},
                {gs(R.string.mnTambahStok),gs(R.string.mnTopup),gs(R.string.mnPenjualan),gs(R.string.mnBayarUtang)},
                {gs(R.string.mnLapProduk),gs(R.string.mnLapPelanggan),gs(R.string.mnLapPegawai),gs(R.string.mnLapTopup)
                        ,gs(R.string.mnLapPenjualan),gs(R.string.mnLapDetail),gs(R.string.mnLapHutang),gs(R.string.mnLapBayarHutang)},
                {gs(R.string.mnBackup),gs(R.string.mnRestore),gs(R.string.mnReset),gs(R.string.mnIdentitas),gs(R.string.mnBeliFitur)
                        },
                {},
        } ;
        listDataHeader = new ArrayList<GroupItem>();

        for (int i = 0 ; i < menu[0].length ; i++){
            GroupItem item = new GroupItem() ;
            item.title = menu[0][i] ;
            for(int j = 0 ; j < menu[i+1].length ; j++){
                if (!menu[i+1][j].equals("")){
                    ChildItem child = new ChildItem() ;
                    child.title = menu[i+1][j] ;
                    item.items.add(child) ;
                }
            }
            listDataHeader.add(item) ;
        }

    }

    public void event() {
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (expListView.isGroupExpanded(groupPosition)) {
                    expListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    expListView.expandGroupWithAnimation(groupPosition);
                }
                if(menu[groupPosition+1][0].equals("")){
                    Toast.makeText(context, menu[0][groupPosition], Toast.LENGTH_SHORT).show();
                }
                return true;
            }

        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(context, menu[i+1][i1], Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String hint;
    }

    private static class ChildHolder {
        TextView title;
        TextView hint;
    }

    private static class GroupHolder {
        TextView title;
        ConstraintLayout wIcon ;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.item_menu_child, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.item_menu_group, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                holder.wIcon = convertView.findViewById(R.id.wIcon);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            if (item.title.equals(gs(R.string.mnBeranda))){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.wIcon.setBackground(context.getResources().getDrawable(R.drawable.home));
                }
            } else if (item.title.equals(gs(R.string.mnMaster))){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.wIcon.setBackground(context.getResources().getDrawable(R.drawable.master));
                }
            } else if (item.title.equals(gs(R.string.mnTransaksi))){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.wIcon.setBackground(context.getResources().getDrawable(R.drawable.transaksi));
                }
            } else if (item.title.equals(gs(R.string.mnLaporan))){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.wIcon.setBackground(context.getResources().getDrawable(R.drawable.laporan));
                }
            } else if (item.title.equals(gs(R.string.mnUtilitas))){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.wIcon.setBackground(context.getResources().getDrawable(R.drawable.utilitas));
                }
            } else if (item.title.equals(gs(R.string.mnAbout))){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.wIcon.setBackground(context.getResources().getDrawable(R.drawable.about));
                }
            }

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }
}
