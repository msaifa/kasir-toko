package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 20/01/2018.
 */

public class TblPegawai {

    String idpegawai,pegawai,alamatpegawai,nohppegawai ;

    public TblPegawai(String idpegawai, String pegawai, String alamatpegawai, String nohppegawai) {
        this.idpegawai = idpegawai;
        this.pegawai = pegawai;
        this.alamatpegawai = alamatpegawai;
        this.nohppegawai = nohppegawai;
    }

    public String getIdpegawai() {
        return idpegawai;
    }

    public String getPegawai() {
        return pegawai;
    }

    public String getAlamatpegawai() {
        return alamatpegawai;
    }

    public String getNohppegawai() {
        return nohppegawai;
    }
}
