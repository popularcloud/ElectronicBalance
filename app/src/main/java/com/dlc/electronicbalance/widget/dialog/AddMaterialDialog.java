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
 * 添加料品dialog
 * Created by YoungeTao on 2017/8/10
 * QQ 2276559259.
 * gmail youngetao@gmail.com
 */

public class AddMaterialDialog extends Dialog{

    private Context context;
    private Spinner spinner02;
    private Spinner spinner01;
    private ArrayAdapter<String> arr_adapter01;
    private ArrayAdapter<String> arr_adapter02;
    private DialogBtnCallBack dialogbtnCallBack;

    public AddMaterialDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init();
    }

    public AddMaterialDialog init(String title,String btn1,String btn2) {
        //隐藏dialog默认title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_material, null);
        setContentView(view);

        spinner01 = (Spinner) view.findViewById(R.id.spinner01);
        spinner02 = (Spinner) view.findViewById(R.id.spinner02);
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
                    dialogbtnCallBack.onFirstBtnClick(AddMaterialDialog.this,"");
                }
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogbtnCallBack != null){
                    dialogbtnCallBack.onSecondBtnClick(AddMaterialDialog.this,"");
                }
            }
        });

        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
       // Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = 1274;
        params.height = 670;
        dialogWindow.setAttributes(params);
        return this;
    }

    public void setDialogBtnClick(DialogBtnCallBack dialogbtnCallBack){
        this.dialogbtnCallBack = dialogbtnCallBack;
    }

    /**
     * 初始化spinner数据
     */
    public AddMaterialDialog initSpinnerData(List<String> data1,List<String> data2){
        //适配器
        arr_adapter01= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data1);
        //设置样式
        arr_adapter01.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner01.setAdapter(arr_adapter01);

        //适配器
        arr_adapter02= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data2);
        //设置样式
        arr_adapter02.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner02.setAdapter(arr_adapter02);

        return this;
    }

    public AddMaterialDialog dismissInTime(final Dialog dialog, Long dismissTime){
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
