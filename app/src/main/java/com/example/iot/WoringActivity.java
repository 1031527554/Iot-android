package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class WoringActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView temp,soilTemp,co2,humidity,soilHumidity,light;
    private Button temp_add,temp_sub,soilTemp_add,soilTemp_sub,co2_add,co2_sub,humidity_add,humidity_sub,soilHumidity_add,soilHumidity_sub,light_add,light_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woring);

        temp = findViewById(R.id.temp);
        soilTemp = findViewById(R.id.soilTemp);
        humidity = findViewById(R.id.humidity);
        soilHumidity = findViewById(R.id.soilHumidity);
        light = findViewById(R.id.light);
        co2 = findViewById(R.id.co2);
        show();
        temp_add = findViewById(R.id.temp_add);
        temp_add.setOnClickListener(this);
        temp_sub = findViewById(R.id.temp_sub);
        temp_sub.setOnClickListener(this);
        humidity_add = findViewById(R.id.humidity_add);
        humidity_add.setOnClickListener(this);
        humidity_sub = findViewById(R.id.humidity_sub);
        humidity_sub.setOnClickListener(this);
        soilHumidity_add = findViewById(R.id.soilHumidity_add);
        soilHumidity_add.setOnClickListener(this);
        soilHumidity_sub = findViewById(R.id.soilHumidity_sub);
        soilHumidity_sub.setOnClickListener(this);
        soilTemp_add = findViewById(R.id.soilTemp_add);
        soilTemp_add.setOnClickListener(this);
        soilTemp_sub = findViewById(R.id.soilTemp_sub);
        soilTemp_sub.setOnClickListener(this);
        co2_add = findViewById(R.id.co2_add);
        co2_add.setOnClickListener(this);
        co2_sub = findViewById(R.id.co2_sub);
        co2_sub.setOnClickListener(this);
        light_add = findViewById(R.id.light_add);
        light_add.setOnClickListener(this);
        light_sub = findViewById(R.id.light_sub);
        light_sub.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.temp_add:
                WoringState.setTemp(WoringState.getTemp()+1);
                break;
            case R.id.temp_sub:
                WoringState.setTemp(WoringState.getTemp()-1);
                break;
            case R.id.soilTemp_add:
                WoringState.setSoiltemp(WoringState.getSoiltemp()+1);
                break;
            case R.id.soilTemp_sub:
                WoringState.setSoiltemp(WoringState.getSoiltemp()-1);
                break;
            case R.id.humidity_add:
                WoringState.setHuidity(WoringState.getHuidity()+1);
                break;
            case R.id.humidity_sub:
                WoringState.setHuidity(WoringState.getHuidity()-1);
                break;
            case R.id.soilHumidity_add:
                WoringState.setSoilhumidity(WoringState.getSoilhumidity()+1);
                break;
            case R.id.soilHumidity_sub:
                WoringState.setSoilhumidity(WoringState.getSoilhumidity()-1);
                break;
            case R.id.co2_add:
                WoringState.setCo2(WoringState.getCo2()+1);
                break;
            case R.id.co2_sub:
                WoringState.setCo2(WoringState.getCo2()-1);
                break;
            case R.id.light_add:
                WoringState.setLight(WoringState.getLight()+1);
                break;
            case R.id.light_sub:
                WoringState.setLight(WoringState.getLight()-1);
                break;

        }
        show();
    }
    private void show(){
        temp.setText("空气温度:  "+WoringState.getTemp()+"℃");
        soilTemp.setText("土壤温度:  "+WoringState.getSoiltemp()+"℃");
        humidity.setText("空气湿度:  "+WoringState.getHuidity()+"%");
        soilHumidity.setText("空气湿度:  "+WoringState.getSoilhumidity()+"%");
        light.setText("光照强度："+WoringState.getLight());
        co2.setText("co2浓度:  "+WoringState.getCo2()+"%");

    }
}
