package com.dlc.electronicbalance.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.adapter.SubmitDataAdapter;
import com.dlc.electronicbalance.base.BaseActivity;
import com.dlc.electronicbalance.database.DBManager;
import com.dlc.electronicbalance.database.WeighDatasBean;
import com.dlc.electronicbalance.interfaces.DialogBtnCallBack;
import com.dlc.electronicbalance.widget.dialog.DeleteDialog;
import com.dlc.electronicbalance.widget.dialog.StatusDialog;
import com.dlc.electronicbalance.widget.wheel_view.DateSelectUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SubmitHistoryDateActivity extends BaseActivity {

    @BindView(R.id.rv_material)
    RecyclerView rv_material;
    private StatusDialog statusDialog;
    private SubmitDataAdapter adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_submit_history_date;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);

        initRecycleView();
    }

    private void initRecycleView() {
        rv_material.setLayoutManager(new LinearLayoutManager(this));
        rv_material.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new SubmitDataAdapter(this);
        rv_material.setAdapter(adapter);

        getInitData();
    }

    /**
     * 获取数据
     */
    private void getInitData() {
      List<WeighDatasBean> dataList =  DBManager.getInstance(this).queryWeightList(null);
        adapter.setDataList(dataList);
    }

    @OnClick({R.id.tv_submit,R.id.tv_select_time})
    public void onBtnClick(View v){
        switch (v.getId()){
            case R.id.tv_submit:
                DeleteDialog deleteDialog = new DeleteDialog(SubmitHistoryDateActivity.this);
                deleteDialog.init("是否提交数据至后台","取消","提交").show();
                deleteDialog.setDialogBtnClick(new DialogBtnCallBack() {
                    @Override
                    public void onFirstBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSecondBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                        showStatusDialog();
                    }
                });
                break;
            case R.id.tv_select_time:
              //  DateSelectUtil.getInstance().getOneDateTime((TextView) v,SubmitHistoryDateActivity.this);
                break;
        }
    }

    private void showStatusDialog() {
        if(statusDialog == null){
            statusDialog = new StatusDialog(this);
            statusDialog.init("提交成功",R.mipmap.ic_success);
        }
        statusDialog.dismissInTime(statusDialog,3000L).show();
    }
}
