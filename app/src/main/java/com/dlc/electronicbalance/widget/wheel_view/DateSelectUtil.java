package com.dlc.electronicbalance.widget.wheel_view;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.base.BaseActivity;
import com.dlc.electronicbalance.interfaces.OnDateSelectedCallBack;
import com.dlc.electronicbalance.widget.DatePickerPopWindow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/7/10.
 */
public class DateSelectUtil {

    private BaseActivity activity;
    private DatePickerPopWindow popWindow;
    private static DateSelectUtil instanc;
    public static DateSelectUtil  getInstance(){
        if(instanc == null){
            instanc = new DateSelectUtil();
        }
        return instanc;
    }

    public void getOneDateTime(final TextView stv,Context context){
        activity = (BaseActivity) context;
        Date date=new Date();
        DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
        popWindow=new DatePickerPopWindow(context, df.format(date), new OnDateSelectedCallBack() {
            @Override
            public void ondestory() {
                popWindow.dismiss();
            }

            @Override
            public void onSelectedOk(String datetime, String obj) {
                        stv.setText(datetime);
                        stv.setTag(datetime);
                        popWindow.dismiss();
            }

            @Override
            public void onError(String msg) {
                activity.showToast(msg);
                return;
            }
        });
        WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
        lp.alpha=0.5f;
        activity.getWindow().setAttributes(lp);
        //防止虚拟软键盘被弹出菜单遮住
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        popWindow.showAtLocation(activity.findViewById(R.id.root), Gravity.BOTTOM, 0, 0);
        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
                lp.alpha=1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }
}
