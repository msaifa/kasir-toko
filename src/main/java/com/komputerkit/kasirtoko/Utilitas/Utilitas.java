package com.komputerkit.kasirtoko.Utilitas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.provider.Settings.Secure;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.komputerkit.kasirtoko.R;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by SaifAlikhan on 31/05/2017.
 *
 * cara membuat Object View
 * View v = this.findViewById(android.R.id.content)
 *
 * Untuk SnackBar Mati
 * compile 'com.android.support:design:25.3.1'
 *
 * Untuk Base64 Mati
 * compile  'com.sun.xml.security:xml-security-impl:1.0'
 */

public class Utilitas {
    static String slct = "SELECT * FROM " ;
    private static final byte[] SALT = Config.getSalt().getBytes();//
    private static final String X = Utilitas.class.getSimpleName();
    static SharedPreferences sharedPreferences ;
    static SharedPreferences sharedPreferencestemp ;
    static SharedPreferences.Editor editor ;
    static SharedPreferences.Editor editortemp ;
    static Context context ;

    public Utilitas(Context context){
        this.context = context ;
        sharedPreferences = context.getSharedPreferences(Config.getSessionName(),context.MODE_PRIVATE) ;
        editor = sharedPreferences.edit() ;
        sharedPreferencestemp = context.getSharedPreferences("temp",context.MODE_PRIVATE) ;
        editortemp = sharedPreferences.edit() ;
    }

    public static String addSlashes(String s) {
        s = s.replaceAll("\\\\", "\\\\\\\\");
        s = s.replaceAll("\\n", "\\\\n");
        s = s.replaceAll("\\r", "\\\\r");
        s = s.replaceAll("\\00", "\\\\0");
        s = s.replaceAll("'", "\"");
        return s;
    }

