package me.tomaz.lab.barcodewidget.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TomazWang on 10/07/2017.
 */

public class DataProvider {

    public static final String SP_NAME = "me.tomaz.lab.barcodewidget";
    public static final String SP_KEY_BARCODE = "SP_KEY_BARCODE";

    private static DataProvider sInstance;
    private final Context context;

    public static DataProvider getInstance(Context context){
        if(DataProvider.sInstance == null){
            sInstance = new DataProvider(context);
        }
        return sInstance;
    }

    public DataProvider(Context context) {
        this.context = context;
    }


    public String getBarcode(){
        return getSp().getString(SP_KEY_BARCODE,"0000");
    }


    public void setBarcode(String code){
        SharedPreferences.Editor editor =getSp().edit();
        editor.putString(SP_KEY_BARCODE, code);
        editor.apply();
    }



    private SharedPreferences getSp(){
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }



}
