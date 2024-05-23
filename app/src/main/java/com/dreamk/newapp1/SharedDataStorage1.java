package com.dreamk.newapp1;

public class SharedDataStorage1 {
    public static String userName;
    public static boolean isAdminUser = false;
    public static String uMessage;

    public static int uColor;

    public static String uNumber;

    public static void setuColor(String colorStr){
        int res = 0;
        switch (colorStr) {
            case "红色":
                res = 1;
                break;
            case "绿色":
                res = 2;
                break;
            case "蓝色":
                res = 3;
                break;
            case "白色":
                res = 4;
                break;
            case "黑色":
                res = 5;
                break;
            case "灰色":
                res = 6;
                break;
            case "粉色":
                res = 7;
                break;
            case "黄色":
                res = 8;
                break;
        }
        uColor = res;
    }


}