    public static String DateFormatChanger(String FormatAsal, String FormatTujuan, String Tgl){
        SimpleDateFormat sdf = new SimpleDateFormat(FormatAsal);
        try {
            Date dEnd = sdf.parse(Tgl);
            sdf = new SimpleDateFormat(FormatTujuan);
            Tgl = sdf.format(dEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Tgl;
    }

    public static String quote(String w){
        return "\'" + addSlashes(w) + "\'" ;
    }

    public static String intToStr(int a){
        try{
            return String.valueOf(a) ;
        }catch (Exception e){
            return "0" ;
        }
    }

    public static int strToInt(String v){
        try {
            return Integer.parseInt(v) ;
        } catch (Exception e){
            return 0 ;
        }
    }

    public static double strToDouble(String v){
        try {
            return Double.parseDouble(v) ;
        }catch (Exception e){
            return 0 ;
        }
    }

    public static String doubleToStr(double d){
        try {
            return String.valueOf(d) ;
        }catch (Exception e){
            return "" ;
        }
    }

    public static String getText(View v, int id){ //cara instal //Utilitas.getText(this.findViewById(android.R.id.content), R.id.jumlah) ;
        TextView a = (TextView) v.findViewById(id) ;
        return a.getText().toString() ;
    }

    public static Boolean setText(View v, int id,String t){ //cara instal //Utilitas.setText(this.findViewById(android.R.id.content), R.id.jumlah,"ini String") ;
        TextView a = (TextView) v.findViewById(id) ;
        try {
            a.setText(t);
            return true ;
        }catch (Exception e){
            return false;
        }
    }
    public static String getSpinnerItem(View v,int idSpinner){
        try {
            Spinner s = (Spinner) v.findViewById(idSpinner) ;
            return s.getSelectedItem().toString() ;
        }catch (Exception e){
            return "" ;
        }
    }

    public static Boolean clearFocus(View v, int id){ //cara instal //Utilitas.setText(this.findViewById(android.R.id.content), R.id.jumlah,"ini String") ;
        TextView a = (TextView) v.findViewById(id) ;
        try {
            a.clearFocus();
            return true ;
        }catch (Exception e){
            return false;
        }
    }

    public static int getInt(Cursor c, String name){
        return c.getInt(c.getColumnIndex(name)) ;
    }

    public static String getDate(String type){ //Random time type : HHmmssddMMyy
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(type);
        String formattedDate = df.format(c.getTime());
        return formattedDate ;
    }

    public static String getPerfectTime(){
        Calendar c = Calendar.getInstance();
        return c.getTime().toString() ;
    }

    public static String setDateData(int year , int month, int day) {
        String bln,thn,hri ;
        if(month < 10){
            bln = "0"+ Utilitas.intToStr(month) ;
        } else {
            bln = Utilitas.intToStr(month) ;
        }

        if(day < 10){
            hri = "0"+ Utilitas.intToStr(day) ;
        } else {
            hri = Utilitas.intToStr(day) ;
        }

        return Utilitas.intToStr(year) + bln+hri;
    }

    public static String setDateNormal(int year , int month, int day) {
        String bln,thn,hri ;
        if(month < 10){
            bln = "0"+ Utilitas.intToStr(month) ;
        } else {
            bln = Utilitas.intToStr(month) ;
        }

        if(day < 10){
            hri = "0"+ Utilitas.intToStr(day) ;
        } else {
            hri = Utilitas.intToStr(day) ;
        }

        return hri+"/"+bln+"/"+ Utilitas.intToStr(year);
    }

    public static String numberFormat(String number){ // Rp. 1,000,000.00
        try{
            String hasil = "" ;
            boolean minus = false ;
            if (number.contains("-")){
                minus = true ;
                number = number.replace("-","") ;
            }
            String[] b = number.split("\\.") ;

            if(b.length == 1){
                String[] a = number.split("") ;
                int c=0 ;
                for(int i=a.length-1;i>=0;i--){
                    if(c == 3 && !TextUtils.isEmpty(a[i])){
                        hasil = a[i] + "." + hasil ;
                        c=1;
                    } else {
                        hasil = a[i] + hasil ;
                        c++ ;
                    }
                }
            } else {
                String[] a = b[0].split("") ;
                int c=0 ;
                for(int i=a.length-1;i>=0;i--){
                    if(c == 3 && !TextUtils.isEmpty(a[i])){
                        hasil = a[i] + "." + hasil ;
                        c=1;
                    } else {
                        hasil = a[i] + hasil ;
                        c++ ;
                    }
                }
                hasil+=","+b[1] ;
            }
            if (minus){
                return  "-" + hasil ;
            } else {
                return  hasil ;
            }
        }catch (Exception e){
            return  "0" ;
        }
    }

    public static String unNumberFormat(String number){
        try {
            String a = number.replace(",","-") ;
            String b = a.replace("\\.","") ;
            String c = b.replace("-",".") ;
            return c ;
        }catch (Exception e){
            return "" ;
        }
    }

    public static String dateDataToNormal(String date){
        try {
            String b1 = date.substring(4) ;
            String b2 = b1.substring(2) ;

            String m = b1.substring(0,2) ;
            String d = b2.substring(0,2) ;
            String y = date.substring(0,4) ;
            return d+"/"+m+"/"+y ;
        }catch (Exception e){
            return "ini tanggal" ;
        }
    }

    public static String getCampur(String satu,String dua){
        return satu+"__"+dua ;
    }
    public static String getCampur(String satu,String dua,String tiga){
        return satu+"__"+dua+"__"+tiga ;
    }
    public static String getCampur(String satu,String dua,String tiga, String empat){
        return satu+"__"+dua+"__"+tiga+"__"+empat ;
    }
    public static String getCampur(String satu,String dua,String tiga, String empat,String lima){
        return satu+"__"+dua+"__"+tiga+"__"+empat+"__"+lima ;
    }
    public static String getCampur(String satu,String dua,String tiga, String empat,String lima,String enam){
        return satu+"__"+dua+"__"+tiga+"__"+empat+"__"+lima+"__"+enam ;
    }

    public static String setCenter(String item){
        int leng = item.length() ;
        String hasil = "" ;
        for(int i=0 ; i<32-leng;i++){
            if((32-leng)/2+1 == i){
                hasil += item ;
            } else {
                hasil += " " ;
            }
        }
        return hasil ;
    }

    public static String getStrip(){
        String a = "" ;
        for(int i = 0 ; i < 32 ; i++){
            a+="-" ;
        }
        return a+"\n" ;
    }

    public static String setRight(String item){
        int leng = item.length() ;
        String hasil = "" ;
        for(int i=0 ; i<32-leng;i++){
            if((31-leng) == i){
                hasil += item ;
            } else {
                hasil += " " ;
            }
        }
        return hasil ;
    }

    public static String setRight(String item,int min){
        int leng = item.length() ;
        int total = 32-min ;
        String hasil = "" ;
        for(int i=0 ; i<total-leng;i++){
            if((total-leng) == i){
                hasil += item ;
            } else {
                hasil += " " ;
            }
        }
        return hasil ;
    }

    public static String getEnter(){
        return "\n" ;
    }

    public static String removeE(double value){
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        return Utilitas.numberFormat(df.format(value)) ;
    }
    public static String removeE(int value){
        String val = intToStr(value) ;
        double hasil = strToDouble(val) ;
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        return Utilitas.numberFormat(df.format(hasil)) ;
    }
    public static String removeE(String value){
        double hasil = Utilitas.strToDouble(value) ;
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        return Utilitas.numberFormat(df.format(hasil)) ;
    }

    public static Boolean copyFile(String pIn, String pOut, String name){
        InputStream in ;
        OutputStream out ;

        try{
            File file = new File(pOut) ;
            if(!file.exists()){
                file.mkdirs() ;
            }

            in = new FileInputStream(pIn+name) ;
            out = new FileOutputStream(pOut+name) ;

            byte[] buffer = new byte[1024] ;
            int read ;
            while((read = in.read(buffer)) != -1){
                out.write(buffer,0,read) ;
            }

            in.close();
            in = null ;

            out.flush();
            out.close();
            out = null ;

            return true ;
        }catch (Exception e){
            return false ;
        }
    }

    public static Boolean deleteFile(String uriFile){
        try{
            File file = new File(uriFile) ;
            if (file.delete()){
                return true ;
            } else {
                return false ;
            }
        } catch (Exception e){
            return false ;
        }
    }

    public static Boolean renameFile(String path, String namaLama, String namaBaru){
        try{
            File a = new File(path+namaLama) ;
            File b = new File(path+namaBaru) ;
            a.renameTo(b) ;

            return true ;
        }catch (Exception e){
            return false ;
        }
    }

    public static String getDeviceID(ContentResolver c){
        return Secure.getString(c, Secure.ANDROID_ID);
    }

    public static void copy(Context context, String text){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", text);
        clipboard.setPrimaryClip(clip);
    }

    public static String paste(Context context){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = clipboard.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String text = item.getText().toString();
        return text ;
    }

    public static String getEncrypted(String plainText) {

        if (plainText == null) {
            return null;
        }

        Key salt = getSalt();

        try {
            Cipher cipher = Cipher.getInstance(Config.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, salt);
            byte[] encodedValue = cipher.doFinal(plainText.getBytes());
            return Base64.encode(encodedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Failed to encrypt data");
    }

    public static String getDecrypted(String encodedText) {

        if (encodedText == null) {
            return null;
        }

        Key salt = getSalt();
        try {
            Cipher cipher = Cipher.getInstance(Config.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, salt);
            byte[] decodedValue = Base64.decode(encodedText);
            byte[] decValue = cipher.doFinal(decodedValue);
            return new String(decValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static Key getSalt() {
        return new SecretKeySpec(SALT, Config.getAlgorithm());
    }

    public static void getSnackBar(View view, int ID, String Teks){
        try{
            ConstraintLayout wadah = (ConstraintLayout) view.findViewById(ID) ;
            Snackbar snackbar = Snackbar
                    .make(wadah,Teks, Snackbar.LENGTH_LONG);
            snackbar.show();
        }catch (Exception e){

        }
    }

    public static void getSnackBar(ConstraintLayout wadah, String teks){
        try{
            Snackbar snackbar = Snackbar
                    .make(wadah,teks, Snackbar.LENGTH_LONG);
            snackbar.show();
        }catch (Exception e){

        }
    }

    public static void getSnackBar(RelativeLayout wadah, String teks){
        try{
            Snackbar snackbar = Snackbar
                    .make(wadah,teks, Snackbar.LENGTH_LONG);
            snackbar.show();
        }catch (Exception e){

        }
    }

    public static void getSnackBar(LinearLayout wadah, String teks){
        try{
            Snackbar snackbar = Snackbar
                    .make(wadah,teks, Snackbar.LENGTH_LONG);
            snackbar.show();
        }catch (Exception e){

        }
    }

    public static Boolean isEmpty(View view, int id){
        try{
            EditText editText = (EditText) view.findViewById(id) ;
            if (editText.getText().toString().equals("")){
                return true ;
            } else {
                return false ;
            }
        }catch (Exception e){
            return false ;
        }
    }

    public static Boolean isEmpty(String teks){
        try{
            if (teks.equals("")){
                return true ;
            } else {
                return false ;
            }
        }catch (Exception e){
            return false ;
        }
    }

    public static String getSession(String name,String defaul){
        return sharedPreferences.getString(name, defaul) ;
    }
    public static void setSession(String name,String defaul){
        editor.putString(name,defaul) ;
        editor.apply();
    }
    public static Boolean getSession(String name,Boolean defaul){
        return sharedPreferences.getBoolean(name, defaul) ;
    }
    public static void setSession(String name,Boolean defaul){
        editor.putBoolean(name,defaul) ;
        editor.apply();
    }
    public static String getSessionTemp(String name,String defaul){
        return sharedPreferencestemp.getString(name, defaul) ;
    }
    public static void setSessionTemp(String name,String defaul){
        editortemp.putString(name,defaul) ;
        editortemp.apply();
    }
    public static void toast(String teks){
        Toast.makeText(context, teks, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int teks){
        Toast.makeText(context, intToStr(teks), Toast.LENGTH_SHORT).show();
    }

    public String getToken(String username){
        try {
            Calendar calendar = Calendar.getInstance() ;
            String tahun = String.valueOf(calendar.get(Calendar.YEAR));
            String bulan = String.valueOf(calendar.get(Calendar.MONTH));
            String tanggal = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String jam = String.valueOf(calendar.get(Calendar.HOUR));
            String menit = String.valueOf(calendar.get(Calendar.MINUTE));
            String detik = String.valueOf(calendar.get(Calendar.SECOND));
            String primary = getEncrypted("komputerkit"+randomString(1)) ;
            String user = getEncrypted(username+randomString(1)) ;
            String now = tahun+"-"+cekNumber(bulan)+"-"+cekNumber(tanggal)
                    + "||" +cekNumber(jam)+":"+cekNumber(menit)+":"+cekNumber(detik) ;

            String token = randomString(8) + primary + randomString(4) + getEncrypted(now) +
                    randomString(4) + user;

            return getEncrypted(token) ;
        } catch (Exception e){
            return "Gagal Membuat Token" ;
        }
    }

    public String getToken(){
        try {
            Calendar calendar = Calendar.getInstance() ;
            String tahun = String.valueOf(calendar.get(Calendar.YEAR));
            String bulan = String.valueOf(calendar.get(Calendar.MONTH));
            String tanggal = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String jam = String.valueOf(calendar.get(Calendar.HOUR));
            String menit = String.valueOf(calendar.get(Calendar.MINUTE));
            String detik = String.valueOf(calendar.get(Calendar.SECOND));
            String primary = getEncrypted("komputerkit"+randomString(1)) ;
            String user = getEncrypted(getSession("idUser","")+randomString(1)) ;
            String now = tahun+"-"+cekNumber(bulan)+"-"+cekNumber(tanggal)
                    + "||" +cekNumber(jam)+":"+cekNumber(menit)+":"+cekNumber(detik) ;

            String token = randomString(8) + primary + randomString(4) + getEncrypted(now) +
                    randomString(4) + user;

            return getEncrypted(token) ;
        } catch (Exception e){
            return "Gagal Membuat Token" ;
        }
    }

    public static String randomString(int length) {
        char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    public String cekNumber(String angka){
        if (Integer.parseInt(angka) < 10){
            return "0"+ angka;
        } else {
            return angka ;
        }
    }

    public static String getAge(int year, int month, int day){
        try{
            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            month-- ;
            dob.set(year, month , day);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age-- ;
            }

            if (age >= 0){
                Integer ageInt = new Integer(age);
                String ageS = ageInt.toString();

                return ageS  ;
            } else {
                return "Umur yang anda masukkan salah" ;
            }
        }catch(Exception e){ return "gagal" ;}
    }

    public static String getAgeMonth(int year, int month, int day){
        try{
            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            month-- ;
            dob.set(year, month, day);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            int mon = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH) ;


            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
            }

            if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
                mon--;
            }

            if (today.get(Calendar.YEAR) - dob.get(Calendar.YEAR) == 0 && mon > 0){
                age = 0 ;
            } else if (today.get(Calendar.YEAR) - dob.get(Calendar.YEAR) == 1 && mon < 0){
                age = 1 ;
            }

            Integer ageInt =  (age * 12 ) + mon ;
            String ageS = ageInt.toString();

            if (today.get(Calendar.YEAR) == dob.get(Calendar.YEAR)&& ageS.equals("12")){
                return "0" ;
            } else {
                if (Integer.parseInt(ageS) >= 0){
                    return ageS ;
                } else {
                    return "Umur yang anda masukkan salah" ;
                }
            }
        }catch(Exception e){
            return "gagal" ;
        }
    }

    public static String getAgeMonth(String date){
        try{
            int year = strToInt(getYear(date)) ;
            int month= strToInt(getMonth(date)) - 1;
            int day = strToInt(getDay(date)) ;

            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();

            dob.set(year, month, day);

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            int mon = today.get(Calendar.MONTH) - dob.get(Calendar.MONTH) ;


            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
            }

            if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
                mon--;
            }

            if (today.get(Calendar.YEAR) - dob.get(Calendar.YEAR) == 0 && mon > 0){
                age = 0 ;
            } else if (today.get(Calendar.YEAR) - dob.get(Calendar.YEAR) == 1 && mon < 0){
                age = 1 ;
            }

            Integer ageInt =  (age * 12 ) + mon ;
            String ageS = ageInt.toString();

            if (today.get(Calendar.YEAR) == dob.get(Calendar.YEAR)&& ageS.equals("12")){
                return "0" ;
            } else {
                if (Integer.parseInt(ageS) >= 0){
                    return ageS ;
                } else {
                    return "Umur yang anda masukkan salah" ;
                }
            }
        }catch(Exception e){
            return "gagal" ;
        }
    }

    public static int getHour(String time){
        try{
            String[] jam = time.split(":") ;
            return Utilitas.strToInt(jam[0]) ;
        } catch(Exception e){
            return 0 ;
        }
    }

    public static int getMinute(String time){
        try{
            String[] jam = time.split(":") ;
            return Utilitas.strToInt(jam[1]) ;
        } catch(Exception e){
            return 99 ;
        }
    }

    public static String getDay(String date){
        try{
            String[] det = date.split("-") ;
            int day = strToInt(det[0]) ;

            if (day < 10){
                return "0"+intToStr(day) ;
            } else {
                return det[0] ;
            }

        } catch(Exception e){
            return "0" ;
        }
    }

    public static String getMonth(String date){
        try{
            String[] det = date.split("-") ;
            int month = strToInt(det[1]) ;

            if (month < 10){
                return "0"+intToStr(month) ;
            } else {
                return det[1] ;
            }
        } catch(Exception e){
            return "0" ;
        }
    }

    public static String getYear(String date){
        try{
            String[] det = date.split("-") ;
            int year = strToInt(det[2]) ;

            if (year < 10){
                return "0"+intToStr(year) ;
            } else {
                return det[2] ;
            }
        } catch(Exception e){
            return "0" ;
        }
    }

    public void showDialog(String teks, final DialogInterface.OnClickListener listener){
        AlertDialog.Builder alertDialog ;
        AlertDialog alert ;
        alertDialog = new AlertDialog.Builder(context) ;
        alertDialog.setMessage(teks)
                .setCancelable(false)
                .setPositiveButton("Iya", listener)
                .setNegativeButton("Tidak", null) ;

        alert = alertDialog.create() ;
        alert.show() ;
    }

    public void showDialog(String teks, final DialogInterface.OnClickListener onClickYes,final DialogInterface.OnClickListener onClickNo){
        AlertDialog.Builder alertDialog ;
        AlertDialog alert ;
        alertDialog = new AlertDialog.Builder(context) ;
        alertDialog.setMessage(teks)
                .setCancelable(false)
                .setPositiveButton("Iya", onClickYes)
                .setNegativeButton("Tidak", onClickNo) ;

        alert = alertDialog.create() ;
        alert.show() ;
    }

    public void showAlert(String teks, final DialogInterface.OnClickListener listener){
        AlertDialog.Builder alertDialog ;
        AlertDialog alert ;
        alertDialog = new AlertDialog.Builder(context) ;
        alertDialog.setMessage(teks)
                .setCancelable(false)
                .setPositiveButton("OK", listener) ;

        alert = alertDialog.create() ;
        alert.show() ;
    }

    public PopupMenu showPopUp(View anchor,int menu){
        final PopupMenu popupMenu = new PopupMenu(context,anchor) ;
        popupMenu.getMenuInflater().inflate(menu,popupMenu.getMenu());

        popupMenu.show();

        return popupMenu ;
    }

    public void showDateDialog(final View v, final int et) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int thn, int bln, int day) {
                EditText temp = v.findViewById(et) ;
                if (context.getString(R.string.typeDate).equals("dd-MM-yyyy")){
                    bln++ ;
                    String t = intToStr(thn);
                    String b = intToStr(bln);
                    String h = intToStr(day);

                    temp.setText(h + "-" + b + "-" + t);
                } else {
                    temp.setText("Harap Ubah Tipe Tanggal Di Date Picker");
                }
            }
        };

        Dialog datePciker = new DatePickerDialog(context, date, year, month, day);
        datePciker.show();
    }

