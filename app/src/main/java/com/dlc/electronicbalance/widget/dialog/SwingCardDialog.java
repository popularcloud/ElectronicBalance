package com.dlc.electronicbalance.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dlc.electronicbalance.R;

/**
 * 刷卡dialog
 * Created by YoungeTao on 2017/8/10
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class SwingCardDialog extends Dialog{

    private Context context;

    public SwingCardDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        //隐藏dialog默认title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_swing_card, null);
        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
       // Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = 830;
        params.height = 500;
        dialogWindow.setAttributes(params);
    }
}
