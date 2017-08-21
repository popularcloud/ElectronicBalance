package com.dlc.electronicbalance.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WeighDatasBean {

    @Id
    private Long id;
    private String rq;  //称重时间
    private String u_account; //员工编号
    private String u_name; //员工名称
    private String goods_id; //料品ID
    private String goods_name; //料品名称
    private String nums; //称重数据量
    private String op_account; //班长编号

    @Generated(hash = 896174209)
    public WeighDatasBean(Long id, String rq, String u_account, String u_name,
            String goods_id, String goods_name, String nums, String op_account) {
        this.id = id;
        this.rq = rq;
        this.u_account = u_account;
        this.u_name = u_name;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.nums = nums;
        this.op_account = op_account;
    }

    @Generated(hash = 1613902763)
    public WeighDatasBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getU_account() {
        return u_account;
    }

    public void setU_account(String u_account) {
        this.u_account = u_account;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getOp_account() {
        return op_account;
    }

    public void setOp_account(String op_account) {
        this.op_account = op_account;
    }
}