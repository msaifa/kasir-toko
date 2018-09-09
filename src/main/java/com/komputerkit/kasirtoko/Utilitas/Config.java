package com.komputerkit.kasirtoko.Utilitas;

import android.os.Environment;

/**
 * Created by SaifAlikhan on 31/05/2017.
 */

public class Config {
    private static Boolean inAppPurchase = true ;
    private static String AppName   = "KasirToko" ;
    private static String sessionName = "AppData" ;
    private static String defaultPath = Environment.getExternalStorageDirectory().toString() + "/Download/";

    private static final String BASE_URL = "" ;
    private static String layananAPI = "" ;
    private static final String ALGORITHM = "AES";
    private static final String Salt = "E/2uUecVAhoehk/Nlumvzw==" ; ////*SALT diatas Dari encrypt "komputerkit" dengan salt 'komputerkit-2017'*/
    private static final String BASE64ENCODE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhR6b54qQs/Z3B91egCziZgm9s+ZUDAgtppEk6ODBJDhmL2xQElosOJG/Ylg8Th9vK4sGBSorph7aJxCD4yUel6wZvfR9XiN5wn1K7NNPPsCgorEMYQQ2X6GCjloG4hksTiL0ExhB8SpoA6eZqy8St0yowsd+JruIVlumrbJcTxuUcW9S9q1IWNJZaqGK599PjDS6q1e6YGkkT9jEtmkCMKv9XYZZ9yQ8bQzYDuShCU5iB1U2VCd7a8MsSuaC7hyNZsn05SAxNaoSZ8hSM//1my3lsp8Jwmerx1LdSOhv3x05CqKv8KJm1C2vt1Hj70TzE1WphSWtko7lHpNXkDdITwIDAQAB" ;

    private static final String IDFullVersion = "com.komputerkit.kasirtoko.fullversion" ;
//    private static final String IDFullVersion = "android.test.purchased" ;

    private static final int jumMaster = 11 ;
















    public static String getDefaultPath() {
        return defaultPath;
    }

    public static String getBASE64ENCODE() {
        return BASE64ENCODE;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setInAppPurchase(Boolean inAppPurchase) {
        Config.inAppPurchase = inAppPurchase;
    }

    public static void setAppName(String appName) {
        AppName = appName;
    }

    public static void setLayananAPI(String layananAPI) {
        Config.layananAPI = layananAPI;
    }

    public static void setSessionName(String sessionName) {
        Config.sessionName = sessionName;
    }

    public static Boolean getInAppPurchase() {
        return inAppPurchase;
    }

    public static String getAppName() {
        return AppName;
    }

    public static String getLayananAPI() {
        return layananAPI;
    }

    public static String getSessionName() {
        return sessionName;
    }

    public static String getALGORITHM() {
        return ALGORITHM;
    }

    public static String getSalt(){
        return Salt ;
    }

    public static String getAlgorithm(){
        return ALGORITHM ;
    }

    public static String getIDFullVersion() {
        return IDFullVersion;
    }

    public static int getJumMaster() {
        return jumMaster;
    }
}
