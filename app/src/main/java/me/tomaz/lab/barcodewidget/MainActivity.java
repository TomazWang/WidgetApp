package me.tomaz.lab.barcodewidget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import java.util.regex.Pattern;
import me.tomaz.lab.barcodewidget.data.DataProvider;
import me.tomaz.lab.barcodewidget.utils.StringUtil;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edInput;
    private View baseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        baseView = findViewById(R.id.layout_main_base);
        edInput = (TextInputEditText) findViewById(R.id.edit_main_barcode);
        View btnUpdate = findViewById(R.id.btn_update);

        InputFilter asciiFilter = new InputFilter() {

            final String format = "[0-9a-zA-Z.$/+% ]+";
            Pattern pattern = Pattern.compile(format);

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                    Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source.subSequence(start, end).toString());

                if(pattern.matcher(builder.toString()).matches()){
                    return null; // keep original
                }else{
                    if (source.length() == 0) {
                        // on delete
                        return null; // keep original
                    }else{
                        return "";
                    }
                }

            }
        };


        edInput.setInputType(InputType.TYPE_CLASS_TEXT);
        edInput.setFilters(new InputFilter[]{
                asciiFilter
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCode();
            }
        });



    }

    private void updateCode() {
        String input = edInput.getEditableText().toString();

        if(input.length() <= 0){
            edInput.setError("請輸入條碼");
            return;
        }

        if(input.length() > 80){
            edInput.setError("字元過長");
            return;
        }

        String code = StringUtil.filterOutNotAscii(input);

        if(!input.equals(code)){
            edInput.setError("條碼不符合格式");
            return;
        }

        code = code.toUpperCase();

        DataProvider.getInstance(this).setBarcode(code);

        updateWidget();
    }

    private void updateWidget() {
        Intent intent = new Intent(this, BarcodeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // get all widgets
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), BarcodeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

        Snackbar.make(baseView, "條碼 Widget 已更新", Snackbar.LENGTH_SHORT).setAction("OK", null).show();

    }
}
