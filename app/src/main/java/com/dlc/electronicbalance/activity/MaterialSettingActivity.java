package com.dlc.electronicbalance.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.adapter.MaterialOprateAdapter;
import com.dlc.electronicbalance.adapter.MaterialTypeAdapter;
import com.dlc.electronicbalance.base.BaseActivity;
import com.dlc.electronicbalance.bean.GetGoodsBean;
import com.dlc.electronicbalance.bean.GetGroupBean;
import com.dlc.electronicbalance.interfaces.DialogBtnCallBack;
import com.dlc.electronicbalance.interfaces.ItemBtnCallBack;
import com.dlc.electronicbalance.interfaces.ItemOnclick;
import com.dlc.electronicbalance.net.MyRequestUtils;
import com.dlc.electronicbalance.widget.dialog.AddMaterialDialog;
import com.dlc.electronicbalance.widget.dialog.DeleteDialog;
import com.dlc.electronicbalance.widget.dialog.StatusDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MaterialSettingActivity extends BaseActivity {

    @BindView(R.id.rv_type)
    RecyclerView rv_type;
    @BindView(R.id.rv_material)
    RecyclerView rv_material;
    private MaterialOprateAdapter adapter;
    private List<GetGoodsBean.DataBean> mGoodsList = new ArrayList<>();
    private StatusDialog statusDialog;

    private List<GetGroupBean.DataBean> mGroupList = new ArrayList<>();
    private MaterialTypeAdapter materialTypeAdapter;



    @Override
    protected int getLayoutID() {
        return R.layout.activity_material_setting;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);

        initRecycleView();
    }

    private void initRecycleView() {
        materialTypeAdapter =new MaterialTypeAdapter(this,mGroupList);
        rv_type.setLayoutManager(new GridLayoutManager(this,4));
        rv_type.setAdapter(materialTypeAdapter);
        materialTypeAdapter.setOnItemOnClick(new ItemOnclick() {
            @Override
            public void onClick(View view, Object obj) {
                getGoods(String.valueOf(obj));
            }
        });
        loadData();

        rv_material.setLayoutManager(new LinearLayoutManager(this));
        rv_material.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new MaterialOprateAdapter(this,mGoodsList);
        rv_material.setAdapter(adapter);

        adapter.setOnItemOnClick(new ItemOnclick() {
            @Override
            public void onClick(View view, Object obj) {

            }
        });

        adapter.setItemBtnClick(new ItemBtnCallBack() {
            @Override
            public void onDelete(View view, String id) {
                DeleteDialog deleteDialog = new DeleteDialog(MaterialSettingActivity.this);
                deleteDialog.init("是否删除该料品","取消","删除").show();
                deleteDialog.setDialogBtnClick(new DialogBtnCallBack() {
                    @Override
                    public void onFirstBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSecondBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                        showStatusDialog("删除成功!");
                    }
                });
            }

            @Override
            public void onEdit(View view, String id) {
                ArrayList<String> data_list01 = new ArrayList<String>();
                data_list01.add("请选择料品");

                data_list01.add("料品1");
                data_list01.add("料品2");
                data_list01.add("料品3");

                ArrayList<String> data_list02 = new ArrayList<String>();
                data_list02.add("请选择喷码信息");
                data_list02.add("5656232");
                data_list02.add("5656123");
                data_list02.add("5123123");
                AddMaterialDialog addDialog = new AddMaterialDialog(MaterialSettingActivity.this);
                addDialog.init("编辑料品","取消","保存").initSpinnerData(data_list01,data_list02).show();
                addDialog.setDialogBtnClick(new DialogBtnCallBack() {
                    @Override
                    public void onFirstBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSecondBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                        showStatusDialog("编辑成功!");
                    }
                });
            }
        });
    }

    private void loadData(){
        MyRequestUtils.get()
                .getGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetGroupBean>() {
                    @Override
                    public void accept(GetGroupBean getGroupBean) throws Exception {
                        if (getGroupBean.code == 1){
                            mGroupList.clear();
                            mGroupList.addAll(getGroupBean.data);
                            materialTypeAdapter.notifyDataSetChanged();
                        }else {
                            showToast(getGroupBean.msg);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void getGoods(String cate_id){
        MyRequestUtils.get()
                .get_goods(cate_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetGoodsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GetGoodsBean getGoodsBean) {
                        if (getGoodsBean.code == 1){
                            mGoodsList.clear();
                            mGoodsList.addAll(getGoodsBean.data);
                            adapter.notifyDataSetChanged();
                        }else {
                            showToast(getGoodsBean.msg);
                            mGoodsList.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mGoodsList.clear();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.tv_add_new)
    public void onBtnClick(View v){
        switch (v.getId()){
            case R.id.tv_add_new:
                ArrayList<String> data_list01 = new ArrayList<String>();
                data_list01.add("请选择料品");

                data_list01.add("料品1");
                data_list01.add("料品2");
                data_list01.add("料品3");

                ArrayList<String> data_list02 = new ArrayList<String>();
                data_list02.add("请选择喷码信息");
                data_list02.add("5656232");
                data_list02.add("5656123");
                data_list02.add("5123123");
                AddMaterialDialog addDialog = new AddMaterialDialog(this);
                addDialog.init(null,null,null).initSpinnerData(data_list01,data_list02).show();
                addDialog.setDialogBtnClick(new DialogBtnCallBack() {
                    @Override
                    public void onFirstBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSecondBtnClick(Dialog dialog, Object obj) {
                        dialog.dismiss();
                        showStatusDialog("添加成功!");
                    }
                });
                break;
        }
    }

    private void showStatusDialog(String message) {
        if(statusDialog == null){
            statusDialog = new StatusDialog(this);
        }
        statusDialog.init(message,R.mipmap.ic_success).dismissInTime(statusDialog,3000L).show();
    }
}
