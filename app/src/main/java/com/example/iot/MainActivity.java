package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch340Util.SerialPort;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int userID;
    private Button button,openButton,closeButton;
    private ImageButton imageBt1,imageBt2,imageBt3,imageBt4,imageBt5,imageBt6;
    private TextView tvDH,tv2,tvname,tv4,tvT,tvH,time3,time2,time1,tvL;
    private DBManager dbManager;
    private Handler handler;
    private SenseData senseData;
    private ChineseToSpeech chineseToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userID = intent.getIntExtra("key",0);  //得到用户ID
        //  打开数据库获取用户名称
        dbManager = new DBManager(this);
        dbManager.openDatabase();
        SQLiteDatabase db = dbManager.getDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM person WHERE ID = ?",
                new String[]{String.valueOf(userID)});
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        dbManager.closeDatabase();



        imageBt1 = findViewById(R.id.imageBt1);
        imageBt1.setOnClickListener(this);
        imageBt2 = findViewById(R.id.imageBt2);
        imageBt2.setOnClickListener(this);
        imageBt3 = findViewById(R.id.imageBt3);
        imageBt3.setOnClickListener(this);
        imageBt4 = findViewById(R.id.imageBt4);
        imageBt4.setOnClickListener(this);
        imageBt5 = findViewById(R.id.imageBt5);
        imageBt5.setOnClickListener(this);
        imageBt6 = findViewById(R.id.imageBt6);
        imageBt6.setOnClickListener(this);

        openButton = findViewById(R.id.bt2);
        openButton.setOnClickListener(this);
        closeButton = findViewById(R.id.bt3);
        closeButton.setOnClickListener(this);



        tvT = findViewById(R.id.tvT);    //温度
        tvH = findViewById(R.id.tvH);    //湿度
        tvDH = findViewById(R.id.tvDH);  //地湿
        tvL =findViewById(R.id.tvL);

        tvname = findViewById(R.id.name);
        tvname.setText(name);
        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if (msg.what ==1){
                    SimpleDateFormat simpleDateFormat1 =new SimpleDateFormat("HH:mm");
                    SimpleDateFormat simpleDateFormat2 =new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(System.currentTimeMillis());
                    time1.setText(simpleDateFormat1.format(date));
                    time2.setText(simpleDateFormat2.format(date));
                }
            }
        };


        time();
        initViews();
        //注册EventBus
        EventBus.getDefault().register(this);
        //数据库存储
       // saveDate();
        //语音播报初始化
        chineseToSpeech = new ChineseToSpeech(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt2 :
                SerialPort.Open();
                break;
            case R.id.bt3:
                SerialPort.Close();
                break;
            case R.id.imageBt1:
                startActivity(new Intent(MainActivity.this,ModeActivity.class));
                break;
            case R.id.imageBt2:
                startActivity(new Intent(MainActivity.this, WoringActivity.class));
                break;
            case R.id.imageBt3:
                Intent intent =  new Intent(MainActivity.this, StateActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBt4:
                startActivity(new Intent(MainActivity.this, HistaryDateActivity.class));
                break;
            case R.id.imageBt5:
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.imageBt6:
                startActivity(new Intent(MainActivity.this, SolveActivity.class));
                break;
            default:
                break;
        }
    }



    // 初始化界面
    private void initViews() {
        ImageView image1 = findViewById(R.id.img1);
        ImageView imageT = findViewById(R.id.imgT);
        ImageView imageH = findViewById(R.id.imgH);
        ImageView imageC = findViewById(R.id.imgL);
        ImageView imageL = findViewById(R.id.imgC);
        ImageView imageDT = findViewById(R.id.imgDT);
        ImageView imageDH = findViewById(R.id.imgDH);



        Glide.with(this)
                .load(R.mipmap.picture)  //将来使用网络图片
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(image1);

        Glide.with(this)
                .load(R.mipmap.wendu)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//圆角半径
                .into(imageT);

        Glide.with(this)
                .load(R.mipmap.shidu)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//圆角半径
                .into(imageH);

        Glide.with(this)
                .load(R.mipmap.guangzhao)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//圆角半径
                .into(imageL);
        Glide.with(this)
                .load(R.mipmap.eryanghuatan)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//圆角半径
                .into(imageC);
        Glide.with(this)
                .load(R.mipmap.dishi)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//圆角半径
                .into(imageDH);
        Glide.with(this)
                .load(R.mipmap.diwen)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//圆角半径
                .into(imageDT);
    }

    //时间刷新并保存登录时间
    private void time(){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm/MM-dd");
        Date date = new Date(System.currentTimeMillis());
        time3.setText(simpleDateFormat.format(date));
        dbManager.openDatabase();
        SQLiteDatabase db = dbManager.getDatabase();
        db.execSQL("UPDATE person SET enterTime = ? WHERE ID = ?",
                new String[]{simpleDateFormat.format(date), String.valueOf(userID)});
        dbManager.closeDatabase();

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    handler.sendEmptyMessage(1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * 用EventBus进行线程间通信，也可以使用Handler
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string){
        Log.d("main","获取到了从传感器发送到Android主板的串口数据");
        senseData =new SenseData();
        senseData.setDate(string);
        int sign = senseData.getSign();
        switch (sign){
            case 1:
                tvT.setText(senseData.getTep()+"℃");
                tvH.setText(senseData.getHum()+"%");


                break;
            case 2:
                tvDH.setText(senseData.getBhum()+"%");
                if (senseData.getBsign()==1) {
                    chineseToSpeech.speech("湿度异常");
                }
                break;
        }

    }



    private void saveDate(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm/MM-dd");
                    Date date = new Date(System.currentTimeMillis());
                    dbManager.openDatabase();
                    SQLiteDatabase db = dbManager.getDatabase();
                    db.execSQL("INSERT INTO sense(temps,humidity,soilHumidity,time) values(?,?,?,?,?)",
                            new String[]{String.valueOf(senseData.getTep()), String.valueOf(senseData.getHum()), String.valueOf(senseData.getBhum()),simpleDateFormat.format(date)});
                    dbManager.closeDatabase();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        chineseToSpeech.destroy();
    }
}
