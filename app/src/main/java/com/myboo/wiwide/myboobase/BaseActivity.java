package com.myboo.wiwide.myboobase;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by Li DaChang on 16/3/12.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置状态栏透明
     *
     * @param isOnlyKitKat   是否仅在4.4上实现
     * @param isFitStatusBar 是否填充状态栏
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setTransWindow(boolean isOnlyKitKat, boolean isFitStatusBar) {
        boolean isSetTrans = isOnlyKitKat ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT : Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isSetTrans) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (isFitStatusBar) {
            fitStatusBar(ContextCompat.getColor(BaseActivity.this, R.color.back));
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setTransWindow(boolean isOnlyKitKat, boolean isFitStatusBar,int color) {
        boolean isSetTrans = isOnlyKitKat ? Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT : Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isSetTrans) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (isFitStatusBar) {
            fitStatusBar(color);
        }
    }



    /**
     * 用view填充状态栏
     */
    public void fitStatusBar(int color) {
        // 创建TextView
        View textView = new View(this);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        //getResources().getColor(R.color.colorPrimary)已被弃用,现在改为下面的方法
        textView.setBackgroundColor(color);
        textView.setLayoutParams(lParams);
//        // 获得根视图并把TextView加进去。
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
//        FrameLayout view = (FrameLayout) findViewById(R.id.ccc);
        view.addView(textView);
    }

    /**
     * 为了兼容4.4的手机,实现透明状态栏+navigationDrawer而写的调整状态栏填充物的方法.
     * 此方法是针对于拥有navigationView的界面使用,其中,在AppBarLayout中要添加一个View用于填充状态栏,同时要去除所有父控件的fitsSystemWindows属性
     * 而且必须在设置透明状态栏以后使用
     * 如果是普通页面,直接调用setTransWindow()即可
     * @param color 状态栏颜色
     * @param view 状态栏填充物
     */
    public void setStatusBarFitter(int color, View view) {
        AppBarLayout.LayoutParams lpStatus = new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        view.setBackgroundColor(color);
        view.setLayoutParams(lpStatus);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 吐司
     *
     * @param msg 信息
     */
    public void toastGo(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT);
    }
}
