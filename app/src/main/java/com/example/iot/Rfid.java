package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ch340Util.SerialPort;

public class Rfid extends AppCompatActivity {
    private DBManager dbManager;
    private int userID;
    private SenseData senseData = new SenseData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);
        SerialPort.initCH340(this.getApplicationContext());
        dbManager = new DBManager(this);
        dbManager.openDatabase();

        // 注册eventbus
        EventBus.getDefault().register(this);




        ImageButton imageButton = findViewById(R.id.test);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string){
        Log.d("Rfid","获取到了从传感器发送到Android主板的串口数据");
        //System.out.println(string);
        senseData.setDate(string);
        SQLiteDatabase db = dbManager.getDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM person WHERE cardID = ?",
                new String[]{senseData.getCardID()});
        if(cursor.moveToFirst())
        {
            userID = cursor.getInt(cursor.getColumnIndex("ID"));
            dbManager.closeDatabase();
            goHome();
        }
    }

    private void goHome() {
        SerialPort.Close();
        EventBus.getDefault().unregister(this);
        Intent intent =  new Intent(Rfid.this,MainActivity.class);
        intent.putExtra("key",userID);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
