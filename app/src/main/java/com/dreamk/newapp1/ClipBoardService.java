package com.dreamk.newapp1;

import android.app.Activity;
import android.content.Context;


public class ClipBoardService {

    private Context contxt;
    private AdminActivity activity;

    public Context getContxt() {
        return contxt;
    }

    public void setContxt(Context contxt) {
        this.contxt = (AdminActivity) contxt;
    }

    public AdminActivity getActivity() {
        return (AdminActivity) activity;
    }

    public void setActivity(Activity activity) {
        this.activity = (AdminActivity) activity;
    }

    public ClipBoardService(Context context, AdminActivity activity) {
        this.setContxt(context);
        this.setActivity(activity);
    }
}
