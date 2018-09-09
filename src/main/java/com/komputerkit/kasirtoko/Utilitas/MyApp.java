package com.komputerkit.kasirtoko.Utilitas;

/**
 * Created by SAIF on 25/12/2017.
 */

public class MyApp {
    Utilitas utilitas ;
    Database database ;

    public MyApp(Utilitas utilitas, Database database) {
        this.utilitas = utilitas;
        this.database = database;
    }

    public String getFaktur(){
        String q = "select * from tblorder order by faktur desc" ;

        if (database.getCount(q) > 0){
            String lastFaktur = database.getValue(q,"faktur") ;

            String faktur = utilitas.intToStr(utilitas.strToInt(lastFaktur) + 1) ;
            String nol = "" ;

            for (int i = 1 ; i < 10-faktur.length() ; i++){
                nol += "0" ;
            }

            return nol + faktur ;
        } else {
            return "000000001" ;
        }
    }

    public String sqlBetweenDate(String dari,String sampai){
        String dthn = utilitas.getYear(dari) ;
        String dbln = utilitas.getMonth(dari) ;
        String dday = utilitas.getDay(dari) ;
        String sthn = utilitas.getYear(sampai) ;
        String sbln = utilitas.getMonth(sampai) ;
        String sday = utilitas.getDay(sampai) ;

        dari = dthn + dbln + dday ;
        sampai = sthn + sbln + sday ;

        String sql = " (tglmix between "+dari+" and "+sampai+") " ;
        return sql ;
    }

    public void updateSaldo(String idPelanggan,String total){
        String sql = "update tblpelanggan set saldodeposit = tblpelanggan.saldodeposit - "+total+" where idpelanggan=" + idPelanggan ;

        database.execution(sql) ;
    }

    public boolean cekRow(String tabelMaster){
        String sql = "select * from "+tabelMaster ;
        if (utilitas.getSession(Config.getIDFullVersion(),false)){
            return false ;
        } else if (database.getCount(sql) > Config.getJumMaster()){
            return true ;
        } else {
            return false ;
        }
    }
}
