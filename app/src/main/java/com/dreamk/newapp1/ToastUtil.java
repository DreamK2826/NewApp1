package com.dreamk.newapp1;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by ABC on 2019/11/1.
 */
public abstract class ToastUtil {
    public static Toast mToast;

    /**
     * 传入文字
     */
    public static void show(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
//            mToast.setText(text);
            View view = mToast.getView();
            mToast.cancel();
            mToast = new Toast(context);
            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 传入资源文件
     */
    public static void show(Context context, int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
//            mToast.setText(resId);
            View view = mToast.getView();
            mToast.cancel();
            mToast = new Toast(context);
            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(resId);
        }
        mToast.show();
    }

    /**
     * 传入文字,在中间显示
     */
    public static void showTop(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
//            mToast.setText(text);
            View view = mToast.getView();
            mToast.cancel();
            mToast = new Toast(context);
            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
        }

        mToast.setGravity(Gravity.TOP, 0, 0);//Gravity.Center
        mToast.show();
    }

    /**
     * 传入文字，带图片
     */
    public static void showImg(Context context, String text, int resImg) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
//            mToast.setText(text);
            View view = mToast.getView();
            mToast.cancel();
            mToast = new Toast(context);
            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
        }

        //添加图片的操作,这里没有设置图片和文字显示在一行的操作呢...
        LinearLayout view = (LinearLayout) mToast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resImg);
        assert view != null;
        view.addView(imageView);

        mToast.show();
    }
}