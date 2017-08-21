package com.dlc.electronicbalance.interfaces;

public interface OnDateSelectedCallBack {
    void ondestory();
    void onSelectedOk(String datatime, String obj);
    void onError(String msg);
}