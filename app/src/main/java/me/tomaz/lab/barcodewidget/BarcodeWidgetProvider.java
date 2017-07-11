package me.tomaz.lab.barcodewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import me.tomaz.lab.barcodewidget.data.DataProvider;
import me.tomaz.lab.barcodewidget.utils.BarcodeUtils;

/**
 * Implementation of App Widget functionality.
 */
public class BarcodeWidgetProvider extends AppWidgetProvider {
    private static final String TAG = BarcodeWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        String code = DataProvider.getInstance(context).getBarcode();
        Log.d(TAG, "updateAppWidget: code = "+code);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bar_code_widget);
        Log.d(TAG, "updateAppWidget: set text");
        views.setTextViewText(R.id.txt_widget_code, code);


        int width = (int) (180 * context.getResources().getDisplayMetrics().density);
        int height = (int) (40 * context.getResources().getDisplayMetrics().density);

        Bitmap bmp = BarcodeUtils.getBarcodeBmp(code, width, height);

        if(bmp != null) {
            views.setImageViewBitmap(R.id.img_widget_barcode, bmp);
        }else{
            Log.w(TAG, "updateAppWidget: bmp = null");
        }


        // on click
        Intent configIntent = new Intent(context, MainActivity.class);
        configIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        views.setOnClickPendingIntent(R.id.img_widget_barcode, configPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

