package com.dlc.electronicbalance.interfaces;

import android.view.View;

/**
 * recycleview item点击
 */
public interface ItemBtnCallBack {
        void onDelete(View view, String id);
        void onEdit(View view, String id);
    }