package com.dlc.electronicbalance.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.base.BaseActivity;
import com.dlc.electronicbalance.bean.UserInfoBean;
import com.dlc.electronicbalance.net.MyRequestUtils;
import com.dlc.electronicbalance.utils.SPUtils;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.ib_main)
    ImageButton ibMain;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_notice)
    TextView etNotice;
    private boolean isLogin = false;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);

        initTitleBar();
        initView();
    }

    private void initView() {
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && s.length() >= 3){
              /*      startActivity(MainActivity.class);
                    s.clear();*/
                    if(!isLogin){
                        login("pwd",s.toString());
                    }

                }
            }
        });
    }

    private void initTitleBar() {
        ibBack.setVisibility(View.INVISIBLE);
        ibMain.setVisibility(View.INVISIBLE);
        etSearch.setVisibility(View.INVISIBLE);
    }

    private void login(String type, String psd){
        isLogin = true;
        MyRequestUtils.get().login(type,psd)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<UserInfoBean, UserInfoBean>() {
                    @Override
                    public UserInfoBean apply(@NonNull UserInfoBean infoBean) throws Exception {
                        SPUtils.saveUserInfo(LoginActivity.this,infoBean);
                        return infoBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoBean>(){
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(UserInfoBean data) {
                        showToast("获取成功!");
                        startActivity(MainActivity.class);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        isLogin = false;
                    }

                    @Override
                    public void onComplete() {
                        isLogin = false;
                    }
         });
    }
}
