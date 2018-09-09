package com.komputerkit.kasirtoko.Utilitas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SAIF on 19/12/2017.
 */

public class Database extends SQLiteOpenHelper{
    Context context ;
    SQLiteDatabase db ;

    public Database(Context context) {
        super(context, Config.getAppName(), null, 1);
        this.context = context ;
        db = this.getWritableDatabase() ;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean execution(String query){
        try{
            db.execSQL(query);
            return true ;
        }catch (Exception e){
            return false ;
        }
    }

    public Cursor select(String query){
        try{
            return db.rawQuery(query,null) ;
        }catch (Exception e){
            return null ;
        }
    }

    public static String getString(Cursor c, String name){
        try{
            return c.getString(c.getColumnIndex(name)) ;
        }catch (Exception e){
            return "Failed Get String" ;
        }
    }

    public int getCount(String query){
        try{
            Cursor c = select(query) ;
            return c.getCount() ;
        }catch (Exception e){
            return 0 ;
        }
    }

    public String getValue(String query,String column){
        try{
            Cursor c = select(query) ;
            c.moveToNext() ;
            return getString(c,column) ;
        }catch (Exception e){
            return "" ;
        }
    }

    public ArrayList fillArray(String query,String[] column){
        try{
            ArrayList arrayList = new ArrayList() ;
            Cursor c = select(query) ;

            if(getCount(query) > 0){
                while(c.moveToNext()){
                    int no = 0 ;
                    String campur = "" ;
//                    for (int i = 0 ; i < column.length ; i++){
//                        if (no == 0){
//                            campur = getString(c,column[i]) ;
//                        } else {
//                            campur += "__"+getString(c,column[i]) ;
//                        }
//                    }
                    arrayList.add(campur) ;
                }
                return arrayList ;
            } else {
                return arrayList ;
            }
        } catch (Exception e){
            return null ;
        }
    }
}
