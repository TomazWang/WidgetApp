package me.tomaz.lab.barcodewidget.utils;

/**
 * Created by TomazWang on 10/07/2017.
 */

public class StringUtil {

    public static String filterOutNotAscii(String str) {
        return str.replaceAll("[^\\x20-\\x7E]+", "").trim().replace("\n", "");
    }
}
