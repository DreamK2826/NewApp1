package com.dreamk.newapp1;

public class DbObject {
    int _id, uColor, permitted, value1;
    String username, uNumber, uMessage;

    /**
     *
     * @param _id  数据库自增数值,主键,integer
     * @param username 用户名,text
     * @param uNumber 用户车牌号,text
     * @param uColor 颜色,integer
     * @param uMessage 用户申请信息,text
     * @param permitted 是否已经许可,integer
     * @param value1 预留数据,integer
     */
    public DbObject(int _id, String username, String uNumber, int uColor,
                    String uMessage, int permitted, int value1) {
        this._id = _id;
        this.uColor = uColor;
        this.permitted = permitted;
        this.value1 = value1;
        this.username = username;
        this.uNumber = uNumber;
        this.uMessage = uMessage;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getuColor() {
        String str = "未知";
        switch (uColor){
            case 1:
                str = "红色";
                break;
            case 2:
                str = "绿色";
                break;
            case 3:
                str = "蓝色";
                break;
            case 4:
                str = "紫色";
                break;
            case 5:
                str = "白色";
                break;
            case 6:
                str = "黑色";
                break;
            case 7:
                str = "灰色";
                break;
            case 8:
                str = "粉色";
                break;
            case 9:
                str = "黄色";
                break;
        }

        return str;
    }

    public void setuColor(int uColor) {
        this.uColor = uColor;
    }

    public int getPermitted() {
        return permitted;
    }

    public void setPermitted(int permitted) {
        this.permitted = permitted;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public String getuMessage() {
        return uMessage;
    }

    public void setuMessage(String uMessage) {
        this.uMessage = uMessage;
    }
}
