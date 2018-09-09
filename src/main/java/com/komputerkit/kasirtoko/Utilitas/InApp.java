package com.komputerkit.kasirtoko.Utilitas;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.komputerkit.kasirtoko.R;

/**
 * Created by SAIF on 10/01/2018.
 */

public class InApp implements BillingProcessor.IBillingHandler{

    String licenseKey ;
    Context context ;
    BillingProcessor bp ;
    Utilitas utilitas ;

    public InApp(Context context) {
        this.context = context;
        licenseKey = Config.getBASE64ENCODE();
        bp = new BillingProcessor(context,licenseKey,this) ;
        utilitas = new Utilitas(context) ;
    }

    public void bayar(Activity activity,String productID){
        bp.purchase(activity,productID) ;
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        utilitas.setSession(productId,true);
        utilitas.toast(context.getString(R.string.iPaySuccess));
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }
}
