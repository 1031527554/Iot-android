package com.example.iot;


public class SenseData {
    private static int sign;  //判断传感器类型
    private static String cardID;//RFID ID
    private static int hum,tep,bhum,bhumsign;//传感器数据


    public static void setDate(String date) {
        String[] strings = date.split("\\|"); //转义字符
        if (strings[0].equals("E"))
        {
            sign =  Integer.parseInt(strings[1]);
            switch (sign){
                case 1:
                    tep = Integer.parseInt(strings[2]);
                    hum = Integer.parseInt(strings[3]);
                case 2:
                    bhum = Integer.parseInt(strings[2]);
                    bhumsign = Integer.parseInt(strings[3]);
                case 3:
                    cardID = strings[2];
                    break;
            }
        }
    }

    public static int getHum(){return hum;}
    public static int getTep(){return tep;}
    public static int getBhum(){return bhum;}
    public static int getSign(){return sign;}
    public static int getBsign(){return bhumsign;}
    public static String getCardID(){return cardID;}



}
