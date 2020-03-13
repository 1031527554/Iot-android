package com.example.iot;

public class DataState {
    private static int temp;
    private static int soiltemp;
    private static int huidity;
    private static int soilhumidity;
    private static int co2;
    private static int light;

    public static int getSelect() {
        return select;
    }

    public static void setSelect(int select) {
        DataState.select = select;
    }

    private static int select;

    public static int getLight() {
        return light;
    }

    public static void setLight(int light) {
        DataState.light = light;
    }

    public static int getCo2() {
        return co2;
    }

    public static void setCo2(int co2) {
        DataState.co2 = co2;
    }

    public static int getSoilhumidity() {
        return soilhumidity;
    }

    public static void setSoilhumidity(int soilhumidity) {
        DataState.soilhumidity = soilhumidity;
    }

    public static int getHuidity() {
        return huidity;
    }

    public static void setHuidity(int huidity) {
        DataState.huidity = huidity;
    }

    public static int getSoiltemp() {
        return soiltemp;
    }

    public static void setSoiltemp(int soiltemp) {
        DataState.soiltemp = soiltemp;
    }

    public static void setTemp(int temp) {
        DataState.temp = temp;
    }

    public static int getTemp() {
        return temp;
    }
}
