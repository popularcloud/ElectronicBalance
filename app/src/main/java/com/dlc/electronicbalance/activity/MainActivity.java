package com.dlc.electronicbalance.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dlc.electronicbalance.R;
import com.dlc.electronicbalance.adapter.MaterialAdapter;
import com.dlc.electronicbalance.adapter.MaterialTypeAdapter;
import com.dlc.electronicbalance.base.BaseActivity;
import com.dlc.electronicbalance.bean.GetGoodsBean;
import com.dlc.electronicbalance.bean.GetGroupBean;
import com.dlc.electronicbalance.database.DBManager;
import com.dlc.electronicbalance.database.WeighDatasBean;
import com.dlc.electronicbalance.interfaces.ItemOnclick;
import com.dlc.electronicbalance.net.MyRequestUtils;
import com.dlc.electronicbalance.widget.dialog.StatusDialog;
import com.dlc.electronicbalance.widget.dialog.SwingCardDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_type)
    RecyclerView rv_type;
    @BindView(R.id.rv_material)
    RecyclerView rv_material;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.tv_connect_device)
    TextView tv_connect_device;

    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();    //获取本地蓝牙适配器，即蓝牙设备
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private SwingCardDialog dialog;
    private StatusDialog statusDialog;
    BluetoothSocket _socket = null;      //蓝牙通信socket
    private InputStream is;    //输入流，用来接收蓝牙数据
    BluetoothDevice _device = null;     //蓝牙设备
    private String selectType = null;   //选中的材料类型
    private GetGoodsBean.DataBean selectMaterial = null;  // 选中的材料

    boolean _discoveryFinished = false;
    boolean bRun = true;
    boolean bThread = false;
    private String smsg="";
    private StringBuilder stringBuilder = new StringBuilder();

    private boolean isShow = false;
    private List<GetGroupBean.DataBean> mGroupList = new ArrayList<>();
    private MaterialTypeAdapter materialTypeAdapter;

    private List<GetGoodsBean.DataBean> mGoodsList = new ArrayList<>();
    private MaterialAdapter materialAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterSetContentView(Bundle savedInstanceState) {
        super.afterSetContentView(savedInstanceState);

        initRecycleView();

        tv_connect_device.setVisibility(View.VISIBLE);
        //检查蓝牙设备
        checkBT();
        //如果打开本地蓝牙设备不成功，提示信息，结束程序
  /*      if (_bluetooth == null){
            Log.e("mainActivity","无法打开手机蓝牙，请确认手机是否有蓝牙功能！");
            return;
        }
        // 设置设备可以被搜索
        new Thread(){
            public void run(){
                if(_bluetooth.isEnabled()==false){
                    _bluetooth.enable();
                }
            }
        }.start();*/
        // 注册接收查找到设备action接收器
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        // 注册查找结束action接收器
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
    }

    /**
     * 检查蓝牙
     */
    public void checkBT() {
        if (_bluetooth != null) {
            if (!_bluetooth.isEnabled()) {
                tv_connect_device.setText("蓝牙未打开");
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 设置蓝牙可见性，最多300秒
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(intent);
            }else{
                tv_connect_device.setText("开始连接");
            }
        } else {
            showToast("蓝牙设备驱动异常!");
            tv_connect_device.setText("蓝牙设备驱动异常");
        }
    }

    /**
     * 开始连接
     */
    private void connectBlueBooth() {
        if(_bluetooth.isEnabled()==false){  //如果蓝牙服务不可用则提示
            Log.e("mainActivity"," 蓝牙服务不可用");
            showToast("蓝牙服务不可用...");
            return;
        }

        if(_socket==null){
            //  开始搜索设备
            doDiscovery();
        } else{
            //关闭连接socket
     /*       try{
                is.close();
                _socket.close();
                _socket = null;
                bRun = false;
            }catch(IOException e){}*/
        }
    }

    private void initRecycleView() {
        materialTypeAdapter = new MaterialTypeAdapter(this,mGroupList);
        rv_type.setLayoutManager(new GridLayoutManager(this,5));
        rv_type.setAdapter(materialTypeAdapter);
        materialTypeAdapter.setOnItemOnClick(new ItemOnclick() {
            @Override
            public void onClick(View view, Object obj) {
                getGoods(String.valueOf(obj));
                selectType = String.valueOf(obj);
            }
        });
        loadData();

        materialAdapter = new MaterialAdapter(this,mGoodsList);
        rv_material.setLayoutManager(new LinearLayoutManager(this));
        rv_material.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        materialAdapter.setOnItemOnClick(new ItemOnclick() {
            @Override
            public void onClick(View view, Object obj) {
                selectMaterial = (GetGoodsBean.DataBean)obj;
            }
        });
        rv_material.setAdapter(materialAdapter);

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
                           materialAdapter.notifyDataSetChanged();
                       }else {
                           showToast(getGoodsBean.msg);
                           mGoodsList.clear();
                           materialAdapter.notifyDataSetChanged();
                       }
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {
                       mGoodsList.clear();
                       materialAdapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onComplete() {

                   }
               });
    }


    @OnClick({R.id.tv_setting_material,R.id.tv_weight_data,R.id.tv_package,R.id.tv_ok,R.id.tv_connect_device})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_setting_material:
                startActivity(MaterialSettingActivity.class);
                break;
            case R.id.tv_weight_data:
                startActivity(SubmitHistoryDateActivity.class);
                break;
            case R.id.tv_package:
                startActivity(PackageActivity.class);
                break;
            case R.id.tv_connect_device:
                String name = tv_connect_device.getText().toString().trim();
                if("开始连接".equals(name)){
                    //开始连接蓝牙
                    connectBlueBooth();
                }
                break;
            case R.id.tv_ok:

                //判断数据是否为空
                if(selectType == null){
                    showToast("请选择材料类型！");
                    return;
                }
                if(selectMaterial == null){
                    showToast("请选择材料");
                    return;
                }
                if(TextUtils.isEmpty(tv_weight.getText())){
                    showToast("请放置待称物品");
                    return;
                }

                if(dialog == null){
                    dialog = new SwingCardDialog(this);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                            //提交到本地数据库
                            Observable.just("0")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(Schedulers.io())
                                    .map(new Function<String,String>() {
                                        @Override
                                        public String apply(@NonNull String s) throws Exception {
                                            DBManager manager = new DBManager(MainActivity.this);
                                            WeighDatasBean weightDataBean = new WeighDatasBean();
                                            weightDataBean.setRq(String.valueOf(new Date().getTime()));
                                            weightDataBean.setGoods_id(selectMaterial.id);
                                            weightDataBean.setGoods_name(selectMaterial.name);
                                            //刷卡获取的数据
                                            weightDataBean.setU_account("56001");
                                            weightDataBean.setU_name("刘德法");

                                            weightDataBean.setNums(tv_weight.getText().toString());
                                            manager.insertWeightDatas(weightDataBean);
                                            return s;
                                        }
                                    })
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String s) throws Exception {
                                            showStatusDialog();
                                        }
                                    });
                        }
                    });
                }
                dialog.show();
                break;
        }
    }

    private void showStatusDialog() {
        if(statusDialog == null){
            statusDialog = new StatusDialog(this);
        }
        statusDialog.init("成功",R.mipmap.ic_success).dismissInTime(statusDialog,3000L).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭服务查找
        if (_bluetooth != null) {
            _bluetooth.cancelDiscovery();
        }
        // 注销action接收器
        this.unregisterReceiver(mReceiver);

        try {
            if(is != null){
                is.close();
            }
            if(_socket != null){
                _socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 开始服务和设备查找
     */
    private void doDiscovery() {
        Log.e("mainActivity", "开始搜索设备......");

        // 关闭再进行的服务查找
        if (_bluetooth.isDiscovering()) {
            _bluetooth.cancelDiscovery();
        }
        //并重新开始
        _bluetooth.startDiscovery();
        tv_connect_device.setText("正在搜索设备...");
    }

    // 查找到设备和搜索完成action监听器
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // 查找到设备action
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 得到蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                Log.e("mainActivity","device_name:  "+device.getName() + "device_address:   "+device.getAddress());

                // 获取到电子称蓝牙名称
                if("XK3190-A7".equals(device.getName())){
                    _bluetooth.cancelDiscovery();
                    tv_connect_device.setText("正在连接设备...");
                    connectDeviceAndGetData(device.getAddress());
                }

                // 如果是已配对的则略过，已得到显示，其余的在添加到列表中进行显示
         /*       if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                   // mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }else{  //添加到已配对设备列表
                   // mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }*/
                // 搜索完成action
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                _bluetooth.cancelDiscovery();
                Log.e("mainActivity","搜索完成");
            }
        }
    };

    /**
     *  接设备并获取数据
     */
    private void connectDeviceAndGetData(String address){
        // 得到蓝牙设备句柄
        _device = _bluetooth.getRemoteDevice(address);

        // 用服务号得到socket
        try{
            _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        }catch(IOException e){
            //Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
            Log.e("mainActivity","连接失败1"+e.getMessage());
            tv_connect_device.setText("连接失败");
        }
        try{
            _socket.connect();
            Log.e("mainActivity","连接"+_device.getName()+"成功！");
            tv_connect_device.setText("连接成功");
        }catch(IOException e){
            try{
                Log.e("mainActivity","连接失败2"+e.getMessage());
                tv_connect_device.setText("连接失败");
                _socket.close();
                _socket = null;
            }catch(IOException ee){
                Log.e("mainActivity","连接失败3"+e.getMessage());
                tv_connect_device.setText("连接失败");
            }
            return;
        }

        //打开接收线程
        try{
            is = _socket.getInputStream();   //得到蓝牙数据输入流
        }catch(IOException e){
            Log.e("mainActivity","接收数据失败！"+e.getMessage());
            tv_connect_device.setText("接收数据失败！");
            return;
        }
        if(bThread==false){
            ReadThread.start();
            bThread=true;
        }else{
            bRun = true;
        }

    }

    //接收数据线程
    Thread ReadThread=new Thread(){
        public void run(){
            String s = "";
            int num = 0;
            byte[] buffer = new byte[1024];
            byte[] buffer_new = new byte[1024];
            int i = 0;
            int n = 0;
            bRun = true;
            //接收线程
            while(true){
                try{
                    while(is.available()==0){
                        while(bRun == false){}
                    }
                    while(true){
                        num = is.read(buffer);         //读入数据
                        n=0;

                        String s0 = new String(buffer,0,num);
                        Log.e("mainActivity","s0==="+s0);
                       // fmsg+=s0;    //保存收到数据
                        for(i=0;i<num;i++){
                            Log.e("ccc","buffer[i]==="+buffer[i]);
                            if((buffer[i] == 0x0d)&&(buffer[i+1]==0x0a)){
                                buffer_new[n] = 0x0a;
                                i++;
                            }else{
                                buffer_new[n] = buffer[i];
                            }
                            n++;
                        }
                        s = new String(buffer_new,0,n);
                        Log.e("mainActivity","s==="+s);
                        smsg+=s;
                        stringBuilder.append(s); //写入接收缓存
                        if(is.available()==0)break;  //短时间没有数据才跳出进行显示
                            if(!isShow){
                                Message message = new Message();
                                message.what = 1;
                                //发送显示消息，进行显示刷新
                                handler.sendMessage(message);
                            }



                    }

                }catch(IOException e){
                }
            }
        }
    };

    //消息处理队列
    Handler handler= new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
          /*  dis.setText(smsg);   //显示数据
            sv.scrollTo(0,dis.getMeasuredHeight()); //跳至数据最后一页*/

          if(msg.what == 1){
              isShow = true;
              while (true){
                  int index = stringBuilder.indexOf("=",1);
                  String number = stringBuilder.substring(0,index+1).replaceAll("=","");
                  stringBuilder.delete(0,index+1);
                  System.out.println("截取的数据"+number.replaceAll("=",""));
                  Log.e("aaa","截取的数据："+stringBuilder);

                  try{
                      if(TextUtils.isEmpty(number)){
                          continue;
                      }
                      String reverseS = reverseString(number);
                      Double numberKg = Double.parseDouble(reverseS);
                      String values = String.format("%.2f",numberKg);
                      tv_weight.setText(values);
                  }catch (Exception e){
                    showToast("数据转换出错");
                  }

                  if(TextUtils.isEmpty(stringBuilder)){
                      isShow = false;
                      break;
                  }
              }

          }else if(msg.what == 2){
                //打开接收线程
                try{
                    is = _socket.getInputStream();   //得到蓝牙数据输入流
                }catch(IOException e){
                    showToast("接收数据失败！");
                    Log.e("mainActivity"," 连接失败...");
                    return;
                }
                if(bThread==false){
                    ReadThread.start();
                    bThread=true;
                }else{
                    bRun = true;
                }
          }

        }
    };

    public static String reverseString(String str) {
        if ((str == null) || str.length() <2) return str;
        char cArray[] = str.toCharArray();
        int begin = 0;
        int end = cArray.length-1;
        while(begin<end){
            char temp=cArray[begin];
            cArray[begin] = cArray[end];
            cArray[end] = temp;
            begin ++;
            end --;
        }
        return new String(cArray);
    }
}
