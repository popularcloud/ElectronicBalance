package com.dlc.electronicbalance.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.interfaces.DialogBtnCallBack;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 删除料品dialog
 * Created by YoungeTao on 2017/8/10
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class DeleteDialog extends Dialog{

    private Context context;
    private DialogBtnCallBack dialogbtnCallBack;

    public DeleteDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init();
    }

    public DeleteDialog init(String title,String btn1,String btn2) {
        //隐藏dialog默认title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete_material, null);
        setContentView(view);

        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_add = (TextView) view.findViewById(R.id.tv_add);


        if(!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
        if(!TextUtils.isEmpty(btn1)){
            tv_cancel.setText(btn1);
        }
        if(!TextUtils.isEmpty(btn2)){
            tv_add.setText(btn2);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogbtnCallBack != null){
                    dialogbtnCallBack.onFirstBtnClick(DeleteDialog.this,"");
                }
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogbtnCallBack != null){
                    dialogbtnCallBack.onSecondBtnClick(DeleteDialog.this,"");
                }
            }
        });

        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
       // Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = 902;
        params.height = 544;
        dialogWindow.setAttributes(params);
        return this;
    }

    public void setDialogBtnClick(DialogBtnCallBack dialogbtnCallBack){
        this.dialogbtnCallBack = dialogbtnCallBack;
    }
}
