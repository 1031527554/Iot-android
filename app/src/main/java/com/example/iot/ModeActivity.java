package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ModeActivity extends AppCompatActivity {
    Spinner sp;
    DBManager dbManager;
    TextView temp,soilTemp,co2,humidity,soilHumidity,light;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        sp = findViewById(R.id.spinner);
        temp=findViewById(R.id.temp);
        soilTemp=findViewById(R.id.soilTemp);
        co2 = findViewById(R.id.co2);
        soilHumidity = findViewById(R.id.soilHumidity);
        light = findViewById(R.id.light);
        spinner();


    }

    private void spinner(){
        dbManager = new DBManager(this);
        dbManager.openDatabase();
        SQLiteDatabase sql = dbManager.getDatabase();
        Cursor cursor =  sql.rawQuery(" SELECT modName FROM mod ",null);
        final List<String> data= new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                data.add(cursor.getString(cursor.getColumnIndex("modName")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        dbManager.closeDatabase();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,data);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(spinnerAdapter);

        sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickName = ((TextView)view).getText().toString();
                dbManager.openDatabase();
                SQLiteDatabase sql = dbManager.getDatabase();
                Cursor cursor =  sql.rawQuery(" SELECT * FROM mod WHERE modName=? ",new String[]{clickName});
                cursor.moveToFirst();
                temp.setText("空气温度:  "+cursor.getString(cursor.getColumnIndex("temp"))+"℃");
                soilTemp.setText("空气湿度:  "+cursor.getString(cursor.getColumnIndex("soilTemp"))+"℃");
                humidity.setText("空气湿度:  "+cursor.getString(cursor.getColumnIndex("humidity"))+"%");
                soilHumidity.setText("土壤湿度:  "+cursor.getString(cursor.getColumnIndex("soilHumidity"))+"%");
                co2.setText("co2浓度:  "+cursor.getString(cursor.getColumnIndex("co2"))+"%");
                light.setText("光照强度："+cursor.getString(cursor.getColumnIndex("light")));
                DataState.setTemp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("temp"))));
                DataState.setSoiltemp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soilTemp"))));
                DataState.setHuidity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("humidity"))));
                DataState.setSoilhumidity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soilHumidity"))));
                DataState.setCo2(Integer.parseInt(cursor.getString(cursor.getColumnIndex("co2"))));
                DataState.setLight(Integer.parseInt(cursor.getString(cursor.getColumnIndex("light"))));
                cursor.close();

                Cursor cursor_w =  sql.rawQuery(" SELECT * FROM woring WHERE modName=? ",new String[]{clickName});
                cursor_w.moveToFirst();
                WoringState.setTemp(Integer.parseInt(cursor_w.getString(cursor_w.getColumnIndex("temp"))));
                WoringState.setSoiltemp(Integer.parseInt(cursor_w.getString(cursor_w.getColumnIndex("soilTemp"))));
                WoringState.setHuidity(Integer.parseInt(cursor_w.getString(cursor_w.getColumnIndex("humidity"))));
                WoringState.setSoilhumidity(Integer.parseInt(cursor_w.getString(cursor_w.getColumnIndex("soilHumidity"))));
                WoringState.setCo2(Integer.parseInt(cursor_w.getString(cursor_w.getColumnIndex("co2"))));
                WoringState.setLight(Integer.parseInt(cursor_w.getString(cursor_w.getColumnIndex("light"))));
                cursor_w.close();
                dbManager.closeDatabase();
            }
        });
    }
}
