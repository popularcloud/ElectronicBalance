package com.dlc.electronicbalance.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.adapter.PackageDataAdapter;
import com.dlc.electronicbalance.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PackageActivity extends BaseActivity {

    @BindView(R.id.rv_material)
    RecyclerView rv_material;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_package;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);

        initRecycleView();

    }


    private void initRecycleView() {
        rv_material.setLayoutManager(new LinearLayoutManager(this));
        rv_material.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rv_material.setAdapter(new PackageDataAdapter(this));
    }

    @OnClick({R.id.tv_setting})
    public void onBtnClick(View v){
        switch (v.getId()){
            case R.id.tv_setting:
                startActivity(PackageSettingActivity.class);
                break;
        }
    }
}
