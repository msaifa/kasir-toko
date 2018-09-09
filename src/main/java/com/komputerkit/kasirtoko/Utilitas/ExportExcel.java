package com.komputerkit.kasirtoko.Utilitas;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


/**
 * Created by SAIF on 20/12/2017.
 */

public class ExportExcel {
    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat timesBold;
    private WritableCellFormat times;
    WritableSheet sheet ;
    WorkbookSettings wbSettings ;
    WritableWorkbook workbook ;

    int row = 0 ;
    int coloumn ;

    String path,query ;
    String[] kolom ;
    String[] kolom2 ;
    Context context ;

    Database db ;

    public ExportExcel(Context context,String query, String path, String[] kolom) throws WriteException, IOException {
        this.path = path;
        this.kolom = kolom;
        this.context = context ;
        this.query = query ;

        db = new Database(context) ;

        init() ;
        create() ;
        Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
    }

    public ExportExcel(Context context,String query, String path, String[] colTitle,String[] colSQL) throws WriteException, IOException {
        this.path = path;
        this.kolom = colTitle;
        this.kolom2 = colSQL;
        this.context = context ;
        this.query = query ;

        db = new Database(context) ;

        init() ;
        create2() ;
        Toast.makeText(context, "Success Export Excel", Toast.LENGTH_SHORT).show();
    }

    public void create() throws IOException, WriteException {

        Cursor c = db.select(query) ;
        if (db.getCount(query) > 0){
            File file = new File(path + ".xls") ;
            wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));

            workbook = Workbook.createWorkbook(file, wbSettings);
            workbook.createSheet("Report", 0);
            sheet = workbook.getSheet(0) ;

            setHeader(kolom.length+1) ;
            excelNextLine(2) ;

            addCell("No.",0,row);
            for(int i = 0 ; i < kolom.length ; i++){
                addCell(kolom[i],(i+1),row);
            }
            row++ ;

            int no = 0 ;
            while(c.moveToNext()){
                addCell(intToStr(no+1),no,row);
                no++ ;
                for(int i = 0 ; i < kolom.length ; i++){
                    addCell(getString(c,kolom[i]),no++,row);
                }
                row++ ;
            }

            workbook.write();
            workbook.close();
        } else {

        }
    }

    public void create2() throws IOException, WriteException {

        Cursor c = db.select(query) ;
        if (db.getCount(query) > 0 && kolom.length == kolom2.length){
            File file = new File(path + ".xls") ;
            wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));

            workbook = Workbook.createWorkbook(file, wbSettings);
            workbook.createSheet("Report", 0);
            sheet = workbook.getSheet(0) ;

            setHeader(kolom.length+1) ;
            excelNextLine(2) ;

            mergeCells("No.",0,1,row,true);
            for(int i = 0 ; i < kolom.length ; i++){
                mergeCells(kolom[i],i+1,1,row,true);
            }
            row++ ;

            int no = 1 ;
            while(c.moveToNext()){
                int col = 0 ;
                addCell(intToStr(no++),col++,row);
                for(int i = 0 ; i < kolom2.length ; i++){
                    String[] temp = kolom2[i].split("__") ;
                    if (temp.length == 2){
                        if (temp[1].equals("float")){
                            addCell(removeE(getString(c,temp[0])),col++,row);
                        } else if (temp[1].equals("stok")){
                            String[] stok = getString(c,temp[0]).split("sisa") ;
                            addCell(stok[0] + " Lebih " + stok[1],col++,row);
                        }
                    } else {
                        addCell(getString(c,kolom2[i]),col++,row);
                    }
                }
                row++ ;
            }

            workbook.write();
            workbook.close();
        } else {

        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void init() throws WriteException {
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        times = new WritableCellFormat(times10pt);
        times.setWrap(true);
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        timesBoldUnderline.setWrap(true);

        WritableFont times10ptBold = new WritableFont(
                WritableFont.TIMES, 12, WritableFont.BOLD, false);
        timesBold = new WritableCellFormat(times10ptBold);
        timesBold.setWrap(true);
    }

    public void mergeCells(String value, int coloumn, int totalColoumns, int row) throws WriteException {
        Label label;
        WritableCellFormat newFormat = new WritableCellFormat(times) ;
        newFormat.setAlignment(Alignment.CENTRE) ;
        label = new Label(coloumn, row, value, newFormat) ;
        sheet.addCell(label);
        sheet.mergeCells(coloumn,row,(coloumn+totalColoumns-1),row) ; // parameters -> col1,row1,col2,row2
    }

    public void mergeCells(String value, int coloumn, int totalColoumns,int row,boolean boldFont) throws WriteException {
        Label label;
        WritableCellFormat newFormat ;

        if(boldFont){
            newFormat = new WritableCellFormat(timesBold) ;
        } else {
            newFormat = new WritableCellFormat(times) ;
        }

        newFormat.setAlignment(Alignment.CENTRE) ;
        label = new Label(coloumn, row, value, newFormat) ;
        sheet.addCell(label);
        sheet.mergeCells(coloumn,row,(coloumn+totalColoumns-1),row) ; // parameters -> col1,row1,col2,row2
    }

    public void addCell(String value, int coloumn, int row) throws WriteException {
        Label label;
        label = new Label(coloumn, row, value, times);
        sheet.addCell(label);
    }

    public void addCell(String value, int coloumn, int row, boolean boldFont) throws WriteException {
        Label label;
        if(boldFont){
            label = new Label(coloumn, row, value, timesBold);
        } else {
            label = new Label(coloumn, row, value, times);
        }
        sheet.addCell(label);
    }

    public void addCell(String value, int coloumn, int row, WritableCellFormat cellFormat) throws WriteException {
        Label label;
        label = new Label(coloumn, row, value, cellFormat);
        sheet.addCell(label);
    }

    public void setHeader(int JumlahKolom){
        try {
            String q = "select * from tblidentitas" ;

            mergeCells(db.getValue(q,"nama"),0,JumlahKolom,row++,true);
            mergeCells(db.getValue(q,"alamat"),0,JumlahKolom,row++,true);
            mergeCells(db.getValue(q,"telp"),0,JumlahKolom,row++,true);
        }catch (Exception e){

        }
    }

    private void addLabel(String s,int JumlahKolom)
            throws WriteException, RowsExceededException {
        Label label;
        JumlahKolom-- ;
        WritableCellFormat newFormat = null;
        newFormat = new WritableCellFormat(timesBold) ;
        newFormat.setAlignment(Alignment.CENTRE) ;
        label = new Label(0, row, s, newFormat) ;
        sheet.addCell(label);
        sheet.mergeCells(0,row,JumlahKolom,row) ; // parameters -> col1,row1,col2,row2
        row++ ;
    }

    public Boolean excelNextLine(int total){
        try {
            for (int i = 0 ; i < total ; i++){
                addCell("",0,row++);
            }
            return true ;
        }catch (Exception e){
            return false ;
        }
    }

    public static String intToStr(int a){
        try{
            return String.valueOf(a) ;
        }catch (Exception e){
            return "0" ;
        }
    }

    public static String getString(Cursor c, String name){
        try{
            return c.getString(c.getColumnIndex(name)) ;
        }catch (Exception e){
            return "Failed Get String" ;
        }
    }

    public static String removeE(String value){
        double hasil = Utilitas.strToDouble(value) ;
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        return Utilitas.numberFormat(df.format(hasil)) ;
    }
}
