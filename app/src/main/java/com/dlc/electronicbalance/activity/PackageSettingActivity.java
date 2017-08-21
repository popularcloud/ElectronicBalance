package com.dlc.electronicbalance.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PackageSettingActivity extends BaseActivity {

    @BindView(R.id.spinner01)
    Spinner spinner01;
    @BindView(R.id.spinner02)
    Spinner spinner02;

    private List data_list01;
    private List data_list02;
    private ArrayAdapter<String> arr_adapter01;
    private ArrayAdapter<String> arr_adapter02;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_package_setting;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);

        initSpinner();
    }

    private void initSpinner() {
        //数据
        data_list01 = new ArrayList<String>();
        data_list01.add("请选择料品");

        data_list01.add("料品1");
        data_list01.add("料品2");
        data_list01.add("料品3");

        data_list02 = new ArrayList<String>();
        data_list02.add("请选择喷码信息");
        data_list02.add("5656232");
        data_list02.add("5656123");
        data_list02.add("5123123");
        //适配器
        arr_adapter01= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list01);
        //设置样式
        arr_adapter01.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner01.setAdapter(arr_adapter01);

        //适配器
        arr_adapter02= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list02);
        //设置样式
        arr_adapter02.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner02.setAdapter(arr_adapter02);
    }
}
