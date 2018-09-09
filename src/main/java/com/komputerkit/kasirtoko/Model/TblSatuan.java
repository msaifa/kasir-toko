package com.komputerkit.kasirtoko.Model;

/**
 * Created by msaifa on 25/02/2018.
 */

public class TblSatuan {

    String satuanbesar, nilaibesar, satuankecil, nilaikecil ;

    public TblSatuan(String satuanbesar, String nilaibesar, String satuankecil, String nilaikecil) {
        this.satuanbesar = satuanbesar;
        this.nilaibesar = nilaibesar;
        this.satuankecil = satuankecil;
        this.nilaikecil = nilaikecil;
    }

    public String getSatuanbesar() {
        return satuanbesar;
    }

    public String getNilaibesar() {
        return nilaibesar;
    }

    public String getSatuankecil() {
        return satuankecil;
    }

    public String getNilaikecil() {
        return nilaikecil;
    }
}
