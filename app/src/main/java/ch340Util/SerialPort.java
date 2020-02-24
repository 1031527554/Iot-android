package ch340Util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

import static android.content.ContentValues.TAG;

public class SerialPort {
    private UsbManager mUsbManager;
    private UsbDevice mUsbDevice;
    private String ACTION_USB_PERMISSION = "com.example.iot.usb.permission";
    private CH34xUARTDriver driver;
    private Context context;
    private boolean isOpenDeviceCH340 = false,isStart = false;
    /**
     2     * initialize ch340 parameters.
     3     *
     4     * @param context Application context.
     5     */
    public SerialPort(Context context){
        this.context = context;
    }

    public void initCH340() {
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        mUsbManager = (UsbManager) appContext.getSystemService(Context.USB_SERVICE);
        if (mUsbManager != null) {
            HashMap<String, UsbDevice> deviceHashMap = mUsbManager.getDeviceList();
            for (UsbDevice device : deviceHashMap.values()) {
                if (device.getProductId() == 29987 && device.getVendorId() == 6790) {
                    mUsbDevice = device;
                    if (mUsbManager.hasPermission(device)) {
                        loadDriver(appContext, mUsbManager);
                    } else {
                        ToastUtil.showMessage(context,"未授权");
                        mUsbManager.requestPermission(device, PendingIntent.getBroadcast(context,0,new Intent(ACTION_USB_PERMISSION),0));  //请求权限
                    }
                    break;
                }
            }
        }
    }
    /**
     * load ch340 driver.
     *
     * @param appContext
     * @param usbManager
     */
    private   void loadDriver(Context appContext, UsbManager usbManager) {
        driver = new CH34xUARTDriver(usbManager, appContext, ACTION_USB_PERMISSION);
        // 判断系统是否支持USB HOST
        if (!driver.UsbFeatureSupported()) {
            Log.e(TAG, "Your mobile phone does not support USB HOST, please change other phones to try again!");
        } else {
            openCH340();
        }
    }
    /**
      * config and open ch340.
      */
    private  void openCH340() {
        int ret_val = driver.ResumeUsbList();
        Log.d(TAG, ret_val + "");
        // ResumeUsbList方法用于枚举CH34X设备以及打开相关设备
        if (ret_val == -1) {
            Log.d(TAG, ret_val + "Failed to open device!");
            driver.CloseDevice();
        } else if (ret_val == 0) {
            if (!driver.UartInit()) {  //对串口设备进行初始化操作
                Log.d(TAG, ret_val + "Failed device initialization!");
                Log.d(TAG, ret_val + "Failed to open device!");
                return;
            }
            Log.d(TAG, ret_val + "Open device successfully!");
            if (!isOpenDeviceCH340) {
                isOpenDeviceCH340 = true;
                configParameters();//配置ch340的参数、需要先配置参数
            }
        } else {
            Log.d(TAG, "The phone couldn't find the device！");
        }
    }
    /**
     * config ch340 parameters.
     * 配置串口波特率，函数说明可参照编程手册
     */
    private void configParameters() {
        if (driver.SetConfig(115200, (byte) 8, (byte)0,(byte)0, (byte)0)) {
            Log.d(TAG, "Successful serial port Settings！");
            isStart = true;
            ReceiveThread receiveThread = new ReceiveThread();
            receiveThread.start();
        } else {
            Log.d(TAG, "Serial port Settings failed！");
        }
    }

    public void WriteData(String string){
        byte [] data =string.getBytes();
        driver.WriteData(data,data.length);

    }
    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            super.run();
            //条件判断，只要条件为true，则一直执行这个线程
            while (isStart) {

                byte[] Data = new byte[13];
                int len = driver.ReadData(Data,Data.length);
                String readData = new String(Data);
                if (len>0){
                    System.out.println(readData);
                }


          //      EventBus.getDefault().post(readData);   线程通讯
            }
        }
    }



}