    public void showDateDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int thn, int bln, int day) {
                if (context.getString(R.string.typeDate).equals("dd-MM-yyyy")){
                    bln++ ;
                    String t = intToStr(thn);
                    String b = intToStr(bln);
                    String h = intToStr(day);

                    editText.setText(h + "-" + b + "-" + t);
                } else {
                    editText.setText("Harap Ubah Tipe Tanggal Di Date Picker");
                }
            }
        };

        Dialog datePciker = new DatePickerDialog(context, date, year, month, day);
        datePciker.show();
    }

    public void showDateDialog(final TextView textView) {
        int thn = strToInt(getYear(getDate(context.getString(R.string.typeDate)))) ;
        int bln = strToInt(getMonth(getDate(context.getString(R.string.typeDate)))) ;
        int day = strToInt(getDay(getDate(context.getString(R.string.typeDate)))) ;

        if (bln == 1){
            bln = 12 ;
        } else {
            bln = bln-1 ;
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int thn, int bln, int day) {
                if (context.getString(R.string.typeDate).equals("dd-MM-yyyy")){
                    bln++ ;
                    String t = intToStr(thn);
                    String b = intToStr(bln);
                    String h = intToStr(day);

                    textView.setText(h + "-" + b + "-" + t);
                } else {
                    textView.setText("Harap Ubah Tipe Tanggal Di Date Picker");
                }
            }
        };

        Dialog datePciker = new DatePickerDialog(context, date, thn, bln, day);
        datePciker.show();
    }

    public String getString(int nameString){
        return context.getString(nameString) ;
    }

    public static void setCustomFont(View v, int EditText, String font){
        EditText et = v.findViewById(EditText) ;
        Typeface typeface =Typeface.createFromAsset(context.getAssets(),font) ;
        et.setTypeface(typeface);
    }

    public static void setCustomFont(EditText editText, String font){
        Typeface typeface =Typeface.createFromAsset(context.getAssets(),font) ;
        editText.setTypeface(typeface);
    }

    public static void setCustomFont(TextView textView, String font){
        Typeface typeface =Typeface.createFromAsset(context.getAssets(),font) ;
        textView.setTypeface(typeface);
    }

    public static String sumInt(String satu,String dua){
        try{
            return intToStr(strToInt(satu) * strToInt(dua)) ;
        } catch (Exception e){
            return "0" ;
        }
    }

    public static String sumDouble(String satu,String dua){
        try{
            return doubleToStr(strToDouble(satu) * strToDouble(dua)) ;
        } catch (Exception e){
            return "0" ;
        }
    }

    public static void sumChar(EditText editText, final TextView textView, final String jumlah){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(intToStr(s.length()) + "/" + jumlah);
            }
        });
    }

    public static void sumChar(final View v, int editText, final int textView , final String jumlah){
        EditText editText1 = v.findViewById(editText) ;
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setText(v,textView,intToStr(s.length()) + "/" + jumlah);
            }
        });
    }

    public static void autoNumber(View view,int editText){
        final EditText et = view.findViewById(editText) ;
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et.removeTextChangedListener(this);
                try{
                    String temp = s.toString().replace(".","") ;
                    String nf = numberFormat(temp) ;
                    et.setText(nf) ;
                    et.setSelection(nf.length());
                }catch (Exception e){

                }
                et.addTextChangedListener(this);
            }
        });
    }

    public static String penjumlahan(String valueA, String valueB){
        try{
            int jum = strToInt(valueA) + strToInt(valueB) ;
            return intToStr(jum) ;
        }catch (Exception e){
            return "0" ;
        }
    }

    public static String perkalian(String valueA, String valueB){
        try{
            int jum = strToInt(valueA) * strToInt(valueB) ;
            return intToStr(jum) ;
        }catch (Exception e){
            return "0" ;
        }
    }

}

