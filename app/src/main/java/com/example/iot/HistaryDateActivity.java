package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class HistaryDateActivity extends AppCompatActivity implements View.OnClickListener {
    private LineChart mlineChart;
    private LineDataSet set1,set2;
    private DBManager dbManager;
    private Button temp,light,co2,humidity;
    private ArrayList<Entry> d_temp,d_soiltemp,d_humidity,d_soilhumidity,d_co2,d_light;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histary_date);
        temp = findViewById(R.id.temp);
        light = findViewById(R.id.light);
        co2 = findViewById(R.id.co2);
        humidity = findViewById(R.id.humidity);

        temp.setOnClickListener(this);
        light.setOnClickListener(this);
        co2.setOnClickListener(this);
        humidity.setOnClickListener(this);

        mlineChart = findViewById(R.id.line1);
        //后台绘制
        mlineChart.setDrawGridBackground(false);
        //设置描述文本
        mlineChart.getDescription().setEnabled(false);
        //设置支持触控手势
        mlineChart.setTouchEnabled(true);
        //设置缩放
        mlineChart.setDragEnabled(true);
        //设置推动
        mlineChart.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        mlineChart.setPinchZoom(true);

        d_temp = new ArrayList<Entry>();
        d_soiltemp = new ArrayList<Entry>();
        d_humidity = new ArrayList<Entry>();
        d_soilhumidity = new ArrayList<Entry>();
        d_co2 = new ArrayList<Entry>();
        d_light = new ArrayList<Entry>();

        dbManager = new DBManager(this);
        dbManager.openDatabase();
        SQLiteDatabase db = dbManager.getDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM sense ",
                new String[]{});
        if(cursor.moveToFirst()){
            cursor.moveToPosition(cursor.getCount()-40);
            do {
                int x = cursor.getInt(cursor.getColumnIndex("ID"));
                d_temp.add(new Entry(x,cursor.getFloat(cursor.getColumnIndex("temps"))));
                d_soiltemp.add(new Entry(x,cursor.getFloat(cursor.getColumnIndex("soilTemp"))));
                d_humidity.add(new Entry(x,cursor.getFloat(cursor.getColumnIndex("humidity"))));
                d_soilhumidity.add(new Entry(x,cursor.getFloat(cursor.getColumnIndex("soilHumidity"))));
                d_co2.add(new Entry(x,cursor.getFloat(cursor.getColumnIndex("co2"))));
                d_light.add(new Entry(x,cursor.getFloat(cursor.getColumnIndex("light"))));
            }while(cursor.moveToNext());
        }
        dbManager.closeDatabase();
        cursor.close();

    }




    @Override
    public void onClick(View view) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        switch (view.getId()) {
            case R.id.temp:
                set1 = setData(d_temp,"温度",-65536);
                set2 = setData(d_soiltemp,"土壤温度",-16776961);
                dataSets.clear();
                dataSets.add(set1);
                dataSets.add(set2);


                break;
            case R.id.humidity:
                set1 = setData(d_humidity,"湿度",-65536);
                set2 = setData(d_soilhumidity,"土壤湿度",-16776961);
                dataSets.clear();
                dataSets.add(set1);
                dataSets.add(set2);

                break;
            case R.id.co2:
                set1 = setData(d_co2,"co2",-65536);
                dataSets.clear();
                dataSets.add(set1);


                break;
            case R.id.light:
                set1 = setData(d_light,"光照",-65536);
                dataSets.clear();
                dataSets.add(set1);
                break;
        }
        LineData data = new LineData(dataSets);
        mlineChart.setData(data);

        //默认动画
        mlineChart.animateX(2500);
        //刷新
        mlineChart.invalidate();
        // 得到这个文字
        Legend l = mlineChart.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);

    }


    private LineDataSet setData(ArrayList<Entry> values, String name , int color){
        LineDataSet set1;
        set1 = new LineDataSet(values, name);
        // 在这里设置线
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(color);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            // 填充背景只支持18以上
            //Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
            //set1.setFillDrawable(drawable);
            set1.setFillColor(Color.YELLOW);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        //添加数据集
        set1.setDrawFilled(false);
        return set1;
    }
}
