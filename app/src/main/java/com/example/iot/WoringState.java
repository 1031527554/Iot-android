package com.example.iot;

public class WoringState {
    private static int temp,soiltemp,huidity,soilhumidity,co2,light;

    public static int getLight() {
        return light;
    }

    public static void setLight(int light) {
        WoringState.light = light;
    }

    public static int getCo2() {
        return co2;
    }

    public static void setCo2(int co2) {
        WoringState.co2 = co2;
    }

    public static int getSoilhumidity() {
        return soilhumidity;
    }

    public static void setSoilhumidity(int soilhumidity) {
        WoringState.soilhumidity = soilhumidity;
    }

    public static int getHuidity() {
        return huidity;
    }

    public static void setHuidity(int huidity) {
        WoringState.huidity = huidity;
    }

    public static int getSoiltemp() {
        return soiltemp;
    }

    public static void setSoiltemp(int soiltemp) {
        WoringState.soiltemp = soiltemp;
    }

    public static void setTemp(int temp) {
        WoringState.temp = temp;
    }

    public static int getTemp() {
        return temp;
    }
}
