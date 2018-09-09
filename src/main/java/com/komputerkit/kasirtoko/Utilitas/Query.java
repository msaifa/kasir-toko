package com.komputerkit.kasirtoko.Utilitas;

public class Query {

    static String slct = "SELECT * FROM " ;

    public static String selectwhere(String table) {
        return Utilitas.slct + table + " WHERE ";
    }

    public static String sWhere(String key, String value){
        return key+"="+ Utilitas.quote(value) ;
    }

    public static String splitParam(String query, String[] p){
        String pecah[] = query.split("\\?") ;
        if(pecah.length > 1){
            String fix = "" ;
            if((p.length+1) != pecah.length){
                return "gagal" ;
            } else {
                int i ;
                for (i = 0; i < p.length; i++) {
                    fix += pecah[i] + Utilitas.quote(p[i]) ;
                }
                fix += pecah[i] ;

                return fix ;
            }
        } else {
            return query ;
        }
    }

}
