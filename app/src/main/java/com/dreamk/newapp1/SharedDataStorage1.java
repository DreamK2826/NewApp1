package com.dreamk.newapp1;

public class SharedDataStorage1 {
    public static String userName;
    public static boolean isAdminUser = false;
    public static String userMessage;

    public static int uColor;

    void setuColor(String colorStr){
        int res = 0;


        if(colorStr.equals("红色")){
            res = 1;
        } else if (colorStr.equals("绿色")) {
            res = 2;
        }else if (colorStr.equals("蓝色")) {
            res = 3;
        }else if (colorStr.equals("白色")) {
            res = 4;
        }else if (colorStr.equals("灰色")) {
            res = 5;
        }else if (colorStr.equals("粉色")) {
            res = 6;
        }else if (colorStr.equals("黄色")) {
            res = 7;
        }


        uColor = res;
    }

}
