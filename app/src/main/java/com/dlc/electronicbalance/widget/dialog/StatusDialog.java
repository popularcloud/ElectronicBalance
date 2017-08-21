package com.dlc.electronicbalance.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlc.electronicbalance.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 成功dialog
 * Created by YoungeTao on 2017/8/10
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class StatusDialog extends AlertDialog{

    private Context context;

    public StatusDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init();
    }

    public StatusDialog init(String noticeText,Integer imageResouce) {
        //隐藏dialog默认title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_swing_card, null);
        setContentView(view);

        ImageView iv_notice = (ImageView) view.findViewById(R.id.iv_notice);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);

        if(!TextUtils.isEmpty(noticeText)){
            tv_message.setText(noticeText);
        }
        if(imageResouce != null){
            iv_notice.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),imageResouce));
        }

        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
       // Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = 830;
        params.height = 500;
        dialogWindow.setAttributes(params);

        return this;
    }

    public StatusDialog dismissInTime(final Dialog dialog, Long dismissTime){
        Timer timer = new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        };
        timer.schedule(task, dismissTime);
        return this;
    }
}
