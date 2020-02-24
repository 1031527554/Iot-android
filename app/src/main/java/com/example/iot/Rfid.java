package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import ch340Util.SerialPort;

public class Rfid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);
        final SerialPort serialPort = new SerialPort(getApplicationContext());
        serialPort.initCH340();
        ImageButton imageButton = findViewById(R.id.test);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serialPort.WriteData("1234567891234F");
            }
        });



    }
}
