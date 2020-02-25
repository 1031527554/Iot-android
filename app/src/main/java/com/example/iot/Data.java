package com.example.iot;


public class Data {
    private String id;
    private String cardID;
    private String message;
    private int hum,tep;
    private int bhum,sign;


    public void setDate(String date) {
        String[] strings = date.split("\\|");
        if (strings[0].equals("E"))
        {
            id =  strings[1];
            switch (id){
                case "1":
                    tep = Integer.parseInt(strings[2]);
                    hum = Integer.parseInt(strings[3]);
                case "2":
                    bhum = Integer.parseInt(strings[2]);
                    sign = Integer.parseInt(strings[3]);
                case "3":
                    cardID = strings[2];
                    break;
            }
        }
    }

    public int getHum(){return hum;}
    public int getTep(){return tep;}
    public int getBhum(){return bhum;}
    public int getSign(){return sign;}
    public String getCardID(){return cardID;}



}
