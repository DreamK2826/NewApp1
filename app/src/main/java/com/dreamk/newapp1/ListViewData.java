package com.dreamk.newapp1;

public class ListViewData {

    private String lName;
    private String lNumber;
    private String lMsg;

    public ListViewData() {
    }

    public ListViewData(String lName, String lNumber, String lMsg) {
        this.lName = lName;
        this.lNumber = lNumber;
        this.lMsg = lMsg;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getlNumber() {
        return lNumber;
    }

    public void setlNumber(String lNumber) {
        this.lNumber = lNumber;
    }

    public String getlMsg() {
        return lMsg;
    }

    public void setlMsg(String lMsg) {
        this.lMsg = lMsg;
    }
}

